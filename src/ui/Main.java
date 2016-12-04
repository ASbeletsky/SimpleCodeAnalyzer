package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Peter on 14.09.2016.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Controller.fxml"));
        primaryStage.setTitle("Надежность программного обеспечения");
        primaryStage.setScene(new Scene(root, 928, 612));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
