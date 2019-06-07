package Objetos;

import Mapa.AEstrela;
import Mapa.Vector2i;
import Pacman.Jogo;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 *
 * @author Guilherme Delmondes
 */
public class Fantasma extends Objetos {

    private double speed = 0.6;

    public Fantasma(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void tick() {
        depth=0;
        if(path == null || path.size() == 0) {
            Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
            Vector2i end = new Vector2i(((int)(Jogo.getPacman().getX()/16)),((int)(Jogo.getPacman().getY()/16)));
            path = AEstrela.findPath(Jogo.getMapa(), start, end);
        }
        if(new Random().nextInt(100) < 50)
            followPath(path);

        if(x % 16 == 0 && y % 16 == 0) {
            if(new Random().nextInt(100) < 10) {
                Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
                Vector2i end = new Vector2i(((int)(Jogo.getPacman().getX()/16)),((int)(Jogo.getPacman().getY()/16)));
                path = AEstrela.findPath(Jogo.getMapa(), start, end);
            }
        }
    }
}
