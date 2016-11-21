package ui;

import java.lang.String;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    Button selectFileButton;
    @FXML
    TextArea textArea;
    @FXML
    Button calculateMetrixButton;
    @FXML
    ListView resultListView;

    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void onClickSelectFile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java files", "*.java"));
            file = fileChooser.showOpenDialog(selectFileButton.getScene().getWindow());
            String fileString = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            textArea.setText(fileString);
            calculateMetrixButton.setDisable(false);
        } catch (IOException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onClickCalculateMetrix(ActionEvent actionEvent) {
        IAnalyticFile analyticFile = null;
        try {
            analyticFile = new AnalyticFile(file);
            IHalsteadMetrixCalculator <IAnalyticFile> fileCalculator = new HalsteadMetrixFileCalculator(analyticFile);
            fileCalculator.calculateMetrix();

            int numberOfUniqueOperators = fileCalculator.getNumberOfUniqueOperators();
            String numberOfUniqueOperatorsString = String.format("Number of Unique Operators: %d", numberOfUniqueOperators);

            int numberOfUniqueOperands = fileCalculator.getNumberOfUniqueOperands();
            String numberOfUniqueOperandsString = String.format("Number of Unique Operands: %d", numberOfUniqueOperands);

            int numberOfOperators = fileCalculator.getNumberOfOperators();
            String numberOfOperatorsString = String.format("Number of Operators: %d", numberOfOperators);

            int numberOfOperands = fileCalculator.getNumberOfOperands();
            String numberOfOperandsString = String.format("Number of Operands: %d", numberOfOperands);

            int programVocabulary = fileCalculator.getProgramVocabulary();
            String programVocabularyString = String.format("Program Vocabulary: %d", programVocabulary);

            int programLength = fileCalculator.getProgramLength();
            String programLengthString = String.format("Program Length: %d", programLength);

            double programVolume = fileCalculator.getProgramVolume();
            String programVolumeString = String.format("Program Volume: %.2f", programVolume);

            ObservableList <String> items = FXCollections.observableArrayList(numberOfUniqueOperatorsString,
                    numberOfUniqueOperandsString,
                    numberOfOperatorsString,
                    numberOfOperandsString,
                    programVocabularyString,
                    programLengthString,
                    programVolumeString);
            resultListView.setItems(items);
        } catch (IOException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exception");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
