package ufrn.alvarofpp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ufrn.alvarofpp.controllers.helpers.Offset;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.ui.helpers.AnimationGenerator;

public class MediaPlayerController extends DefaultController {

    private Offset offset;
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    private AnimationGenerator animationGenerator = null;

    @FXML
    private HBox mediaplayerui;
    @FXML
    private Pane sidebar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        offset = new Offset();
        makeStageDrageable(mediaplayerui, offset);
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
     * @param ui Interface de usuário
     * @param offset Coordenadas da interface
     */
    @Override
    protected void makeStageDrageable(HBox ui, Offset offset) {

        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                offset.setxOffset(event.getSceneX());
                offset.setyOffset(event.getSceneY());
            }
        });
        ui.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MediaPlayerUI.stage.setX(event.getScreenX() - offset.getxOffset());
                MediaPlayerUI.stage.setY(event.getScreenY() - offset.getyOffset());
                MediaPlayerUI.stage.setOpacity(0.7f);
            }
        });
        ui.setOnDragDone((e) -> {
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
        ui.setOnMouseReleased((e) -> {
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
    }
}
