package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
import ufrn.alvarofpp.ui.helpers.AnimationGenerator;

public class LoginController extends DefaultController {
    private static final int DEFAULT_STARTING_X_POSITION = 0;
    private static final int DEFAULT_ENDING_X_POSITION = -120;

    /**
     * Interface de usuário
     */
    @FXML
    private HBox loginui;
    /**
     * Campo que conterá o username do usuário
     */
    @FXML
    private JFXTextField loginUsername;
    /**
     * Campo que conterá a senha do usuário
     */
    @FXML
    private JFXPasswordField loginPassword;
    /**
     * Interface de registrar novo usuário
     */
    @FXML
    private Pane sideRegister;

    /**
     * Usuários
     */
    Users users;

    /**
     * Animações
     */
    private AnimationGenerator animationGenerator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preference = Preferences.getPreferences();
        this.coordinates = new Coordinates();
        makeStageDrageable();
        this.sideRegister.setVisible(false);
        this.users = new Users();
        this.animationGenerator = new AnimationGenerator();
    }

    /**
     * Realiza o login no sistema
     * @param event
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = this.loginUsername.getText();
        String password = this.loginPassword.getText();

        if (this.users.validateUser(username, password)) {
            System.out.println("Autenticado");
        } else {
            System.out.println("Não autenticado");
        }
    }

    @FXML
    private void callRegister(ActionEvent event) {
        if (!this.sideRegister.isVisible()) {
            this.sideRegister.setVisible(true);
            animationGenerator.applyTranslateAnimationOn(this.sideRegister, 500, DEFAULT_ENDING_X_POSITION, DEFAULT_STARTING_X_POSITION);
            animationGenerator.applyFadeAnimationOn(this.sideRegister, 600, 0f, 1.0f, null);
        } else {
            animationGenerator.applyFadeAnimationOn(this.sideRegister, 600, 1.0f, 0.0f, (e) -> {
                this.sideRegister.setVisible(false);
            });
        }
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
