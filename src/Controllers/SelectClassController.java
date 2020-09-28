package Controllers;

import DataControllers.DataReader;
import Modules.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectClassController implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    AC_TypeList ac_typeList;
    Stream stream;
    AcademicCourse academicCourse;
    Subject subject;
    Teacher teacher;
    ACC_Type acc_type;
    AC_TypeDetails ac_typeDetails;
    Label lblExam;
    Label lblSubject;
    Label lblTeacher;
    Label lblClassType;
    JFXComboBox<String> cmbTeacher01;
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
            ac_typeList = ObjectGenerator.getAc_typeList();
            stream = ObjectGenerator.getStream();
            academicCourse = ObjectGenerator.getAcademicCourse();
            subject = ObjectGenerator.getSubject();
            teacher = ObjectGenerator.getTeacher();
            acc_type = ObjectGenerator.getAcc_type();
            ac_typeDetails = ObjectGenerator.getAc_typeDetails();

            readyClassTable();
            dataReader.fillClassTable(tblClass);
            dataReader.fillStreamCombo(cmbStatus);
            dataReader.fillExamYearCombo(cmbExamYear);
            dataReader.fillSubjectCombo(cmbSubject);
            dataReader.fillClassTypeCombo(cmbClassType);
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

    public void filterClassTableByStream() {
        if (!cmbStatus.getValue().equals("")) {
            try {
                stream.setStream(cmbStatus.getValue());
                dataReader.fillClassTableByStream(tblClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void filterClassTableByExamYear() {
        if (!cmbStatus.getValue().equals("") && !cmbExamYear.getValue().equals("")) {
            try {
                stream.setStream(cmbStatus.getValue());
                academicCourse.setExam_year(cmbExamYear.getValue());
                dataReader.fillClassTableByExamYear(tblClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void filterClassTableBySubject() {
        if (!cmbStatus.getValue().equals("") && !cmbExamYear.getValue().equals("") && !cmbSubject.getValue().equals("")) {
            try {
                stream.setStream(cmbStatus.getValue());
                academicCourse.setExam_year(cmbExamYear.getValue());
                subject.setName(cmbSubject.getValue());
                dataReader.fillTeacherCombo(cmbTeacher, cmbSubject);

                dataReader.fillClassTableBySubject(tblClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void filterClassTableByTeacher() {
        if (!cmbStatus.getValue().equals("") && !cmbExamYear.getValue().equals("") && !cmbSubject.getValue().equals("") && !cmbTeacher.getValue().equals("")) {
            try {
                stream.setStream(cmbStatus.getValue());
                academicCourse.setExam_year(cmbExamYear.getValue());
                subject.setName(cmbSubject.getValue());
                teacher.setFname(cmbTeacher.getValue());

                dataReader.fillClassTableByTeacher(tblClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void filterClassTableByClassType() {
        if (!cmbStatus.getValue().equals("") && !cmbExamYear.getValue().equals("") && !cmbSubject.getValue().equals("") && !cmbTeacher.getValue().equals("") && !cmbClassType.getValue().equals("")) {
            try {
                stream.setStream(cmbStatus.getValue());
                academicCourse.setExam_year(cmbExamYear.getValue());
                subject.setName(cmbSubject.getValue());
                teacher.setFname(cmbTeacher.getValue());
                acc_type.setType(cmbClassType.getValue());

                dataReader.fillClassTableByClassType(tblClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void selectClass() {
        try {
            if (!tblClass.getItems().isEmpty()) {
                SelectClassController.ClassList classList = tblClass.getSelectionModel().getSelectedItem();

                ac_typeDetails.setId(classList.getId());
                stream.setStream(classList.getStream());
                academicCourse.setExam_year(classList.getExamYear());
                subject.setName(classList.getSubject());
                teacher.setFname(classList.getTeacher());
                acc_type.setType(classList.getClassType());

                lblExam.setText(stream.getStream() + " / " + academicCourse.getExam_year());
                lblSubject.setText(subject.getName());
                lblTeacher.setText(teacher.getFname());
                if (cmbTeacher01 != null) {
                    cmbTeacher01.setValue(teacher.getFname());
                }
                lblClassType.setText(acc_type.getType());

                closeMe();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComponents(Label lblExam, Label lblSubject, Label lblTeacher, Label lblClassType) {
        this.lblExam = lblExam;
        this.lblSubject = lblSubject;
        this.lblTeacher = lblTeacher;
        this.lblClassType = lblClassType;
    }

    public void loadComponents(Label lblExam, Label lblSubject, Label lblTeacher, Label lblClassType, JFXComboBox<String> cmbTeacher) {
        this.lblExam = lblExam;
        this.lblSubject = lblSubject;
        this.lblTeacher = lblTeacher;
        this.lblClassType = lblClassType;
        this.cmbTeacher01 = cmbTeacher;
    }

    public void selectTeacher_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectClass();
        }
    }

    public void closeMe() {
        if (ac_typeDetails.getId() > 0) {
            Stage stage = (Stage) tblClass.getScene().getWindow();
            stage.close();
        }
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
