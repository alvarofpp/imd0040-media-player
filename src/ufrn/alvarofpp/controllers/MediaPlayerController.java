package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javazoom.jl.player.Player;
import ufrn.alvarofpp.PlayerP;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.controllers.helpers.Coordinates;
import ufrn.alvarofpp.controllers.helpers.AnimationGenerator;
import ufrn.alvarofpp.db.files.Musics;
import ufrn.alvarofpp.db.models.Music;

public class MediaPlayerController extends DefaultController {
    /**
     * Interface de usuário
     */
    @FXML
    private HBox mediaplayerui;
    /**
     * Interface ao lado
     */
    @FXML
    private Pane sidebar;
    /**
     * Label de nome de usuário, nome de playlist, nome da música
     */
    @FXML
    private Label usernameLabel, playlistLabel, musicLabel;
    /**
     * Label de nome de usuário, nome de playlist, nome da música
     */
    @FXML
    private ListView musicList, playlistList;
    /**
     * Animações
     */
    private AnimationGenerator animationGenerator;

    /**
     * Usuário logado
     */
    private User user;
    /**
     * Músicas do BD
     */
    Musics musics = new Musics();
    /**
    * Bool usado pelo play
    */
    private boolean press = true;
    /**
     * Reprodutor de música
     */
    PlayerP player = null;
    
    public void initMusicList(){
        ObservableList data =  FXCollections.observableArrayList();
        for(Music music : musics.getMusics())
            data.add(music.getName());
        musicList.setItems(data);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coordinates = new Coordinates();
        makeStageDrageable();
        animationGenerator = new AnimationGenerator();
        initMusicList();
        
        // Testes
        this.user = new User("israelfontes", "israel123");
        this.prepareEnv();
       
    }
    String temp;
    @FXML
    private void handlePlay(){
        if(player == null){
            if(musicList.getSelectionModel().getSelectedItem() == null){
                player = new PlayerP(musics.nextMusic().getPath());
                player.Play();
            }else{
                player = new PlayerP(musics.getMusic(musicList.getSelectionModel().getSelectedItem().toString()).getPath());
                temp = musics.getMusic(musicList.getSelectionModel().getSelectedItem().toString()).getPath();
                player.Play();
            }
        }else{
            if(musics.getMusic(musicList.getSelectionModel().getSelectedItem().toString()).getPath() != temp && musicList.getSelectionModel().getSelectedItem() != null){
                    player.Stop();
                    temp = musics.getMusic(musicList.getSelectionModel().getSelectedItem().toString()).getPath();
                    player = new PlayerP(temp);
                    player.Play();
            }else{
                if(press == true){
                    player.Pause();
                    press = false;
                }else{
                    player.Play();
                    press = true;
                }
            }
        }
    }
    
    @FXML
    private void handleNextMusic(){
       Music music = musics.nextMusic();
        
       if( music != null ){
           if(player!=null) player.Stop();
           player = new PlayerP(music.getPath());
           player.Play();
       }
     }
    
    @FXML
    private void handleBackMusic(){
        Music music = musics.backMusic();
        
        if( music!=null ){
            if(player != null) player.Stop();
            player = new PlayerP(music.getPath());
            player.Play();
        } 
    }
    /**
     * Prepara o ambiente da view
     */
    private void prepareEnv() {
        // Nome do usuário
        this.usernameLabel.setText(this.user.getUsername());
        // Playlist inicialmente é null
        this.playlistLabel.setText(null);
        // Música inicialmente é null
        this.musicLabel.setText("Não está tocando nada");
        // Upload das músicas
    }

    /**
     * Mostra o sidebar
     * @param event
     */
    @FXML
    private void showSidebar(MouseEvent event) {
        if (!sidebar.isVisible()) {
            sidebar.setVisible(true);
            animationGenerator.applyTranslateAnimationOn(sidebar);
            animationGenerator.applyFadeAnimationOn(sidebar, AnimationGenerator.INVISIBLE, AnimationGenerator.VISIBLE, null);
        } else {
            animationGenerator.applyFadeAnimationOn(sidebar, AnimationGenerator.VISIBLE, AnimationGenerator.INVISIBLE, (e) -> {
                sidebar.setVisible(false);
            });
        }
    }

    /**
     * Torna a interface arrastavel.
     */
    @Override
    protected void makeStageDrageable() {
        mediaplayerui.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coordinates.setxOffset(event.getSceneX());
                coordinates.setyOffset(event.getSceneY());
            }
        });
        mediaplayerui.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MediaPlayerUI.stage.setX(event.getScreenX() - coordinates.getxOffset());
                MediaPlayerUI.stage.setY(event.getScreenY() - coordinates.getyOffset());
                MediaPlayerUI.stage.setOpacity(0.7f);
            }
        });
        mediaplayerui.setOnDragDone((e) -> {
            MediaPlayerUI.stage.setOpacity(AnimationGenerator.VISIBLE);
        });
        mediaplayerui.setOnMouseReleased((e) -> {
            MediaPlayerUI.stage.setOpacity(AnimationGenerator.VISIBLE);
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
}
