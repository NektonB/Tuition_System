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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.*;

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
    private TableView<TeacherSubjectsList> tblTeacher_Subjects;

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
    User user;
    UserType userType;

    TableView tblTeacher;
    HashMap<Integer, String> actionList = new HashMap<>();
    HashMap<Integer, String> subList = new HashMap<>();

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
            user = ObjectGenerator.getUser();
            userType = ObjectGenerator.getUserType();

            readySubjectTable();

            dataReader.fillStatusCombo(cmbStatus);
            dataReader.fillSubjectCombo(cmbSubjects);
            cmbStatus.setValue("ACTIVE");
            loadContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadContent() {
        if (teacher.getId() > 0) {
            txtFname.setText(teacher.getFname());
            txtLName.setText(teacher.getLname());
            txtNIC.setText(teacher.getNic_number());
            txtHomeNum.setText(teacher.getHome_number());
            txtMobileNum.setText(teacher.getMobile_number());
            txtEmail.setText(teacher.getEmail());
            txtAddress.setText(teacher.getAddress());
            cmbStatus.setValue(status.getStatus());

            ObservableList<TeacherSubjectsList> subjectsList = null;
            HashMap<Integer, String> subjects = teacherHasSubject.getSubjectList();
            Set<Integer> keySet = subjects.keySet();

            for (int key : keySet) {
                subjectsList = tblTeacher_Subjects.getItems();
                /*System.out.println("Sub ID : " + key);
                System.out.println("Sub Name : " + subjects.get(key));*/
                subjectsList.add(new TeacherSubjectsList(key, subjects.get(key)));
            }
            tblTeacher_Subjects.setItems(subjectsList);
        }
    }

    public void setWindowData(String title, String actionName, TableView tblTeacher) {
        lblTitle.setText(title);
        btnSU.setText(actionName);
        this.tblTeacher = tblTeacher;
    }

    private void readySubjectTable() {
        tcSubjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcSubjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
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
            if (!cmbSubjects.getValue().isEmpty()) {
                subject.setName(cmbSubjects.getValue());
                dataReader.getSubjectDetailsByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSubjects() {
        if (!cmbSubjects.getValue().isEmpty()) {
            boolean isAvailable = dataReader.checkSubjectIsAvailable(cmbSubjects.getValue());
            System.out.println(isAvailable);
            if (isAvailable) {
                Toolkit.getDefaultToolkit().beep();
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
            if (userType.getType().equals("Super Admin")) {
                int deleteSubject = 0;
                subject.setName(cmbSubjects.getValue());

                Optional<ButtonType> dialog = alerts.getConfirmationDialog("Warning", "Subject Delete", "Are you sure, you want to delete " + cmbSubjects.getValue() + " ?");
                if (dialog.get().equals(ButtonType.OK)) {
                    deleteSubject = dataWriter.deleteSubject();
                } else {

                }

                if (deleteSubject > 0) {
                    subject.resetAll();

                    dataReader.fillSubjectCombo(cmbSubjects);
                    alerts.getSuccessNotify("Subject Delete", "Congratulation Chief..!\nSubject delete successful");
                }
            }
        }
    }

    public void addSubjectToTable() {
        try {
            searchSubjectDetailsByName();

            ObservableList<TeacherSubjectsList> subjectsList;
            subjectsList = tblTeacher_Subjects.getItems();
            subjectsList.add(new TeacherSubjectsList(subject.getId(), subject.getName()));
            tblTeacher_Subjects.setItems(subjectsList);

            subList.put(subject.getId(), subject.getName());
            actionList.put(subject.getId(), "Save");

            subject.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeSubjectFromTable() {
        try {
            if (tblTeacher_Subjects.getItems().isEmpty()) {
                alerts.getWarningNotify("Warning !", "No more rows here...");
            } else {
                TeacherSubjectsList subject = tblTeacher_Subjects.getSelectionModel().getSelectedItem();

//                subList.remove(subject.getId(), subject.getName());
                subList.remove(subject.getId());
                actionList.remove(subject.getId());
                actionList.put(subject.getId(), "Delete");

                tblTeacher_Subjects.getItems().remove(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TblTeacher_Subjects_KeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            removeSubjectFromTable();
        }
    }

    public void setCmbSubjectsKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            addSubjectToTable();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveSubjects();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {
            updateSubjects();
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            deleteSubjects();
        }
    }

    private int saveSubjectList(int saveTeacher) {
        int saveSubjectList = 0;
        try {
            if (saveTeacher > 0) {
                if (!tblTeacher_Subjects.getItems().isEmpty()) {
                    ObservableList<? extends TableColumn<?, ?>> columns = tblTeacher_Subjects.getColumns();
                    for (int i = 0; i < tblTeacher_Subjects.getItems().size(); i++) {

                        subject.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));

                        saveSubjectList = dataWriter.saveTeacherSubjectList();

                        if (saveSubjectList > 0) {
                            subject.resetAll();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveSubjectList;
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
                    int saveTeacher = dataWriter.saveTeacher();
                    if (saveTeacher > 0) {

                        int saveSubjectList = saveSubjectList(saveTeacher);

                        if (saveSubjectList > 0) {
                            teacher.resetAll();
                            status.resetAll();

                            dataReader.fillTeacherTable(tblTeacher);
                            alerts.getSuccessNotify("Teacher Registration", "Congratulation Chief..!\nTeacher registration successful");

                            subList.clear();
                            actionList.clear();

                            closeMe();
                        }
                    }
                } else if (btnSU.getText().equals("Update")) {
                    int updateTeacher = dataWriter.updateTeacher();
                    if (updateTeacher > 0) {
                        int operation = 0;

                        for (int key : actionList.keySet()) {
                            if (actionList.get(key).equals("Save")) {
                                subject.setId(key);
                                operation = dataWriter.saveTeacherSubjectList();

                            } else if (actionList.get(key).equals("Delete")) {
                                subject.setId(key);
                                operation = dataWriter.deleteTeacherSubject();
                            }
                        }

                        if (operation > 0) {
                            teacher.resetAll();
                            status.resetAll();

                            dataReader.fillTeacherTable(tblTeacher);
                            alerts.getSuccessNotify("Teacher Update", "Congratulation Chief..!\nTeacher update successful");

                            subList.clear();
                            actionList.clear();

                            closeMe();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFrmTeacher_KeyReleased(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S) && !(cmbSubjects.isFocused())) {
            saveTeacher();
        }
    }

    public void closeMe() {
        teacher.resetAll();
        status.resetAll();
        teacherHasSubject.resetAll();
        subject.resetAll();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
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
}
