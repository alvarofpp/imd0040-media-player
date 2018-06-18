package ufrn.alvarofpp.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController extends UiController implements Initializable {

    //private final static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //preference = Preferences.getPreferences();
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

}
