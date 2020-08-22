package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.NearCity;
import Modules.School;
import Modules.UserType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AU_StudentController implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private JFXTextField txtFName;

    @FXML
    private JFXTextField txtLName;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtHome;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXComboBox<String> cmbNearCity;

    @FXML
    private JFXComboBox<String> cmbSchool;

    @FXML
    private JFXComboBox<String> cmbStream;

    @FXML
    private JFXComboBox<String> cmbGrade;

    @FXML
    private JFXComboBox<String> cmbExamYear;

    @FXML
    private JFXComboBox<String> cmbSubject;

    @FXML
    private JFXComboBox<String> cmbTeacher;

    @FXML
    private TableView<SubjectList> tblSubjectInfo;

    @FXML
    private TableColumn<SubjectList, Integer> tcSubId;

    @FXML
    private TableColumn<SubjectList, String> tcSubject;

    @FXML
    private TableColumn<SubjectList, String> tcTeacher;

    @FXML
    private TableColumn<SubjectList, String> tcTheory;

    @FXML
    private TableColumn<SubjectList, String> tcRevision;

    @FXML
    private JFXComboBox<String> cmbParents;

    @FXML
    private FontAwesomeIconView btnAddParent;

    @FXML
    private TableView<ParentList> tblParent;

    @FXML
    private TableColumn<ParentList, Integer> tcId;

    @FXML
    private TableColumn<ParentList, String> tcName;

    @FXML
    private TableColumn<ParentList, String> tcHome;

    @FXML
    private TableColumn<ParentList, String> tcMobile;

    @FXML
    private JFXButton btnSave;


    DataReader dataReader;
    DataWriter dataWriter;
    Alerts alerts;

    NearCity nearCity;
    UserType userType;
    School school;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            dataWriter = ObjectGenerator.getDataWriter();

            nearCity = ObjectGenerator.getNearCity();
            userType = ObjectGenerator.getUserType();
            school = ObjectGenerator.getSchool();

            readySubjectInfoTable();
            readyParentTable();

            dataReader.fillNearCityCombo(cmbNearCity);
            dataReader.fillSchoolCombo(cmbSchool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readySubjectInfoTable() {
        tcSubId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        tcTheory.setCellValueFactory(new PropertyValueFactory<>("theory"));
        tcRevision.setCellValueFactory(new PropertyValueFactory<>("revision"));
    }

    private void readyParentTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcHome.setCellValueFactory(new PropertyValueFactory<>("home"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    public void saveCity_Key(KeyEvent event) {
        try {
            if (!cmbNearCity.getValue().isEmpty()) {

                if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {

                    nearCity.setCity(cmbNearCity.getValue());
                    int saveNearCity = dataWriter.saveNearCity();
                    if (saveNearCity > 0) {
                        nearCity.resetAll();

                        dataReader.fillNearCityCombo(cmbNearCity);
                        alerts.getSuccessNotify("City Registration", "City Registration successful");
                    }
                } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {

                    nearCity.setCity(cmbNearCity.getValue());
                    int updateNearCity = dataWriter.updateNearCity();
                    if (updateNearCity > 0) {
                        nearCity.resetAll();

                        dataReader.fillNearCityCombo(cmbNearCity);
                        alerts.getSuccessNotify("City Update", "City update successful");
                    }
                } else if (event.getCode().equals(KeyCode.DELETE) && userType.getType().equals("SUPER ADMIN")) {

                    int deleteNearCity = dataWriter.deleteNearCity();
                    if (deleteNearCity > 0) {
                        nearCity.resetAll();

                        dataReader.fillNearCityCombo(cmbNearCity);
                        alerts.getSuccessNotify("City Delete", "City delete successful");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSchool_Key(KeyEvent event) {
        try {
            if (!cmbSchool.getValue().isEmpty()) {

                if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {

                    school.setName(cmbSchool.getValue());
                    int saveSchool = dataWriter.saveSchool();
                    if (saveSchool > 0) {
                        school.resetAll();

                        dataReader.fillSchoolCombo(cmbSchool);
                        alerts.getSuccessNotify("School Registration", "School Registration successful");
                    }
                } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {

                    school.setName(cmbSchool.getValue());
                    int updateSchool = dataWriter.updateSchool();
                    if (updateSchool > 0) {
                        school.resetAll();

                        dataReader.fillSchoolCombo(cmbSchool);
                        alerts.getSuccessNotify("School Update", "School update successful");
                    }
                } else if (event.getCode().equals(KeyCode.DELETE) && userType.getType().equals("SUPER ADMIN")) {

                    int deleteSchool = dataWriter.deleteSchool();
                    if (deleteSchool > 0) {
                        school.resetAll();

                        dataReader.fillSchoolCombo(cmbSchool);
                        alerts.getSuccessNotify("School Delete", "School delete successful");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchCityDetailsByCity() {
        try {
            if (!cmbNearCity.getValue().isEmpty()) {
                nearCity.setCity(cmbNearCity.getValue());
                dataReader.getNearCityByCity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchSchoolDetailsByName() {
        try {
            if (!cmbSchool.getValue().isEmpty()) {
                school.setName(cmbSchool.getValue());
                dataReader.getSchoolByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_parent() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmParant.fxml"));
            //productsStage.setTitle("Add New Employee");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UNDECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeMe() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public static class SubjectList {
        SimpleIntegerProperty id;
        SimpleStringProperty subject;
        SimpleStringProperty teacher;
        SimpleStringProperty theory;
        SimpleStringProperty revision;

        public SubjectList(int id, String subject, String teacher, String theory, String revision) {
            this.id = new SimpleIntegerProperty(id);
            this.subject = new SimpleStringProperty(subject);
            this.teacher = new SimpleStringProperty(teacher);
            this.theory = new SimpleStringProperty(theory);
            this.revision = new SimpleStringProperty(revision);
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

        public String getSubject() {
            return subject.get();
        }

        public SimpleStringProperty subjectProperty() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject.set(subject);
        }

        public String getTeacher() {
            return teacher.get();
        }

        public SimpleStringProperty teacherProperty() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher.set(teacher);
        }

        public String getTheory() {
            return theory.get();
        }

        public SimpleStringProperty theoryProperty() {
            return theory;
        }

        public void setTheory(String theory) {
            this.theory.set(theory);
        }

        public String getRevision() {
            return revision.get();
        }

        public SimpleStringProperty revisionProperty() {
            return revision;
        }

        public void setRevision(String revision) {
            this.revision.set(revision);
        }
    }

    public static class ParentList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty home;
        SimpleStringProperty mobile;

        public ParentList(int id, String name, String home, String mobile) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.home = new SimpleStringProperty(home);
            this.mobile = new SimpleStringProperty(mobile);
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

        public String getHome() {
            return home.get();
        }

        public SimpleStringProperty homeProperty() {
            return home;
        }

        public void setHome(String home) {
            this.home.set(home);
        }

        public String getMobile() {
            return mobile.get();
        }

        public SimpleStringProperty mobileProperty() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile.set(mobile);
        }
    }

}
