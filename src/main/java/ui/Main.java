package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Peter on 14.09.2016.
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Controller.fxml"));
            primaryStage.setTitle("Надежность программного обеспечения");
            primaryStage.setScene(new Scene(root, 928, 612));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().log(
                    Level.WARNING, getClass().getClassLoader().getResource("Controller.fxml") + "not found");
        }
    }
}
