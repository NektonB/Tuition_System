package Controllers;

import DataControllers.DataReader;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    DataReader dataReader;
    Alerts alerts;
    @FXML
    private TableView<ClassList> tblClass;
    @FXML
    private TableColumn<ClassList, Integer> tcId;
    @FXML
    private TableColumn<ClassList, String> tcStream;
    @FXML
    private TableColumn<ClassList, String> tcExamYeear;
    @FXML
    private TableColumn<ClassList, String> tcSubject;
    @FXML
    private TableColumn<ClassList, String> tcTeacher;
    @FXML
    private TableColumn<ClassList, String> tcClassType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();

            readyClassTable();
            dataReader.fillClassTable(tblClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readyClassTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcStream.setCellValueFactory(new PropertyValueFactory<>("stream"));
        tcExamYeear.setCellValueFactory(new PropertyValueFactory<>("examYear"));
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        tcClassType.setCellValueFactory(new PropertyValueFactory<>("classType"));
    }

    public static class ClassList {
        SimpleIntegerProperty id;
        SimpleStringProperty stream;
        SimpleStringProperty examYear;
        SimpleStringProperty subject;
        SimpleStringProperty teacher;
        SimpleStringProperty classType;

        public ClassList(int id, String stream, String examYear, String subject, String teacher, String classType) {
            this.id = new SimpleIntegerProperty(id);
            this.stream = new SimpleStringProperty(stream);
            this.examYear = new SimpleStringProperty(examYear);
            this.subject = new SimpleStringProperty(subject);
            this.teacher = new SimpleStringProperty(teacher);
            this.classType = new SimpleStringProperty(classType);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getStream() {
            return stream.get();
        }

        public void setStream(String stream) {
            this.stream.set(stream);
        }

        public SimpleStringProperty streamProperty() {
            return stream;
        }

        public String getExamYear() {
            return examYear.get();
        }

        public void setExamYear(String examYear) {
            this.examYear.set(examYear);
        }

        public SimpleStringProperty examYearProperty() {
            return examYear;
        }

        public String getSubject() {
            return subject.get();
        }

        public void setSubject(String subject) {
            this.subject.set(subject);
        }

        public SimpleStringProperty subjectProperty() {
            return subject;
        }

        public String getTeacher() {
            return teacher.get();
        }

        public void setTeacher(String teacher) {
            this.teacher.set(teacher);
        }

        public SimpleStringProperty teacherProperty() {
            return teacher;
        }

        public String getClassType() {
            return classType.get();
        }

        public void setClassType(String classType) {
            this.classType.set(classType);
        }

        public SimpleStringProperty classTypeProperty() {
            return classType;
        }
    }
}
