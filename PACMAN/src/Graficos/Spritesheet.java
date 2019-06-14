package Graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Guilherme Delmondes
 * Extraído do curso de Guilherme Grillo da DankiCode
 * Link: https://cursos.dankicode.com/campus/curso-dev-games/classe-spritesheet-player-e-entity
 */
public class Spritesheet {

    private BufferedImage spritesheet;

    public Spritesheet(String path) {
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //retorna uma das sprites do spritesheet apartir da localização x e y, em pixels, da sprite
    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }
}
