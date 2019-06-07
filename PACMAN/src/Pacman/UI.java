/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Guilherme
 */
public class UI {

    public static void render(Graphics g) {
        //Desenha UI(Score e vidas)
        g.setColor(Color.white);
        g.drawString("SCORE: " + Jogo.getPacman().getScore(), 4, Jogo.getAltura() - 12);
        //g.drawString("LIVES: " + Jogo.getPacman().getVidas(), Jogo.getLargura() - 52, Jogo.getAltura() - 12);
        if(Jogo.gameover){
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("GAME OVER!", Jogo.getLargura()/2-64, Jogo.getAltura()/2);
        }
        
        //Printa se o bonus estiver ativo
        if(Jogo.getPacman().getBonus())
            g.drawString("BONUS-ON", 140, Jogo.getAltura() - 12);
        for (int i = 0; i <= Jogo.getPacman().getVidas(); i++) {
            g.drawImage(Jogo.spritesheet.getSprite(96,0,16,16), Jogo.getLargura()-i*18, Jogo.getAltura()-24, null);
        }
     
    }
}
