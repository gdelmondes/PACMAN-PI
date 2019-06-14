/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacman;

import Objetos.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Guilherme Essa classe foi desenvolvida com base nos nossos
 * conhecimentos adquiridos no PI do semestre passado E no curso de games da
 * danki code especialmente a aula de setup e graficos onde o professor
 * Guilherme Grillo Link:
 * https://cursos.dankicode.com/campus/curso-dev-games/setup-e-graficos
 */
public class UI {

    public static void render(Graphics g) {
        //Desenha UI(Score e vidas)
        g.setColor(Color.white);
        g.drawString("SCORE: " + Jogo.getPacman().getScore(), 4, Jogo.getAltura() - 12);
        //g.drawString("LIVES: " + Jogo.getPacman().getVidas(), Jogo.getLargura() - 52, Jogo.getAltura() - 12);
        if (Jogo.menu) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Jogo.getLargura(), Jogo.getAltura());
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("PLAY", Jogo.getLargura() / 2 - 25, Jogo.getAltura() / 2);

            

            g.drawString("'S' to Start", Jogo.getLargura() / 2 - 50, Jogo.getAltura() / 2 + 64);
            g.drawImage(Jogo.spritesheet.getSprite(80, 0, 16, 16), Jogo.getLargura()/2 - 7, Jogo.getAltura()/2 + 15, null);
            //g.drawString(Jogo.getPacman().getScore(),Jogo.getLargura()/2-64, Jogo.getAltura()+128/2);
        }
        if (Jogo.gameover) {
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, Jogo.getLargura(), Jogo.getAltura());
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("GAME OVER!", Jogo.getLargura() / 2 - 64, Jogo.getAltura() / 2);
            g.drawString("Score " + Jogo.getPacman().getScore(), Jogo.getLargura() / 2 - 38, Jogo.getAltura() / 2 + 32);
            g.drawString("    'R' to Restart", Jogo.getLargura() / 2 - 84, Jogo.getAltura() / 2 + 64);
            //g.drawString(Jogo.getPacman().getScore(),Jogo.getLargura()/2-64, Jogo.getAltura()+128/2);
        }

        //Printa se o bonus estiver ativo
        if (Player.bonus || Player.boost || Player.dormir || Player.shield) {
            g.drawString(Player.status, 140, Jogo.getAltura() - 12);
        }
        if (!Jogo.menu) {
            for (int i = 0; i <= Jogo.getPacman().getVidas(); i++) {
                g.drawImage(Jogo.spritesheet.getSprite(96, 0, 16, 16), Jogo.getLargura() - i * 18, Jogo.getAltura() - 24, null);
            }
        }
    }
}
