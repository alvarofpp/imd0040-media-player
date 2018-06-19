package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.scene.layout.HBox;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ufrn.alvarofpp.db.files.Users;
import ufrn.alvarofpp.ui.LoginUI;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import ufrn.alvarofpp.controllers.helpers.Coordinates;

public class LoginController extends DefaultController {

    /**
     * Interface de usuário
     */
    @FXML
    private HBox loginui;
    /**
     * Campo que conterá o username do usuário
     */
    @FXML
    private JFXTextField username;
    /**
     * Campo que conterá a senha do usuário
     */
    @FXML
    private JFXPasswordField password;

    /**
     * Usuários
     */
    Users users;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preference = Preferences.getPreferences();
        coordinates = new Coordinates();
        makeStageDrageable();
        this.users = new Users();
    }

    /**
     * Realiza o login no sistema
     * @param event
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = this.username.getText();
        String password = this.password.getText();

        if (username.equals("alvarofpp") && password.equals("senha123")) {
            System.out.println("Autenticado");
        } else {
            System.out.println("Não autenticado");
        }
    }

    @FXML
    private void callRegister(ActionEvent event) {

    }

    /**
     * Torna a interface arrastavel
     */
    @Override
    protected void makeStageDrageable() {
        loginui.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coordinates.setxOffset(event.getSceneX());
                coordinates.setyOffset(event.getSceneY());
            }
        });
        loginui.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LoginUI.stage.setX(event.getScreenX() - coordinates.getxOffset());
                LoginUI.stage.setY(event.getScreenY() - coordinates.getyOffset());
                LoginUI.stage.setOpacity(0.7f);
            }
        });
        loginui.setOnDragDone((e) -> {
            LoginUI.stage.setOpacity(1.0f);
        });
        loginui.setOnMouseReleased((e) -> {
            LoginUI.stage.setOpacity(1.0f);
        });
    }

}
