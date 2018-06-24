package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.controllers.helpers.Coordinates;
import ufrn.alvarofpp.controllers.helpers.AnimationGenerator;

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
     * Animações
     */
    private AnimationGenerator animationGenerator;

    /**
     * Usuário logado
     */
    User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coordinates = new Coordinates();
        makeStageDrageable();
        sidebar.setVisible(false);
        animationGenerator = new AnimationGenerator();
    }

    @FXML
    private void open_or_close_sidebar(MouseEvent event) {
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
