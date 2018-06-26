package ufrn.alvarofpp.controllers.helpers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
    }

    private Thread thread = null;

    public void play() {
        try {
            input = new FileInputStream(pathMusic);
            BufferedInputStream buffer = new BufferedInputStream(input);
            player = new Player(buffer);
            if (pauseLocate > 0) {
                input.skip(timeMusic - pauseLocate);
            } else {
                timeMusic = input.available();
            }

        } catch (IOException ex) {
            Logger.getLogger(PlayerMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException e) {
            e.printStackTrace();
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
        if (thread != null) thread.stop();
        player.close();
    }

    public void pause() {
        if (thread != null) thread.stop();
        try {
            pauseLocate = input.available();
        } catch (IOException ex) {
            Logger.getLogger(PlayerMusic.class.getName()).log(Level.SEVERE, null, ex);
        }

        player.close();
    }

    public void changeMusic(String path) {
        this.pathMusic = path;
    }
}
