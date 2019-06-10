package Objetos;

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
    private int frames = 0, maxFrames = 5, index = 0, maxIndex = 2;
    private int dir_cima = 1, dir_baixo = 2, dir_esq = 3, dir_dir = 4;
    private int direct = dir_dir;
    private boolean up, down, right, left;
    private boolean moved;
    private double velocidade = 1.0;
    private int vidas = 3;
    private int score = 0;
    private double dirX;
    private double dirY;
    private static boolean bonus = false;
    private String novo = "";
    private String ant = "";
    private int timer = 0;

    private BufferedImage pacCima[];
    private BufferedImage pacBaixo[];
    private BufferedImage pacEsq[];
    private BufferedImage pacDir[];

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        pacCima = new BufferedImage[3];
        pacBaixo = new BufferedImage[3];
        pacEsq = new BufferedImage[3];
        pacDir = new BufferedImage[3];

        pacCima[0] = Jogo.spritesheet.getSprite(112, 0, 16, 16);
        pacCima[1] = Jogo.spritesheet.getSprite(112, 32, 16, 16);
        pacCima[2] = Jogo.spritesheet.getSprite(64, 0, 16, 16);

        pacBaixo[0] = Jogo.spritesheet.getSprite(64, 16, 16, 16);
        pacBaixo[1] = Jogo.spritesheet.getSprite(96, 32, 16, 16);
        pacBaixo[2] = Jogo.spritesheet.getSprite(64, 0, 16, 16);

        pacEsq[0] = Jogo.spritesheet.getSprite(96, 0, 16, 16);
        pacEsq[1] = Jogo.spritesheet.getSprite(96, 16, 16, 16);
        pacEsq[2] = Jogo.spritesheet.getSprite(64, 0, 16, 16);

        pacDir[0] = Jogo.spritesheet.getSprite(80, 0, 16, 16);
        pacDir[1] = Jogo.spritesheet.getSprite(80, 16, 16, 16);
        pacDir[2] = Jogo.spritesheet.getSprite(64, 0, 16, 16);

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
                    timer = 0;
                    Jogo.getObjJogo().remove(atual);
                }
            }

            if (atual instanceof Fruta) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 100;
                    Jogo.getObjJogo().remove(atual);
                }
            }

            if (atual instanceof Blinky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Blinky(160, 200, 16, 16, Objetos.BLINKY));
                }
            }
            if (atual instanceof Clide && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Clide(160, 200, 16, 16, Objetos.CLIDE));
                }
            }

            if (atual instanceof Pinky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Pinky(160, 200, 16, 16, Objetos.PINKY));
                }
            }

            if (atual instanceof Inky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Inky(160, 200, 16, 16, Objetos.INKY));
                }
            }
            if (timer >= 80) {
                bonus = false;
            }

            //Gameover
            if (atual instanceof Fantasma && !bonus) {
                if (Objetos.isColliding(this, atual)) {
                    Jogo.gameOver();
                }
            }
        }
        timer++;
    }

    @Override
    public void tick() {
        moved = false;
//        if(Mapa.colide((int) (x + velocidadeX),(int) (y + velocidadeY))){
//            this.x += velocidadeX;
//            this.y += velocidadeY;
//        }
//        if(Mapa.colide((int) (x + velocidadeX),(int) (y + velocidadeY))){
//            this.x += velocidadeX;
//            this.y += velocidadeY;
//        }

        if (left && Mapa.colide((int) (x - velocidade), this.getY())) {
            moved = true;
            direct = dir_esq;
            x -= velocidade;
            dirX = -(velocidade * 32);
            dirY = 0;

            if (ant.equals("up")) {
                up = false;
            }
            if (ant.equals("down")) {
                down = false;
            }
        }
        if (right && Mapa.colide((int) (x + velocidade), this.getY())) {
            moved = true;
            direct = dir_dir;
            x += velocidade;
            dirX = (velocidade * 32);
            dirY = 0;

            if (ant.equals("up")) {
                up = false;
            }
            if (ant.equals("down")) {
                down = false;
            }
        }
        if (up && Mapa.colide(this.getX(), (int) (y - velocidade))) {
            moved = true;
            direct = dir_cima;
            y -= velocidade;
            dirX = 0;
            dirY = -(velocidade * 32);

            if (ant.equals("left")) {
                left = false;
            }
            if (ant.equals("right")) {
                right = false;
            }
        }
        if (down && Mapa.colide(this.getX(), (int) (y + velocidade))) {
            moved = true;
            direct = dir_baixo;
            y += velocidade;
            dirX = 0;
            dirY = (velocidade * 32);

            if (ant.equals("left")) {
                left = false;
            }
            if (ant.equals("right")) {
                right = false;
            }
        }
        //Logica para animar o pacman (Extraido da video-aula da DankiCode)
        //https://cursos.dankicode.com/campus/curso-dev-games/manipulando-direcoes-e-animacoes
        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                index++;
                if (index > maxIndex) {
                    index = 0;
                }
            }
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
        if (direct == dir_cima) {
            g.drawImage(pacCima[index], this.x, this.y, null);
        } else if (direct == dir_baixo) {
            g.drawImage(pacBaixo[index], this.x, this.y, null);
        } else if (direct == dir_esq) {
            g.drawImage(pacEsq[index], this.x, this.y, null);
        } else if (direct == dir_dir) {
            g.drawImage(pacDir[index], this.x, this.y, null);
        }
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

    public boolean getBonus() {
        return bonus;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setNovo(String novo) {
        this.novo = novo;
    }

    public String getNovo() {
        return novo;
    }

    public void setAnt(String ant) {
        this.ant = ant;
    }

    public double getDirX() {
        return dirX;
    }

    public double getDirY() {
        return dirY;
    }

    public static boolean isBonus() {
        return bonus;
    }

}
