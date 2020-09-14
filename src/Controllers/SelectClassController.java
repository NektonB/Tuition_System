package Controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectClassController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXComboBox<String> cmbExamYear;

    @FXML
    private JFXComboBox<String> cmbSubject;

    @FXML
    private JFXComboBox<String> cmbTeacher;

    @FXML
    private JFXComboBox<String> cmbClassType;

    @FXML
    private TableView<?> tblClass;

    @FXML
    private TableColumn<?, ?> tcStream;

    @FXML
    private TableColumn<?, ?> tcExamYeear;

    @FXML
    private TableColumn<?, ?> tcSubject;

    @FXML
    private TableColumn<?, ?> tcTeacher;

    @FXML
    private TableColumn<?, ?> tcClassType;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
