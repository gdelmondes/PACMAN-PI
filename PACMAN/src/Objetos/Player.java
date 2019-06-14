package Objetos;

import Mapa.Mapa;
import java.awt.Graphics;
import Pacman.Jogo;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author guilherme.mdelmondes
 */
public class Player extends Objetos {

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
    public static boolean bonus = false, shield = false, dormir = false, boost = false;
    private String novo = ""; //usado para saber o keypressed direção atual do jogador
    private String ant = ""; //usado para saber o keypressed anterior do jogador
    private int timer = 0; //usado para controlar o tempo de permanencia do bonus
    public static String status = "";

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
        //faz um looping para verificar se cada objeto do jogo está colidindo com o player
        for (int i = 0; i < Jogo.getObjJogo().size(); i++) {
            Objetos atual = Jogo.getObjJogo().get(i);
            if (atual instanceof Pontos) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 10;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.pontos.remove(atual);
                    Som ponto = new Som();
                    ponto.somPacMan();
                }
            }

            //bonus da pilula ativo, randomiza o tipo de bonus
            if (atual instanceof Pilula) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 10;
                    timer = 0; //seta o tempo que o bonus deve ficar ativo
                    Jogo.getObjJogo().remove(atual);
                    Som powerup = new Som();
                    powerup.somFruta();
                    int rand = new Random().nextInt(4);
                    switch (rand) {
                        case 0:
                            status = "Bonus-on!";
                            bonus = true;
                            break;
                        case 1:
                            status = "Escudo ativo!";
                            shield = true;
                            break;
                        case 2:
                            status = "Paralizar!";
                            dormir = true;
                            break;
                        case 3:
                            status = "Velocidade +";
                            boost = true;
                            break;
                        default:
                            break;
                    }

                }
            }

            if (atual instanceof Fruta) {
                if (Objetos.isColliding(this, atual)) {
                    this.score += 100;
                    Jogo.getObjJogo().remove(atual);
                    Som fruta = new Som();
                    fruta.somFruta();
                }
            }

            if (atual instanceof Blinky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    Som come = new Som();
                    come.somFruta();
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Blinky(160, 200, 16, 16, Objetos.BLINKY));
                }
            }
            if (atual instanceof Clide && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    Som come = new Som();
                    come.somFruta();
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Clide(160, 200, 16, 16, Objetos.CLIDE));
                }
            }

            if (atual instanceof Pinky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    Som come = new Som();
                    come.somFruta();
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Pinky(160, 200, 16, 16, Objetos.PINKY));
                }
            }

            if (atual instanceof Inky && bonus) {
                if (Objetos.isColliding(this, atual)) {
                    Som come = new Som();
                    come.somFruta();
                    this.score += 200;
                    Jogo.getObjJogo().remove(atual);
                    Jogo.getObjJogo().add(new Inky(160, 200, 16, 16, Objetos.INKY));
                }
            }
            if (timer >= 60 * 6) { //controla o tempo de bonus ativo
                //Reseta buffs
                bonus = false;
                boost = false;
                dormir = false;
                shield = false;
            }

            //Gameover
            if (atual instanceof Fantasma && !bonus) {
                if (Objetos.isColliding(this, atual)) {
                    if (shield) {//Verifica se o escudo está ativo
                        continue;
                    }
                    Som morte = new Som();
                    morte.somGameOver();
                    Jogo.gameOver();
                }
            }//else if(atual instanceof Fantasma && !shield) {
            //if (Objetos.isColliding(this, atual)) {
            //  Jogo.gameOver();
            //}
            //}

        }
        timer++;
    }

    @Override
    public void tick() {
        moved = false;

        //Checa os booleanos de direção e colisão se o caminho estiver livre o pacman anda naquela direção true
        //strings novo e ant servem para definir a proxima direção antes dela estar livre, e setar os booleanos para false
        if (left && Mapa.colide((int) (x - velocidade), this.getY())) {
            moved = true;
            direct = dir_esq;
            if (boost) {
                velocidade = 1.2;//Boost de velocidade
            } else {
                velocidade = 1.0;
            }
            x -= velocidade;
            dirX = -(velocidade * 32);
            dirY = 0;

            //seta a direção anterior para false caso esteja percorrendo uma direção válida
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
            if (boost) {
                velocidade = 1.2;//Boost de velocidade
            } else {
                velocidade = 1.0;
            }
            x += velocidade;
            dirX = (velocidade * 32);
            dirY = 0;

            //seta a direção anterior para false caso esteja percorrendo uma direção válida
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
            if (boost) {
                velocidade = 1.2;//Boost de velocidade
            } else {
                velocidade = 1.0;
            }
            y -= velocidade;

            dirX = 0;
            dirY = -(velocidade * 32);

            //seta a direção anterior para false caso esteja percorrendo uma direção válida
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
            if (boost) {
                velocidade = 1.2;//Boost de velocidade
            } else {
                velocidade = 1.0;
            }
            y += velocidade;
            dirX = 0;
            dirY = (velocidade * 32);

            //seta a direção anterior para false caso esteja percorrendo uma direção válida
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
        if (direct == dir_cima) {//Verifica qual direção o pacman esta andando
            g.drawImage(pacCima[index], this.x, this.y, null);
        } else if (direct == dir_baixo) {
            g.drawImage(pacBaixo[index], this.x, this.y, null);
        } else if (direct == dir_esq) {
            g.drawImage(pacEsq[index], this.x, this.y, null);
        } else if (direct == dir_dir) {
            g.drawImage(pacDir[index], this.x, this.y, null);
        }
        if (shield) {
            g.setColor(Color.CYAN);
            g.drawOval(this.x - 8, this.y - 8, 32, 32);//Escudo
        }
    }

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
