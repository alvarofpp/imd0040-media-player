package ufrn.alvarofpp.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import ufrn.alvarofpp.ui.helpers.AnimationGenerator;

public class MediaPlayerController extends UiController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;
    AnimationGenerator animationGenerator = null;

    @FXML
    private HBox mediaplayer;
    @FXML
    private Pane sidebar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDrageable();
        sidebar.setVisible(false);
        animationGenerator = new AnimationGenerator();
    }

    public void makeStageDrageable() {
        mediaplayer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mediaplayer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MediaPlayerUI.stage.setX(event.getScreenX() - xOffset);
                MediaPlayerUI.stage.setY(event.getScreenY() - yOffset);
                MediaPlayerUI.stage.setOpacity(0.7f);
            }
        });
        mediaplayer.setOnDragDone((e) -> {
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
        mediaplayer.setOnMouseReleased((e) -> {
            MediaPlayerUI.stage.setOpacity(1.0f);
        });
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
}
