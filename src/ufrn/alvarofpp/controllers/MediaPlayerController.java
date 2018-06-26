package ufrn.alvarofpp.controllers;

import java.io.File;
import java.net.URL;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ufrn.alvarofpp.controllers.helpers.Alerts;
import ufrn.alvarofpp.controllers.helpers.PlayerMusic;
import ufrn.alvarofpp.db.models.Playlist;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;
import ufrn.alvarofpp.exceptions.UserExistException;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.controllers.helpers.Coordinates;
import ufrn.alvarofpp.controllers.helpers.AnimationGenerator;
import ufrn.alvarofpp.db.files.Musics;
import ufrn.alvarofpp.db.models.Music;

import javax.swing.*;

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
     * Ícone do play
     */
    @FXML
    private FontAwesomeIconView iconPlay;
    /**
     * Animações
     */
    private AnimationGenerator animationGenerator;
    /**
     * Usuário logado
     */
    private User user;
    /**
     * Músicas no banco de dados
     */
    private Musics musics;
    /**
     * Bool usado pelo play
     */
    private boolean press = true;
    /**
     * Reprodutor de música
     */
    private PlayerMusic player;
    /**
     * Última música tocada
     */
    private String lastMusic;
    /**
     * Media Player, serve para controlar as músicas
     */
    private Playlist playlist;
    /**
     * Alertas
     */
    private Alerts alerts;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.coordinates = new Coordinates();
        makeStageDrageable();
        this.animationGenerator = new AnimationGenerator();
        this.alerts = new Alerts();

        // Usuário logado
        this.user = LoginController.user;

        // Músicas do usuário
        this.musics = new Musics(this.user.getUsername());
        updateListMusic();
        // Playlist
        this.playlist = new Playlist(this.musics.getMusics());

        // Nome do usuário
        this.usernameLabel.setText(this.user.getUsername());
        // Playlist inicialmente é null
        this.playlistLabel.setText(null);
        // Música inicialmente é null
        this.musicLabel.setText("Não está tocando nada");

    }

    private void updateListMusic() {
        // A lista de itens
        ObservableList listaItens = FXCollections.observableArrayList();
        // Alimenta a lista
        for (Music music : this.musics.getMusics()) {
            listaItens.add(music.getName());
        }
        //listaItens.add(musics.getMusics());
        // Envia a lista para view
        this.musicList.setItems(listaItens);
    }

    /**
     * Adiciona uma nova música
     * @param event
     */
    @FXML
    private void handleAddMusic(MouseEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);

        // Verifica se o usuário selecionou um arquivo
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Tenta adicionar uma nova música
            try {
                // Adiciona a nova música no banco
                this.musics.create(selectedFile.getName(), selectedFile.getParentFile().getPath());
                updateListMusic();
                // Mostra alerta para o usuário
                this.alerts.show(1, "Adição de nova música", selectedFile.getName(), "Adicionado com sucesso!");
            } catch (FieldNotFoundException e) {
                e.printStackTrace();
            } catch (UserExistException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Toca a música
     *
     * @param event
     */
    @FXML
    private void handlePlay(MouseEvent event) {
        // Música selecionada na playlist
        String musicSelectedName;
        Music musicSelected = null;

        // Verifica se teve item selecionado na playlist
        if (this.musicList.getSelectionModel().getSelectedItem() != null) {
            musicSelectedName = this.musicList.getSelectionModel().getSelectedItem().toString();
            musicSelected = this.playlist.getMusic(musicSelectedName);
            // Muda a label de música
            this.musicLabel.setText(musicSelected.getName());
        }

        // Quando o tocador de música ainda está vazio
        if (this.player == null) {
            // Caso não tenha item selecionado na lista
            if (musicSelected == null) {
                this.player = new PlayerMusic(this.playlist.next().getFullPath());
                // Muda a label de música
                this.musicLabel.setText(this.playlist.getActual().getName());
            } else {
                this.player = new PlayerMusic(musicSelected.getFullPath());
                this.lastMusic = musicSelected.getFullPath();
            }
            this.player.play();
            this.iconPlay.setIcon(FontAwesomeIcon.PAUSE);
        } else {
            if ((musicSelected != null) && (!musicSelected.getFullPath().equals(this.lastMusic))) {
                this.player.stop();
                this.lastMusic = musicSelected.getFullPath();
                this.player.changeMusic(this.lastMusic);
                this.player.play();
                this.iconPlay.setIcon(FontAwesomeIcon.PAUSE);
            } else {
                if (this.press) {
                    this.player.pause();
                    this.press = false;
                    this.iconPlay.setIcon(FontAwesomeIcon.PLAY);
                } else {
                    this.player.play();
                    this.press = true;
                    this.iconPlay.setIcon(FontAwesomeIcon.PAUSE);
                }
            }
        }
    }

    /**
     * Passa a música
     *
     * @param event
     */
    @FXML
    private void handleNextMusic(MouseEvent event) {
        this.changeMusic(this.playlist.next());
    }

    /**
     * Volta a música
     *
     * @param event
     */
    @FXML
    private void handleBackMusic(MouseEvent event) {
        this.changeMusic(this.playlist.back());
    }

    /**
     * Muda a música que tá tocando
     * @param music Música que se deseja tocar
     */
    private void changeMusic(Music music) {
        if (player != null) {
            player.stop();
        }
        player.changeMusic(music.getFullPath());
        player.play();

        // Atualiza label de música
        this.musicLabel.setText(music.getName());
    }

    /**
     * Mostra o sidebar
     *
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
}
