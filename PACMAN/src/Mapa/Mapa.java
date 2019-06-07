package Mapa;

import Objetos.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import Pacman.Jogo;

/**
 *
 * @author Guilherme Delmondes
 */
public class Mapa {

    private static Bloco[] tiles;
    private static int WIDTH, HEIGHT;
    private static final int TILE_SIZE = 16;

    public Mapa(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Bloco[pixels.length];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            for (int xx = 0; xx < map.getWidth(); xx++) {
                for (int yy = 0; yy < map.getHeight(); yy++) {
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    tiles[xx + (yy * WIDTH)] = new Chao(xx * 16, yy * 16, Bloco.TILE_FLOOR);
                    switch (pixelAtual) {
                        case 0xFF000000:
                            //Chao
                            tiles[xx + (yy * WIDTH)] = new Chao(xx * 16, yy * 16, Bloco.TILE_FLOOR);
                            break;
                        case 0xFF0013FF:
                            //Parede
                            if(Jogo.getFase() == 1){
                                tiles[xx + (yy * WIDTH)] = new Parede(xx * 16, yy * 16, Bloco.TILE_WALL1);
                            } else if(Jogo.getFase() == 2){
                                tiles[xx + (yy * WIDTH)] = new Parede(xx * 16, yy * 16, Bloco.TILE_WALL2);
                            }else if(Jogo.getFase() == 3){
                                tiles[xx + (yy * WIDTH)] = new Parede(xx * 16, yy * 16, Bloco.TILE_WALL3);
                            }
                            break;
                        case 0xffff0000:
                            //Blinky
                            Fantasma blink = new Fantasma(xx * 16, yy * 16, 16, 16, Objetos.BLINKY);
                            Jogo.getObjJogo().add(blink);
                            Jogo.getFantasmas().add(blink);
                            break;
                        case 0xffff945a:
                            //Clide
                            Fantasma clide = new Fantasma(xx * 16, yy * 16, 16, 16, Objetos.CLIDE);
                            Jogo.getObjJogo().add(clide);
                            Jogo.getFantasmas().add(clide);
                            break;
                        case 0xff5cdbff:
                            //Inki
                            Fantasma inki = new Fantasma(xx * 16, yy * 16, 16, 16, Objetos.INKY);
                            Jogo.getObjJogo().add(inki);
                            Jogo.getFantasmas().add(inki);
                            break;
                        case 0xffff00ec:
                            //Pinky
                            Fantasma pinky = new Fantasma(xx * 16, yy * 16, 16, 16, Objetos.PINKY);
                            Jogo.getObjJogo().add(pinky);
                            Jogo.getFantasmas().add(pinky);
                            break;
                        case 0xFFc7c7c7:
                            //Pilula
                            Jogo.getObjJogo().add(new Pilula(xx * 16, yy * 16, 16, 16, Objetos.PILULA_SPRITE));
                            break;
                        case 0xFFffffff:
                            //Pontos
                            Jogo.getObjJogo().add(new Pontos(xx * 16, yy * 16, 16, 16, Objetos.PONTOS_SPRITE));
                            break;
                        case 0xFFff4900:
                            //Fruta
                            Jogo.getObjJogo().add(new Fruta(xx * 16, yy * 16, 16, 16, Objetos.FRUTA_SPRITE));
                            break;
                        case 0xFFFFd600:
                            //Player
                            Jogo.getPacman().setX(xx * 16);
                            Jogo.getPacman().setY(yy * 16);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*public static void restartGame(String level) {
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        spritesheet = new Spritesheet("/res/spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entities.add(player);
        world = new World("/res/" + level);
        return;
    }
     */

   public static boolean colide(int xNext, int yNext) {
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = (yNext / TILE_SIZE);

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * Mapa.WIDTH)] instanceof Parede)
                || (tiles[x2 + (y2 * Mapa.WIDTH)] instanceof Parede)
                || (tiles[x3 + (y3 * Mapa.WIDTH)] instanceof Parede)
                || (tiles[x4 + (y4 * Mapa.WIDTH)] instanceof Parede));
    }
    
    public void render(Graphics g) {
        for (int xx = 0; xx <= WIDTH; xx++) {
            for (int yy = 0; yy <= HEIGHT; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
                    continue;
                }
                Bloco tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }
    
    public static Bloco[] getTiles() {
        return tiles;
    }
    
    public static int getWIDTH() {
        return WIDTH;
    }

}