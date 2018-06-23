package ufrn.alvarofpp.ui.helpers;

import javafx.scene.Node;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

public class AnimationGenerator {
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -80;
    public static final double VISIBLE = 1.0;
    public static final double INVISIBLE = 0.0;
    private static final int DURATION = 500;

    public void applyTranslateAnimationOn(Node node) {
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.millis(this.DURATION), node);
        translateTransition.setFromX(DEFAULT_ENDING_X_POSITION);
        translateTransition.setToX(DEFAULT_STARTING_X_POSITION);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    public void applyFadeAnimationOn(Node node, double from, double to, EventHandler<ActionEvent> eventHandler) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(this.DURATION), node);
        fadeTransition.setOnFinished(eventHandler);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
}
