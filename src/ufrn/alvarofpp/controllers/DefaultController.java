package ufrn.alvarofpp.controllers;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ufrn.alvarofpp.controllers.helpers.Offset;

abstract class DefaultController implements Initializable {

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
     * @param ui Interface de usuário
     * @param offset Coordenadas da interface
     */
    abstract void makeStageDrageable(HBox ui, Offset offset);

}
