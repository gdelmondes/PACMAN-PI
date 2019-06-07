package Mapa;

import Pacman.Jogo;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bloco {
	
    public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(144,16,16,16);
    public static BufferedImage BAR = Jogo.spritesheet.getSprite(112,16,16,16);
    public static BufferedImage TILE_WALL1 = Jogo.spritesheet.getSprite(144,0,16,16);
    public static BufferedImage TILE_WALL2 = Jogo.spritesheet.getSprite(128,32,16,16);
    public static BufferedImage TILE_WALL3 = Jogo.spritesheet.getSprite(144,32,16,16);

    private BufferedImage sprite;
    private int x,y;

    public Bloco(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    //Renderiza todos os tiles do mapa
    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
