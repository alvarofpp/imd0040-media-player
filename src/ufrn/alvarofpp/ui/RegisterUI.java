package ufrn.alvarofpp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterUI extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("xml/register.fxml"));
        stage.setTitle("Register new user");
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        this.stage = stage;
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}