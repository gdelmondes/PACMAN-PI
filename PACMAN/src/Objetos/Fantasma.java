package Objetos;

import java.awt.image.BufferedImage;



/**
 *
 * @author Guilherme Delmondes
 * Essa classe recebe herança da classe objetos para que assim possamos fazer o uso
 * do polimorfismo
 */
public class Fantasma extends Objetos {

    private double speed = 0.6;

    public Fantasma(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
}
