package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.ui.helpers.AnimationGenerator;
import ufrn.alvarofpp.controllers.helpers.Coordinates;

public class MediaPlayerController extends DefaultController {
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    private AnimationGenerator animationGenerator = null;

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
            animationGenerator.applyTranslateAnimationOn(sidebar, 500, DEFAULT_ENDING_X_POSITION, DEFAULT_STARTING_X_POSITION);
            animationGenerator.applyFadeAnimationOn(sidebar, 600, 0f, 1.0f, null);
        } else {
            animationGenerator.applyFadeAnimationOn(sidebar, 600, 1.0f, 0.0f, (e) -> {
                sidebar.setVisible(false);
            });
        }
    }

    /**
     * Torna a interface arrastavel
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
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
        mediaplayerui.setOnMouseReleased((e) -> {
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
    }
}
