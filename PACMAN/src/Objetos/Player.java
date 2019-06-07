package Objetos;

import Mapa.Camera;
import Mapa.Mapa;
import java.awt.Graphics;
import Pacman.Jogo;
import java.awt.image.BufferedImage;

/**
 *
 * @author guilherme.mdelmondes
 */
public class Player extends Objetos {

//    private int x;
//    private int y;
//    private double velocidadeX = 0.6, velocidadeY = 0.6;
    private boolean up, down, right, left;
    private double velocidade = 1.0;
    private int vidas = 3;
    private int score = 0;
    private boolean bonus = false;
    private String novo = "";
    private String ant = "";
    private int timer = 0;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    //Acrescentar aqui, logica para colisao com a pilula de poder e frutas
    public void coletaItem() {
        for (int i = 0; i < Jogo.getObjJogo().size(); i++) {
            Objetos atual = Jogo.getObjJogo().get(i);
            if (atual instanceof Pontos) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 10;
                    Jogo.getObjJogo().remove(atual);
                }
            } 
            
            if (atual instanceof Pilula) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 10;
                    bonus = true;
                    timer=0;
                    Jogo.getObjJogo().remove(atual);
                }
            }
            
            if (atual instanceof Fruta) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 100;
                    Jogo.getObjJogo().remove(atual);
                }
            }
            
            if (atual instanceof Fantasma && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Fantasma(160, 200, 16, 16, Objetos.BLINKY));
                }
                if(timer >= 80){
                    bonus = false;
                }
            }
            
            //Gameover
            if (atual instanceof Fantasma && !bonus) {
                if (Objetos.isColliding(this, atual)) {
                    //Jogo.GameOver();
                }
            }
        }  
        timer++;
    }

    @Override
    public void tick() {
//        if(Mapa.colide((int) (x + velocidadeX),(int) (y + velocidadeY))){
//            this.x += velocidadeX;
//            this.y += velocidadeY;
//        }
//        if(Mapa.colide((int) (x + velocidadeX),(int) (y + velocidadeY))){
//            this.x += velocidadeX;
//            this.y += velocidadeY;
//        }
        
        if(left && Mapa.colide((int)(x - velocidade),this.getY())){
            x-=velocidade;
            
            if(ant.equals("up"))
                up = false;
            if(ant.equals("down"))
                down = false;
        }
        if(right && Mapa.colide((int)(x + velocidade), this.getY())){
            x+=velocidade;
            
            if(ant.equals("up"))
                up = false;
            if(ant.equals("down"))
                down = false;
        }
        if(up && Mapa.colide(this.getX(),(int)(y-velocidade))){
            y-=velocidade;
            
            if(ant.equals("left"))
                left = false;
            if(ant.equals("right"))
                right = false;
        }
        if(down && Mapa.colide(this.getX(),(int)(y+velocidade))){
            y+=velocidade;
            
            if(ant.equals("left"))
                left = false;
            if(ant.equals("right"))
                right = false;
        }
            
        coletaItem();
        
        //Verifica margens, caso chegue ao limite de uma aparece do outor lado
        if (x > Jogo.getLargura() - 18) {
            this.x = 0;
        } else if (this.x < 0) {
            x = Jogo.getLargura() - 18;
        }
        
    }
    
    public void render(Graphics g) {
        g.drawImage(Jogo.spritesheet.getSprite(80,0,16,16), this.getX(), this.getY(), null);
    }
    
//    public int getX() {
//        return x;
//    }

//    public double getVelocidadeX() {
//        return velocidadeX;
//    }
//
//    public void setVelocidadeX(double velocidadeX) {
//        this.velocidadeX = velocidadeX;
//    }
//
//    public double getVelocidadeY() {
//        return velocidadeY;
//    }
//
//    public void setVelocidadeY(double velocidadeY) {
//        this.velocidadeY = velocidadeY;
//    }
    
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    
    public boolean getBonus(){
        return bonus;
    }
    
    public void setUp(boolean up){
        this.up = up;
    }
    
    public void setDown(boolean down){
        this.down = down;
    }
    
    public void setRight(boolean right){
        this.right = right;
    }
    
    public void setLeft(boolean left){
        this.left = left;
    }
    
    public void setNovo(String novo){
        this.novo = novo;
    }
    
    public String getNovo(){
        return novo;
    }
    
    public void setAnt(String ant){
        this.ant = ant;
    }
}
