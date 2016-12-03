package ui;

import java.lang.String;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import bl.model.IAnalytic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML
    CheckBox onlyClassCheckBox;
    @FXML
    ComboBox <String> onlyClassComboBox;
    @FXML
    CheckBox onlyFunctionCheckBox;
    @FXML
    ComboBox <String> onlyFunctionComboBox;

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
            boolean disabled = (textArea.getText().length() == 0);
            onlyClassCheckBox.setDisable(disabled);
            calculateMetrixButton.setDisable(disabled);
            clearControls();
            onlyClassComboBox.setDisable(true);
            onlyClassCheckBox.setSelected(false);
            resultListView.getItems().clear();
        } catch (IOException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onClickOnlyClassCheckBox(ActionEvent actionEvent) {
        boolean disabled = !onlyClassCheckBox.isSelected();
        onlyClassComboBox.setDisable(disabled);
        if (onlyClassCheckBox.isSelected()) {
            try {
                IAnalyticFile analyticFile = new AnalyticFile(file);
                List <IAnalyticClass> analyticClasses = analyticFile.getClasses();
                ObservableList <String> items = FXCollections.observableArrayList();
                for (int index = 0; index < analyticClasses.size(); index++) {
                    IAnalyticClass analyticClass = analyticClasses.get(index);
                    items.add(analyticClass.getName());
                }
                onlyClassComboBox.setItems(items);
            } catch (IOException | ParseException e) {
                showAlert(e.getMessage());
                e.printStackTrace();
            }
        } else {
            clearControls();
        }
    }

    public void onClickOnlyClassComboBox(ActionEvent actionEvent) {
        onlyFunctionCheckBox.setDisable(false);
            try {
                IAnalyticFile analyticFile = new AnalyticFile(file);
                List <IAnalyticClass> analyticClasses = analyticFile.getClasses();
                ObservableList <String> items = FXCollections.observableArrayList();
                for (int i = 0; i < analyticClasses.size(); i++) {
                    IAnalyticClass analyticClass = analyticClasses.get(i);
                    if (analyticClass.getName().equals(onlyClassComboBox.getSelectionModel().getSelectedItem())) {
                        for (int j = 0; j < analyticClass.getMethods().size(); j++) {
                            IAnalyticMethod analyticMethod = analyticClass.getMethods().get(j);
                            items.add(analyticMethod.getName());
                        }
                        onlyFunctionComboBox.setItems(items);
                        break;
                    }
                }
            } catch (IOException | ParseException e) {
                showAlert(e.getMessage());
                e.printStackTrace();
            }
    }

    public void onClickOnlyFunctionCheckBox(ActionEvent actionEvent) {
        boolean disabled = !onlyFunctionCheckBox.isSelected();
        onlyFunctionComboBox.setDisable(disabled);
        if (disabled) {
            onlyFunctionComboBox.valueProperty().set(null);
        }
    }

    public void onClickCalculateMetrix(ActionEvent actionEvent) {
        try {
            IHalsteadMetrixCalculator <?extends IAnalytic> calculator;
            IAnalyticFile analyticFile = new AnalyticFile(file);
            calculator = new HalsteadMetrixFileCalculator(analyticFile);

            if (!(!onlyFunctionComboBox.getSelectionModel().isEmpty() && onlyFunctionComboBox.getSelectionModel().getSelectedItem().length() > 0)) {
                List <IAnalyticClass> analyticClasses = analyticFile.getClasses();
                Optional<IAnalyticClass> iAnalyticClassOptional = analyticClasses.stream().filter(item->item.getName().equals(onlyClassComboBox.getSelectionModel().getSelectedItem())).findFirst();
                if (iAnalyticClassOptional.isPresent()){
                    calculator = new HalsteadMetrixClassCalculator(iAnalyticClassOptional.get());
                }
            } else if (!onlyClassComboBox.getSelectionModel().isEmpty() && onlyClassComboBox.getSelectionModel().getSelectedItem().length() > 0) {
                List <IAnalyticClass> analyticClasses = analyticFile.getClasses();
                Optional<IAnalyticClass> iAnalyticClassOptional = analyticClasses.stream().filter(item->item.getName().equals(onlyClassComboBox.getSelectionModel().getSelectedItem())).findFirst();
                if (iAnalyticClassOptional.isPresent()) {
                    List <IAnalyticMethod> analyticMethods = iAnalyticClassOptional.get().getMethods();
                    Optional<IAnalyticMethod> iAnalyticMethodOptional = analyticMethods.stream().filter(item->item.getName().equals(onlyFunctionComboBox.getSelectionModel().getSelectedItem())).findFirst();
                    if (iAnalyticMethodOptional.isPresent()) {
                        calculator = new HalsteadMetrixMethodCalculator(iAnalyticMethodOptional.get());
                    }
                }
            }

            calculator.calculateMetrix();

            int numberOfUniqueOperators = calculator.getNumberOfUniqueOperators();
            String numberOfUniqueOperatorsString = String.format("Число уникальных операторов: %d", numberOfUniqueOperators);

            int numberOfUniqueOperands = calculator.getNumberOfUniqueOperands();
            String numberOfUniqueOperandsString = String.format("Число уникальных операндов: %d", numberOfUniqueOperands);

            int numberOfOperators = calculator.getNumberOfOperators();
            String numberOfOperatorsString = String.format("Общее число операторов: %d", numberOfOperators);

            int numberOfOperands = calculator.getNumberOfOperands();
            String numberOfOperandsString = String.format("Общее число операндов: %d", numberOfOperands);

            int programVocabulary = calculator.getProgramVocabulary();
            String programVocabularyString = String.format("Словарь программы: %d", programVocabulary);

            int programLength = calculator.getProgramLength();
            String programLengthString = String.format("Длина программы: %d", programLength);

            double programVolume = calculator.getProgramVolume();
            String programVolumeString = String.format("Объем программы: %.2f", programVolume);

            int errorsCount = calculator.getErrorsCount();
            String errorsCountString = String.format("Количество ошибок: %d", errorsCount);

            ObservableList <String> items = FXCollections.observableArrayList(numberOfUniqueOperatorsString,
                    numberOfUniqueOperandsString,
                    numberOfOperatorsString,
                    numberOfOperandsString,
                    programVocabularyString,
                    programLengthString,
                    programVolumeString,
                    errorsCountString);
            resultListView.setItems(items);
        } catch (IOException | ParseException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearControls() {
        onlyClassComboBox.getItems().clear();
        onlyFunctionComboBox.getItems().clear();
        onlyFunctionComboBox.setDisable(true);
        onlyFunctionCheckBox.setSelected(false);
        onlyFunctionCheckBox.setDisable(true);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exception");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
