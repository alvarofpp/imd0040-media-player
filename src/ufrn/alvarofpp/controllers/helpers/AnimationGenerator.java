package ufrn.alvarofpp.controllers.helpers;

import javafx.scene.Node;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;

/**
 * Essa classe é usada para fazer animações com as telas.
 */
public class AnimationGenerator {
    /**
     * Posição inicial da tela na animação
     */
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    /**
     * Posição final da tela na animação
     */
    private static final int DEFAULT_ENDING_X_POSITION = -80;
    /**
     * Valor para "visivel"
     */
    public static final double VISIBLE = 1.0;
    /**
     * Valor para "invisivel"
     */
    public static final double INVISIBLE = 0.0;
    /**
     * Duração de animação
     */
    private static final int DURATION = 500;

    /**
     * Aplica a animação na interface.
     * @param node Interface que acontecerá a animação
     */
    public void applyTranslateAnimationOn(Node node) {
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.millis(this.DURATION), node);
        translateTransition.setFromX(DEFAULT_ENDING_X_POSITION);
        translateTransition.setToX(DEFAULT_STARTING_X_POSITION);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }

    /**
     * Aplica a animação de aparecer e desaparecer na interface desejada.
     * @param node Interface que acontecerá a animação
     * @param from Valor inicial de visibilidade
     * @param to Valor final de visibilidade
     * @param eventHandler Evento
     */
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
