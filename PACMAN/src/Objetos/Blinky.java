/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Mapa.AEstrela;
import Mapa.Vector2i;
import Pacman.Jogo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Guilherme Delmondes
 */
public class Blinky extends Fantasma {

    public Blinky(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void tick() {
        depth = 0;
        if (path == null || path.size() == 0) {
            Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
            Vector2i end = new Vector2i(((int) (Jogo.getPacman().getX() / 16)), ((int) (Jogo.getPacman().getY() / 16)));
            path = AEstrela.findPath(Jogo.getMapa(), start, end);
        }
        followPath(path);

        if (x % 16 == 0 && y % 16 == 0) {
            if (new Random().nextInt(100) < 10) {
                Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                Vector2i end = new Vector2i(((int) (Jogo.getPacman().getX() / 16)), ((int) (Jogo.getPacman().getY() / 16)));
                path = AEstrela.findPath(Jogo.getMapa(), start, end);
            }
        }
    }

    public void render(Graphics g) {
        if (Player.isBonus()) {
            g.drawImage(Jogo.spritesheet.getSprite(48, 16, 16, 16), this.getX(), this.getY(), null);
        } else {
            g.drawImage(Jogo.spritesheet.getSprite(48, 0, 16, 16), this.getX(), this.getY(), null);
        }

    }

}
