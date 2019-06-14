package Objetos;

import java.io.File;
import javafx.animation.PauseTransition;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Som {
    // Todos os códigos de som foram desenvolvidos com o auxilo do professor Bruno Sanches!

    public void play() {

    }

    public static void main(String[] args) throws InterruptedException {

    }

    public void playSound(String name) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void somStart() {
        new Som().playSound("pacman_beginning.wav");
    }

    public void somMusica() {
        new Som().playSound("musica.wav");
    }

    public void somPacMan() {
        new Som().playSound("pacman_chomp.wav");
    }

    public void somFruta() {
        new Som().playSound("pacman_eatfruit.wav");
    }

    public void somFase() {
        new Som().playSound("pacman_Trocafase.wav");
    }

    public void somGameOver() {
        new Som().playSound("pacman_death.wav");
    }
}
