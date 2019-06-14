/*      Centro Universitario Senac
        Tecnologia em Jogos Digitais - 1o semestre de 2019 
        Professor: Bruno Sanches

        Projeto Integrador III - Projeto Final
        Arquivo: PACMAN 
        GUSTAVO ANDRÉ DE ALMEIDA SANTOS
        GUILHERME DELMONDES
        ÍCARO COSTA 

        Referências extraídas:
        
        Trechos e classes extraídos da video-aula do professor Guilherme Grillo da DankiCode.
        https://cursos.dankicode.com/campus/curso-dev-games
        
        Algoritmo da AEstrela (Classes AEstrela, No, Vector2i, método FollowPath() 
        da classe Objetos, e métodos tick() das classes Blinky, Clide, Inky, Pinky):
        https://cursos.dankicode.com/campus/curso-dev-games/a*-algoritmo
        https://cursos.dankicode.com/campus/curso-dev-games/aplicando-a*

        TileMap (Classe Mapa):
        https://cursos.dankicode.com/campus/curso-dev-games/tiles-e-validando-posicoes

        Spritesheet(Classe)
        https://cursos.dankicode.com/campus/curso-dev-games/classe-spritesheet-player-e-entity

        Animações do Player e Inimigos:
        https://cursos.dankicode.com/campus/curso-dev-games/manipulando-direcoes-e-animacoes
        
        
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Jogo extends Canvas implements Runnable, KeyListener {

    public static JFrame frame;
    private Thread thread;
    private static boolean isRunning = true;
    private static final int largura = 21 * 16;
    private static final int altura = 27 * 16 + 32;
    private static final int SCALE = 2; //Manter essa variavel, ela eh poderosissima para mudar o tamanho do game
    private static int fase = 1;
    public static boolean menu = true;
    public static boolean gameover;
    private Som musica = new Som();
    private int fruitTimer = 0; //Variavel usada para setar o tempo de desaparecimento da fruta

    private BufferedImage image;

    private static List<Objetos> objJogo; //Conterá todos os objetos do jogo
    private static List<Fantasma> fantasmas; //Conterá todos os inimigos do jogo
    public static List<Pontos> pontos; //Conterá os pontos do jogo

    private static Mapa mapa;
    private static Player pacman;
    private static Fruta fruit;
    public static Spritesheet spritesheet;
    private static boolean delay;

    public Jogo() {
        addKeyListener(this);
        setPreferredSize(new Dimension(largura * SCALE, altura * SCALE));
        initFrame();

        //INICIALIZANDO OBJETOS
        image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        objJogo = new ArrayList<>();
        pontos = new ArrayList<>();
        fantasmas = new ArrayList<>();
        spritesheet = new Spritesheet("/res/spritesheet.png");
        pacman = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        objJogo.add(pacman);
        mapa = new Mapa("/res/level1.png");
    }

    public void initFrame() {
        frame = new JFrame("PACMAN - O JOGO");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    //garante que todas as thread parem caso tenha algum problema
    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Jogo game = new Jogo();
        game.start();
    }

    public void tick() {
        if (!menu) {
            if (!gameover) {
                if (!delay) {
                    for (int i = 0; i < objJogo.size(); i++) {
                        Objetos e = objJogo.get(i);
                        e.tick();
                    }
                }
                //Randomiza o tempo de -aparecimento- da fruta caso ela já não esteja na lista de obj 
                if (new Random().nextInt(3000) == 1 && !objJogo.contains(fruit)) {
                    fruit = new Fruta(160, 160, 16, 16, spritesheet.getSprite(64, 32, 16, 16));
                    fruitTimer = 0; //inicia o contador da fruta
                    objJogo.add(fruit);
                }
                //Seta o tempo de -desaparecimento- da fruta pra 8s, caso ela esteja na lista de obj
                //A cada looping a variavel vai incrementando até chegar a 350
                if (fruitTimer >= 300 && objJogo.contains(fruit)) {
                    objJogo.remove(fruit);
                }

                if (pontos.isEmpty()) {
                    /*
                   Troca fase
                     */
                    if (fase > 3) {
                        fase = 1;
                    }
                    fase++;
                    Mapa.restartGame("level1.png");
                }

                fruitTimer++;
            }

        }
    }

    //Método que renderiza o mapa
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3); //serve para otimizar a renderizacao
            return;
        }
        //essas linhas limpam a tela toda vez, para não sobrecarregar
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, largura, altura);

        //Renderiza o mapa
        mapa.render(g);

        if (!gameover) { //Para de renderizar se o game acaba
            //Renderiza as entidades da classe objetos, pontos, pacman etc
            Collections.sort(objJogo, Objetos.nodeSorter);
            for (int i = 0; i < objJogo.size(); i++) {
                Objetos e = objJogo.get(i);
                e.render(g);

            }
        }
        UI.render(g); //renderiza a UI
        g.dispose(); //serve pra melhorar a otimização
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, largura * SCALE, altura * SCALE, null);
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();  //Pega o tempo real do pc em nano segundos
        double amountOfTicks = 60.0;  //60 frames por segundo
        double ns = 1000000000 / amountOfTicks; // usado para fazer o calculo de quando o jogo deve dar o update(tick)
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis(); //tempo atual em formato menos preciso e mais leve
        requestFocus(); //Permite mover ou interagir com o jogo sem precisar clicar na tela do jogo

        //GameLoop
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; // em algum momento vai dar 1, que é quando quer que de o tick
            lastTime = now;

            if (delta >= 1) {
                //atualiza o jogo antes de redenrizar, primeiro o tick dps o render
                tick();
                render();
                frames++;
                delta--; //decrementa pq ja chegou no 1
            }

            //Se for true, quer dizer que se passou um segundo e deve mostrar a mensagem
            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop(); //Metodo nao obrigatorio que serve de garantia, caso o programa saia do loop ele faz as thread parar
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*
        Se o jogador apertar tal tecla, seta aquela direção para true e a oposta pra false
        E seta a string 'novo' com o nome daquela direção, e a string 'ant' com o nome da direção anterior
        As strings sao usadas para ter controle sobre a movimentação, e o jogador só ir a direção desejada
        quando a mesma estiver livre, ou seja, se a direção que quer ir não estiver livre, o jogador vai
        continuar percorrendo a que ele já está até tiver uma livre.
         */
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //Direita
            pacman.setRight(true);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("right");
            pacman.setLeft(false);

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //Esquerda
            pacman.setLeft(true);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("left");
            pacman.setRight(false);

        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            //Cima
            pacman.setUp(true);
            pacman.setDown(false);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("up");

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //Baixo
            pacman.setDown(true);
            pacman.setUp(false);
            pacman.setAnt(pacman.getNovo());
            pacman.setNovo("down");
        }
        if (menu) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                menu = false;
                Som start = new Som();
                start.somStart();
                musica.somMusica();
            }
        }
        if (gameover) {//Restart Game
            if (e.getKeyCode() == KeyEvent.VK_R) {
                fase = 1;
                Mapa.restartGame("level" + fase + ".png");
            }
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

    public static void setObjJogo(List<Objetos> objJogo) {
        Jogo.objJogo = objJogo;
    }

    public static List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public static void setFantasmas(List<Fantasma> fantasmas) {
        Jogo.fantasmas = fantasmas;
    }

    public static Player getPacman() {
        return pacman;
    }

    public static void setPacman(Player pacman) {
        Jogo.pacman = pacman;
    }

    public static void setPontos(List<Pontos> pontos) {
        Jogo.pontos = pontos;
    }

    public static int getFase() {
        return fase;
    }

    public static Mapa getMapa() {
        return mapa;
    }

    public static void setMapa(Mapa mapa) {
        Jogo.mapa = mapa;
    }

    public static void gameOver() {

        for (int i = 0; i < Jogo.getObjJogo().size(); i++) {
            Objetos atual = Jogo.getObjJogo().get(i);
            if (atual instanceof Blinky) {
                Jogo.getObjJogo().remove(atual);
            }

            if (atual instanceof Inky) {
                Jogo.getObjJogo().remove(atual);
            }

            if (atual instanceof Pinky) {
                Jogo.getObjJogo().remove(atual);
            }

            if (atual instanceof Clide) {
                Jogo.getObjJogo().remove(atual);
            }

        }
        pacman.setX(10 * 16);
        pacman.setY(15 * 16);
        pacman.setVidas(pacman.getVidas() - 1);
        Jogo.getObjJogo().add(new Blinky(10 * 16, 12 * 16, 16, 16, Objetos.BLINKY));

        Jogo.getObjJogo().add(new Inky(10 * 16, 13 * 16, 16, 16, Objetos.INKY));

        Jogo.getObjJogo().add(new Pinky(11 * 16, 13 * 16, 16, 16, Objetos.PINKY));

        Jogo.getObjJogo().add(new Clide(9 * 16, 13 * 16, 16, 16, Objetos.CLIDE));

        if (pacman.getVidas() == 0) {
            gameover = true;
        }
    }
}
