
package ufrn.alvarofpp;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerP {
    private Player player;
    /**
     * Stream do arquivo de audio
     */
    FileInputStream input;
    /**
     * Buffer do arquivo de audio
     */
    BufferedInputStream buffer;
    /**
     * Tempo onde foi pausado
     */
    private long pauseLocate;
    /**
     * Tempo total da música
     */
    private long lengthMusic;
    /**
     * Caminho da música
     */
    private String pathMusic;
    
    public PlayerP(String path){ this.pathMusic = path; }
    
    private Thread thread = null;
    public void Play(){
        try {
            input = new FileInputStream(pathMusic);
            buffer = new BufferedInputStream(input);
            player = new Player(buffer);
            if(pauseLocate > 0){
                input.skip(lengthMusic - pauseLocate);
            }else{
                lengthMusic = input.available();
            }
            
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayerP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PlayerP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(PlayerP.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        thread = new Thread(){
            public void run(){
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(PlayerP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
    }
    public void Stop(){
        if(thread != null) thread.stop();
        player.close();
    }
    public void Pause(){
            if(thread!=null) thread.stop();
            try {
                pauseLocate = input.available();
            } catch (IOException ex) {
                Logger.getLogger(PlayerP.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            player.close();
  
    }
}
