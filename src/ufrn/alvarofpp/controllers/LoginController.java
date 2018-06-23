package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ufrn.alvarofpp.db.files.Users;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;
import ufrn.alvarofpp.exceptions.UserExistException;
import ufrn.alvarofpp.ui.LoginUI;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import ufrn.alvarofpp.controllers.helpers.Coordinates;
import ufrn.alvarofpp.ui.helpers.AnimationGenerator;

public class LoginController extends DefaultController {
    /**
     * Interface de usuário
     */
    @FXML
    private HBox loginui;
    /**
     * Campo que conterá o username do usuário, Login e Register respectivamente
     */
    @FXML
    private JFXTextField loginUsername, registerUsername;
    /**
     * Campo que conterá a senha do usuário, Login e Register respectivamente
     */
    @FXML
    private JFXPasswordField loginPassword, registerPassword;

    /**
     * Interface de registrar novo usuário
     */
    @FXML
    private Pane sideRegister;

    /**
     * Usuários
     */
    private Users users;

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

    /**
     * Chama a interface de registrar novo usuário
     * @param event
     */
    @FXML
    private void callRegister(ActionEvent event) {
        if (!this.sideRegister.isVisible()) {
            this.sideRegister.setVisible(true);
            animationGenerator.applyTranslateAnimationOn(this.sideRegister);
            animationGenerator.applyFadeAnimationOn(this.sideRegister, AnimationGenerator.INVISIBLE, AnimationGenerator.VISIBLE, null);
        } else {
            animationGenerator.applyFadeAnimationOn(this.sideRegister, AnimationGenerator.VISIBLE, AnimationGenerator.INVISIBLE, (e) -> {
                this.sideRegister.setVisible(false);
            });
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = this.registerUsername.getText();
        String password = this.registerPassword.getText();

        try {
            // Cria o novo usuário
            this.users.create(username, password);

            // Alerta de sucesso
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Registro");
            alertSuccess.setHeaderText("Usuário criado com sucesso!");
            alertSuccess.setContentText("O usuário de nome " + username + " foi criado com sucesso!");
            alertSuccess.showAndWait();

            // Fecha a janela
            animationGenerator.applyFadeAnimationOn(this.sideRegister, AnimationGenerator.VISIBLE, AnimationGenerator.INVISIBLE, (e) -> {
                this.sideRegister.setVisible(false);
            });
            // Limpa os campos
            this.registerUsername.setText(null);
            this.registerPassword.setText(null);
        } catch (FieldNotFoundException | UserExistException e) {
            // Alerta de erro
            e.printStackTrace();
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("Alerta");
            alertWarning.setHeaderText(null);
            alertWarning.setContentText(e.getMessage());
            alertWarning.showAndWait();
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
            LoginUI.stage.setOpacity(AnimationGenerator.VISIBLE);
        });
        loginui.setOnMouseReleased((e) -> {
            LoginUI.stage.setOpacity(AnimationGenerator.VISIBLE);
        });

    }
    
}
