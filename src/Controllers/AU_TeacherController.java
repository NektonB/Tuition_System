package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;


    Status status;
    Teacher teacher;
    TeacherHasSubject teacherHasSubject;
    Subject subject;

    TableView tblEmployee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();

            teacher = ObjectGenerator.getTeacher();
            teacherHasSubject = ObjectGenerator.getTeacherHasSubject();
            status = ObjectGenerator.getStatus();
            subject = ObjectGenerator.getSubject();

            dataReader.fillStatusCombo(cmbStatus);
            dataReader.fillSubjectCombo(cmbSubjects);
//            loadContent();
            cmbStatus.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchStatusDetailsByStatus() {
        try {
            if (!cmbStatus.getValue().isEmpty()) {
                status.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public boolean checkValidate() {
        boolean validate = false;
        if (!txtFname.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtNIC.getText().isEmpty() && !cmbStatus.getValue().isEmpty()) {
            validate = true;
        } else {
            txtFname.setStyle("-fx-border-color: crimson");
            txtLName.setStyle("-fx-border-color: crimson");
            txtNIC.setStyle("-fx-border-color: crimson");
            cmbStatus.setStyle("-fx-border-color: crimson");
            alerts.getErrorNotify("Error", "Please fill required fields *");
        }
        return validate;
    }

    public void resetBoarders() {
        txtFname.setStyle("-fx-border-color: none");
        txtLName.setStyle("-fx-border-color: none");
        txtNIC.setStyle("-fx-border-color: none");
        cmbStatus.setStyle("-fx-border-color: none");
    }

    public void searchSubjectDetailsByName() {
        try {
            subject.setName(cmbSubjects.getValue());
            dataReader.getSubjectDetailsByName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSubjects() {
        if (!cmbSubjects.getValue().isEmpty()) {
            boolean isAvailable = dataReader.checkSubjectIsAvailable(cmbSubjects.getValue());

            if (isAvailable) {
                alerts.getWarningNotify("Subject Registration", "Careful Chief..!\n" + cmbSubjects.getValue() + " is already available");
            } else {
                subject.setName(cmbSubjects.getValue());
                int saveSubject = dataWriter.saveSubject();
                if (saveSubject > 0) {
                    subject.resetAll();

                    dataReader.fillSubjectCombo(cmbSubjects);
                    alerts.getSuccessNotify("Subject Registration", "Congratulation Chief..!\nSubject registration successful");
                }
            }
        }
    }

    public void updateSubjects() {
        if (!cmbSubjects.getValue().isEmpty()) {
            subject.setName(cmbSubjects.getValue());
            int updateSubject = dataWriter.updateSubject();
            if (updateSubject > 0) {
                subject.resetAll();

                dataReader.fillSubjectCombo(cmbSubjects);
                alerts.getSuccessNotify("Subject Update", "Congratulation Chief..!\nSubject update successful");
            }
        }
    }

    public void deleteSubjects() {
        if (!cmbSubjects.getValue().isEmpty()) {
            int deleteSubject = dataWriter.deleteSubject();
            if (deleteSubject > 0) {
                subject.resetAll();

                dataReader.fillSubjectCombo(cmbSubjects);
                alerts.getSuccessNotify("Subject Delete", "Congratulation Chief..!\nSubject delete successful");
            }
        }
    }

    public void setCmbSubjectsKeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveSubjects();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {
            updateSubjects();
        }
    }

    public void saveTeacher() {
        try {
            if (checkValidate()) {
                teacher.setFname(txtFname.getText());
                teacher.setLname(txtLName.getText());
                teacher.setNic_number(txtNIC.getText());
                teacher.setAddress(txtAddress.getText());
                teacher.setHome_number(txtHomeNum.getText());
                teacher.setMobile_number(txtMobileNum.getText());
                teacher.setEmail(txtEmail.getText());


                status.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                if (btnSU.getText().equals("Save")) {
                    int saveEmployee = dataWriter.saveEmployee();
                    if (saveEmployee > 0) {
                        teacher.resetAll();
                        status.resetAll();

                        dataReader.fillEmployeeTable(tblEmployee);
                        //alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                        alerts.getSuccessNotify("Employee Registration", "Congratulation Chief..!\nEmployee registration successful");

                        closeMe();
                    }
                } else if (btnSU.getText().equals("Update")) {
                    int updateEmployee = dataWriter.updateEmployee();
                    if (updateEmployee > 0) {
                        teacher.resetAll();
                        status.resetAll();

                        dataReader.fillEmployeeTable(tblEmployee);
                        //alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                        alerts.getSuccessNotify("Employee Update", "Congratulation Chief..!\nEmployee update successful");

                        closeMe();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
