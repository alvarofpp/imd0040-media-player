package ufrn.alvarofpp.controllers;

import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;

public class UiController {

    /**
     * Fecha o aplicativo
     * @param event
     */
    @FXML
    private void handleExitApp(MouseEvent event) {
        System.exit(0);
    }

}
