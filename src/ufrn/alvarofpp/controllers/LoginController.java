package ufrn.alvarofpp.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ufrn.alvarofpp.controllers.helpers.Offset;
import ufrn.alvarofpp.ui.LoginUI;
import ufrn.alvarofpp.ui.MediaPlayerUI;

public class LoginController extends DefaultController {

    //private final static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());

    @FXML
    private HBox loginui;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    private Offset offset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preference = Preferences.getPreferences();
        offset = new Offset();
        makeStageDrageable(loginui, offset);
    }

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

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/library/assistant/ui/main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Assistant");
            stage.setScene(new Scene(parent));
            stage.show();
            //LibraryAssistantUtil.setStageIcon(stage);
        }
        catch (IOException ex) {
            //LOGGER.log(Level.ERROR, "{}", ex);
        }

    }

    /**
     * Torna a interface arrastavel
     * @param ui Interface de usuário
     * @param offset Coordenadas da interface
     */
    @Override
    protected void makeStageDrageable(HBox ui, Offset offset) {

        ui.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                offset.setxOffset(event.getSceneX());
                offset.setyOffset(event.getSceneY());
            }
        });
        ui.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LoginUI.stage.setX(event.getScreenX() - offset.getxOffset());
                LoginUI.stage.setY(event.getScreenY() - offset.getyOffset());
                LoginUI.stage.setOpacity(0.7f);
            }
        });
        ui.setOnDragDone((e) -> {
            LoginUI.stage.setOpacity(1.0f);
        });
        ui.setOnMouseReleased((e) -> {
            LoginUI.stage.setOpacity(1.0f);
        });
    }

}
