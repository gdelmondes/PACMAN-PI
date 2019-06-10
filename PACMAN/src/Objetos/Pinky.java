/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Mapa.AEstrela;
import Mapa.Vector2i;
import Pacman.Jogo;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Guilherme Delmondes
 */
public class Pinky extends Fantasma{
    
    public Pinky(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
         this.setMask(4, 4, 8, 8);
    }
    
    @Override
    public void tick() {
        depth=0;
        if(path == null || path.size() == 0) {
            Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
            Vector2i end = new Vector2i(((int)((Jogo.getPacman().getX()+Jogo.getPacman().getDirX())/16)),((int)((Jogo.getPacman().getY()+Jogo.getPacman().getDirY())/16)));
            path = AEstrela.findPath(Jogo.getMapa(), start, end);
        }
            followPath(path);
            
        if(x % 16 == 0 && y % 16 == 0) {
            if(new Random().nextInt(100) < 10) {
                Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
                Vector2i end = new Vector2i(((int)((Jogo.getPacman().getX()+Jogo.getPacman().getDirX())/16)),((int)((Jogo.getPacman().getY()+Jogo.getPacman().getDirY())/16)));
                path = AEstrela.findPath(Jogo.getMapa(), start, end);
            }
        }
        
    }
    
        public void render(Graphics g) {
        if (Player.isBonus()) {
            g.drawImage(Jogo.spritesheet.getSprite(0, 16, 16, 16), this.getX(), this.getY(), null);
        } else {
            g.drawImage(Jogo.spritesheet.getSprite(0, 0, 16, 16), this.getX(), this.getY(), null);
        }

    }
    
}
