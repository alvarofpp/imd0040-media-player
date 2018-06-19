package ufrn.alvarofpp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import ufrn.alvarofpp.controllers.helpers.Coordinates;

abstract class DefaultController implements Initializable {

    /**
     * Coordenadas da tela
     */
    Coordinates coordinates;

    /**
     * Fecha o aplicativo
     * @param event
     */
    @FXML
    private void handleExitApp(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Torna a interface arrastavel
     */
    abstract void makeStageDrageable();

}
