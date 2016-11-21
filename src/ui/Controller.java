package ui;

import java.lang.String;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixFileCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixClassCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixMethodCalculator;
import bl.model.IAnalyticFile;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticMethod;
import bl.model.impl.AnalyticFile;
import bl.model.impl.AnalyticClass;
import bl.model.impl.AnalyticMethod;

import com.github.javaparser.ParseException;

/**
 * Created by Radislav on 20.11.2016.
 */

public class Controller implements Initializable {
    @FXML
    Pane rootPane;
    @FXML
    Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void onClick(ActionEvent actionEvent) throws IOException, ParseException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java files", "*.java"));
        File file = fileChooser.showOpenDialog(button.getScene().getWindow());
        if (file != null) {
            IAnalyticFile analyticFile = new AnalyticFile(file);
            IHalsteadMetrixCalculator <IAnalyticFile> fileCalculator = new HalsteadMetrixFileCalculator(analyticFile);
            fileCalculator.calculateMetrix();
            int val = fileCalculator.getNumberOfUniqueOperands();
            String str = String.valueOf(val);
            showAlert(str);
        } else {
            // showAlert("file not selected!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
