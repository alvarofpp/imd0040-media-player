package ufrn.alvarofpp.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import ufrn.alvarofpp.ui.LoginUI;
import ufrn.alvarofpp.db.models.User;
import ufrn.alvarofpp.db.files.Users;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import ufrn.alvarofpp.ui.MediaPlayerUI;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import ufrn.alvarofpp.controllers.helpers.Alerts;
import ufrn.alvarofpp.exceptions.UserExistException;
import ufrn.alvarofpp.controllers.helpers.Coordinates;
import ufrn.alvarofpp.exceptions.FieldNotFoundException;
import ufrn.alvarofpp.controllers.helpers.AnimationGenerator;

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
    /**
     * Alertas
     */
    private Alerts alerts;

    /**
     * Usuário logado
     */
    static User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preference = Preferences.getPreferences();
        this.coordinates = new Coordinates();
        makeStageDrageable();
        this.sideRegister.setVisible(false);
        this.users = new Users();
        this.animationGenerator = new AnimationGenerator();
        this.alerts = new Alerts();
    }

    /**
     * Realiza o login no sistema.
     * @param event
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = this.loginUsername.getText();
        String password = this.loginPassword.getText();

        try {
            if (this.users.validateUser(username, password)) {
                // Pega o usuário que se autenticou
                this.user = this.users.getUser(username);
                // Chama a nova tela
                MediaPlayerUI mediaPlayerUI = new MediaPlayerUI();
                mediaPlayerUI.start(new Stage());
                // Envia o usuário autenticado
                // Fecha a janela atual
                LoginUI.stage.close();
            } else {
                this.alerts.show(1, "Aviso", null, "Usuário " + username + " não foi encontrado no sistema.");
            }
        } catch (FieldNotFoundException e) {
            this.alerts.show(2, "Alerta!!!", null, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chama a interface de registrar novo usuário.
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

    /**
     * Realiza o processo de registro de um novo usuário.
     * @param event
     */
    @FXML
    private void handleRegister(ActionEvent event) {
        String username = this.registerUsername.getText();
        String password = this.registerPassword.getText();

        try {
            // Cria o novo usuário
            this.users.create(username, password);

            // Alerta de sucesso
            this.alerts.show(1, "Registro",
                    "Usuário criado com sucesso!",
                    "O usuário de nome " + username + " foi criado com sucesso!");

            // Fecha a janela
            animationGenerator.applyFadeAnimationOn(this.sideRegister, AnimationGenerator.VISIBLE, AnimationGenerator.INVISIBLE, (e) -> {
                this.sideRegister.setVisible(false);
            });
            // Limpa os campos
            this.registerUsername.setText(null);
            this.registerPassword.setText(null);

        } catch (FieldNotFoundException | UserExistException e) {
            // Alerta de erro
            this.alerts.show(2, "Alerta!!!", null, e.getMessage());
        }
    }

    /**
     * Torna a interface arrastavel.
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
