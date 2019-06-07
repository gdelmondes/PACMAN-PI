/*          Centro Universitario Senac
        Tecnologia em Jogos Digitais - 1o semestre de 2019 
        Professor: Bruno Snches

        Projeto Integrador III - Projeto Final
        Arquivo: <nome do arquivo> 
        GUSTAVO ANDRÉ DE ALMEIDA SANTOS
        GUILHERME DELMONDES
        ÍCARO COSTA 

        Data de entrega: 13/06/2019                               */

package Pacman;

import Graficos.Spritesheet;
import Mapa.Mapa;
import Objetos.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Jogo extends Canvas implements Runnable, KeyListener{

    public static JFrame frame;
    private Thread thread;
    private static boolean isRunning = true;
    private static final int largura = 21 * 16;
    private static final int altura = 27 * 16 + 32;
    private static final int SCALE = 1; //Manter essa variavel, ela eh poderosissima para mudar o tamanho do game
    private static int fase = 1;
    
    private BufferedImage image;
    
    private static List<Objetos> objJogo;
    private static List<Fantasma> fantasmas;

    private static Mapa mapa;
    private static Player pacman;
    private static Fruta fruit;
    private static Random rand;
    public static Spritesheet spritesheet;
    

    public Jogo (){
        rand = new Random();
        addKeyListener(this);
        setPreferredSize(new Dimension(largura * SCALE, altura * SCALE));
	initFrame();
        
        //INICIALIZANDO OBJETOS
        image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        objJogo = new ArrayList<>();
        fantasmas = new ArrayList<>();
        spritesheet = new Spritesheet("/res/spritesheet.png");
        pacman = new Player(0, 0, 16, 16, spritesheet.getSprite(32,0,16,16));
        objJogo.add(pacman);
        mapa = new Mapa("/res/level3.png");
    }
    
    public void initFrame(){
        frame = new JFrame("PACMAN - O JOGO");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
	
    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
    public static void main (String args []){
        Jogo game = new Jogo();
        game.start();
    }
    
    public void tick(){
        for (int i = 0; i < objJogo.size(); i++) {
            Objetos e = objJogo.get(i);
            e.tick();
        }
        
        //Randomiza o tempo de -aparecimento- da fruta caso ela já não esteja na lista de obj 
        if(rand.nextInt(3000) < 3 && !objJogo.contains(fruit)){
            fruit = new Fruta(160, 160, 16, 16, spritesheet.getSprite(16,32,16,16));
            objJogo.add(fruit);
        }
        //Randomiza o tempo de -desaparecimento- da fruta caso ela esteja na lista de obj
        if(rand.nextInt(2500) < 5 && objJogo.contains(fruit)){
            objJogo.remove(fruit);
        }
        
        
    }
    
    //Método que renderiza o mapa
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, largura, altura);
        
        //Renderiza o mapa
        mapa.render(g);
        
        //Renderiza as entidades da classe objetos, pontos, pacman etc
        Collections.sort(objJogo, Objetos.nodeSorter);
	for (int i = 0; i < objJogo.size(); i++) {
            Objetos e = objJogo.get(i);
            e.render(g);
        }
        
        //Desenha UI(Score e vidas)
        g.setColor(Color.white);
        g.drawString("SCORE: " + pacman.getScore(), 4, altura - 12);
        g.drawString("LIVES: " + pacman.getVidas(), largura - 52, altura - 12);
        
        //Printa se o bonus estiver ativo
        if(pacman.getBonus())
            g.drawString("BONUS-ON", 140, altura - 12);
        
        g.dispose(); 
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, largura * SCALE, altura * SCALE, null);
        bs.show();
    }

    @Override
    public void run() {
        //Game loop nao colocar mais nada aqui, toda logica deve ser implementadata no metodo tick
        long lastTime = System.nanoTime(); 
        double amountOfTicks = 60.0; 
        double ns = 1000000000 / amountOfTicks; 
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        
        while(isRunning){
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            
            if(delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer+=1000;
            }
        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //Direita
            pacman.setRight(true);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("right");
            //pacman.setUp(false);
            pacman.setLeft(false);
            //pacman.setDown(false);
//            pacman.setVelocidadeX(2.0);
//            pacman.setVelocidadeY(0);
            
              
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //Esquerda
            pacman.setLeft(true);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("left");
            //pacman.setUp(false);
            pacman.setRight(false);
            //pacman.setDown(false);
//            pacman.setVelocidadeX(-2.0);
//            pacman.setVelocidadeY(0);
            
          
        } if (e.getKeyCode() == KeyEvent.VK_UP) {
            //Cima
            pacman.setUp(true);
            pacman.setDown(false);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("up");
            //pacman.setLeft(false);
            //pacman.setRight(false);
//            pacman.setVelocidadeX(0);
//            pacman.setVelocidadeY(-2.0);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //Baixo
            pacman.setDown(true);
            pacman.setUp(false);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("down");
            //pacman.setLeft(false);
            //pacman.setRight(false);
//            pacman.setVelocidadeX(0);
//            pacman.setVelocidadeY(2.0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static int getLargura() {
        return largura;
    }

    public static int getAltura() {
        return altura;
    }

    public static List<Objetos> getObjJogo() {
        return objJogo;
    }

    public static List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public static Player getPacman() {
        return pacman;
    }

    public static int getFase() {
        return fase;
    }
    
    
    
    public static Mapa getMapa() {
        return mapa;
    }
    
    public static void GameOver() {
        isRunning = false;
    }
}
	
