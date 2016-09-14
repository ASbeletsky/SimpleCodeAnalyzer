package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Peter on 14.09.2016.
 */

public class Controller implements Initializable {
    @FXML
    Pane rootPane;
    @FXML
    TextField editText;
    @FXML
    Button button;

    private Stage stage;

    public void onClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java files", "*.java"));
        File file = fileChooser.showOpenDialog(button.getScene().getWindow());

        if(file != null) {

        } else {
            showAlert("file not selected!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editText.setText("RuntimeText");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
