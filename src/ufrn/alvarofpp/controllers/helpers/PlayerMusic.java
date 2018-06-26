package ufrn.alvarofpp.controllers.helpers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerMusic {
    /**
     * Player
     */
    private Player player;
    /**
     * Stream do arquivo de audio
     */
    private FileInputStream input;
    /**
     * Tempo onde foi pausado
     */
    private long pauseLocate;
    /**
     * Tempo total da música
     */
    private long timeMusic;
    /**
     * Caminho completo da música
     */
    private String pathMusic;

    public PlayerMusic(String pathMusic) {
        this.pathMusic = pathMusic;
        this.changeMusic(pathMusic);
    }

    private Thread thread = null;

    public void play() {
        try {
            if (pauseLocate > 0) {
                input.skip(timeMusic - pauseLocate);
            } else {
                timeMusic = input.available();
            }

        } catch (IOException ex) {
            Logger.getLogger(PlayerMusic.class.getName()).log(Level.SEVERE, null, ex);
        }

        thread = new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(PlayerMusic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
        }
    }

    public void pause() {
        if (thread != null) {
            thread.stop();
        }
        try {
            pauseLocate = input.available();
        } catch (IOException ex) {
            Logger.getLogger(PlayerMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeMusic(String path) {
        if (thread != null) {
            thread.stop();
        }
        this.pathMusic = path;
        this.pauseLocate = 0;

        try {
            input = new FileInputStream(pathMusic);
            BufferedInputStream buffer = new BufferedInputStream(input);
            player = new Player(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
