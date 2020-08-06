package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AU_TeacherController implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private JFXTextField txtFname;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtMobileNum;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtLName;

    @FXML
    private JFXTextField txtHomeNum;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXComboBox<String> cmbSubjects;

    @FXML
    private TableView<?> tblTeacher_Subjects;

    @FXML
    private TableColumn<TeacherSubjectsList, Integer> tcSubjectId;

    @FXML
    private TableColumn<TeacherSubjectsList, String> tcSubjectName;

    @FXML
    private JFXButton btnSU;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static class TeacherSubjectsList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;

        public TeacherSubjectsList(int id, String name) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }
    }

    public void closeMe() {
        /*employee.resetAll();
        status.resetAll();*/
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
