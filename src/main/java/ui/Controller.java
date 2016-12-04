package ui;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixClassCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixFileCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixMethodCalculator;
import bl.metrix.mccabe.service.CompilerService;
import bl.metrix.mccabe.service.SyntaxTreeService;
import bl.metrix.mccabe.service.impl.CompilerServiceImpl;
import bl.metrix.mccabe.service.impl.CyclomaticComplexityService;
import bl.metrix.mccabe.service.impl.SyntaxTreeServiceImpl;
import bl.model.IAnalytic;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import bl.model.IAnalyticMethod;
import bl.model.impl.AnalyticFile;
import bl.model.impl.AnalyticMethod;
import bl.model.impl.SourceFile;
import com.github.javaparser.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import ui.mccabe.graph.McCabeGraph;
import ui.mccabe.graph.McCabeGraphDisplayer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Peter on 20.11.2016.
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
    @FXML
    TableView tableView;

    private ObservableList <SourceFile> history;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        history = FXCollections.observableArrayList();
        ObservableList <TableColumn> columns = tableView.getColumns();
        TableColumn column = columns.get(0);
        column.setCellValueFactory(new PropertyValueFactory<SourceFile, String>("name"));
        column = columns.get(1);
        column.setCellValueFactory(new PropertyValueFactory<SourceFile, String>("lastModified"));
    }

    public void onClickSelectFile(ActionEvent actionEvent) throws ParseException {
        try {
            File file = openFile();
            String name = file.getName().replace(".java","");
            Date date = new Date(file.lastModified());
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String lastModified = format.format(date);
            String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            SourceFile sourceFile = new SourceFile(name, lastModified, content, file);
            history.add(sourceFile);
            tableView.setItems(history);
            tableView.getSelectionModel().select(history.size() - 1);
            changeSourceFile();

            try {
                String graphSrc = content;
                int packageIndex = graphSrc.indexOf("package");
                if(packageIndex >= 0) {
                    graphSrc = graphSrc.substring(graphSrc.indexOf(";"));
                }
                McCabeGraph graph = new McCabeGraph(graphSrc);
                new McCabeGraphDisplayer(graph).displayGraphInJFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                IAnalyticFile analyticFile = new AnalyticFile(currentSourceFile().file);
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
                IAnalyticFile analyticFile = new AnalyticFile(currentSourceFile().file);
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
            IAnalyticFile analyticFile = new AnalyticFile(currentSourceFile().file);
            calculator = new HalsteadMetrixFileCalculator(analyticFile);

            CyclomaticComplexityService cyclomaticComplexityservice = new CyclomaticComplexityService();
            SyntaxTreeService syntaxTreeService = new SyntaxTreeServiceImpl();
            CompilerService compilerService = new CompilerServiceImpl();

            File compiledFile = compilerService.compileJavaSource(new File(currentSourceFile().file.getPath()));
            InputStream inputStream = new BufferedInputStream(new FileInputStream(compiledFile));
            ClassNode classNode = syntaxTreeService.buildSyntaxTreeForClass(inputStream);
            long complexity = cyclomaticComplexityservice.calculateClassComplexity(classNode);

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
                        AnalyticMethod method = (AnalyticMethod) iAnalyticMethodOptional.get();
                        Optional<MethodNode> methodNode = syntaxTreeService.getMethodFromClass(classNode, method.getName());
                        if (methodNode.isPresent()) {
                            complexity = cyclomaticComplexityservice.calculateMethodComplexity(methodNode.get());
                        }
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

            String complexityString = String.format("Цикломатическая сложность: %d", complexity);

            ObservableList <String> items = FXCollections.observableArrayList(numberOfUniqueOperatorsString,
                    numberOfUniqueOperandsString,
                    numberOfOperatorsString,
                    numberOfOperandsString,
                    programVocabularyString,
                    programLengthString,
                    programVolumeString,
                    errorsCountString,
                    complexityString);
            resultListView.setItems(items);
        } catch (IOException | ParseException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onClickTableView(MouseEvent mouseEvent) {
        changeSourceFile();
    }

    private File openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java files", "*.java"));
        return fileChooser.showOpenDialog(selectFileButton.getScene().getWindow());
    }

    private SourceFile currentSourceFile() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        index = index >= 0 ? index : 0;
        SourceFile sourceFile = history.get(index);
        return sourceFile;
    }

    private void changeSourceFile() {
        textArea.setText(currentSourceFile().getContent());
        changeControlsState();
    }

    private void changeControlsState() {
        boolean disabled = (textArea.getText().length() == 0);
        onlyClassCheckBox.setDisable(disabled);
        calculateMetrixButton.setDisable(disabled);
        clearControls();
        onlyClassComboBox.setDisable(true);
        onlyClassCheckBox.setSelected(false);
        resultListView.getItems().clear();
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
