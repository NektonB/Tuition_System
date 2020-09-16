package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
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
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class AU_StudentController implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtIndexNumber;

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
    private JFXComboBox<String> cmbExam;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private TableView<SubjectList> tblSubjectInfo;

    @FXML
    private TableColumn<SubjectList, Integer> tcSubId;

    @FXML
    private TableColumn<SubjectList, String> tcSubject;

    @FXML
    private TableColumn<SubjectList, Integer> tcTeacherId;

    @FXML
    private TableColumn<SubjectList, String> tcTeacher;

    @FXML
    private TableColumn<SubjectList, JFXCheckBox> tcTheory;

    @FXML
    private TableColumn<SubjectList, JFXCheckBox> tcRevision;

    @FXML
    private TableColumn<SubjectList, JFXCheckBox> tcPaper;

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
    Stream stream;
    Subject subject;
    Teacher teacher;
    Guardian guardian;
    Status status;
    Student student;
    AcademicCourse academicCourse;
    Exam exam;
    AC_Class ac_class;
    AC_TypeDetails ac_typeDetails;
    AC_TypeList ac_typeList;

    HashMap<String, String> actionList = new HashMap<>();
    HashMap<String, SubjectList> subList = new HashMap<>();

    HashMap<Integer, String> actionListP = new HashMap<>();
    HashMap<Integer, ParentList> guaList = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            dataWriter = ObjectGenerator.getDataWriter();

            nearCity = ObjectGenerator.getNearCity();
            userType = ObjectGenerator.getUserType();
            school = ObjectGenerator.getSchool();
            stream = ObjectGenerator.getStream();
            subject = ObjectGenerator.getSubject();
            teacher = ObjectGenerator.getTeacher();
            guardian = ObjectGenerator.getGuardian();
            status = ObjectGenerator.getStatus();
            student = ObjectGenerator.getStudent();
            exam = ObjectGenerator.getExam();
            academicCourse = ObjectGenerator.getAcademicCourse();
            ac_class = ObjectGenerator.getAc_class();
            ac_typeDetails = ObjectGenerator.getAc_typeDetails();
            ac_typeList = ObjectGenerator.getAc_typeList();

            readySubjectInfoTable();
            readyParentTable();

            dataReader.fillNearCityCombo(cmbNearCity);
            dataReader.fillSchoolCombo(cmbSchool);
            dataReader.fillStreamCombo(cmbStream);
            dataReader.fillExamCombo(cmbExam);
            dataReader.fillSubjectCombo(cmbSubject);
            dataReader.fillParentCombo(cmbParents);
            dataReader.fillStatusCombo(cmbStatus);

            readyGradeCombo();
            readyYearCombo();
            loadContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readySubjectInfoTable() {
        tcSubId.setCellValueFactory(new PropertyValueFactory<>("subId"));
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tcTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        tcTheory.setCellValueFactory(new PropertyValueFactory<>("cbTheory"));
        tcRevision.setCellValueFactory(new PropertyValueFactory<>("cbRevision"));
        tcPaper.setCellValueFactory(new PropertyValueFactory<>("cbPaper"));
    }

    private void readyParentTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcHome.setCellValueFactory(new PropertyValueFactory<>("home"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    public void readyGradeCombo() {
        try {
            cmbGrade.getItems().addAll("6", "7", "8", "9", "10", "11", "12", "13");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyYearCombo() {
        try {
            cmbExamYear.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadContent() {
        if (student.getId() > 0) {
            txtFName.setText(student.getF_name());
            txtLName.setText(student.getL_name());
            txtMobile.setText(student.getContact_number());
            txtNIC.setText(student.getNic_number());
            txtEmail.setText(student.getEmail());
            txtAddress.setText(student.getAddress());

            cmbNearCity.setValue(nearCity.getCity());
            cmbStatus.setValue(status.getStatus());
            cmbSchool.setValue(school.getName());
            cmbGrade.setValue(student.getGrade());
            cmbExam.setValue(exam.getExam());
            cmbExamYear.setValue(academicCourse.getExam_year());
            cmbStream.setValue(stream.getStream());

            dataReader.fillSubjectInfoTable(tblSubjectInfo);
            dataReader.fillGuardianTable(tblParent);
        }
    }

    public void setWindowData(String title, String actionName, TableView tblStudent) {
        lblTitle.setText(title);
        btnSave.setText(actionName);
        //this.tblTeacher = tblTeacher;
    }

    public void saveCity() {
        try {
            if (!cmbNearCity.getValue().isEmpty()) {
                nearCity.setCity(cmbNearCity.getValue());
                int saveNearCity = dataWriter.saveNearCity();
                if (saveNearCity > 0) {
                    nearCity.resetAll();

                    dataReader.fillNearCityCombo(cmbNearCity);
                    alerts.getSuccessNotify("City Registration", "City Registration successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCity() {
        try {
            if (!cmbNearCity.getValue().isEmpty()) {
                nearCity.setCity(cmbNearCity.getValue());
                int updateNearCity = dataWriter.updateNearCity();
                if (updateNearCity > 0) {
                    nearCity.resetAll();

                    dataReader.fillNearCityCombo(cmbNearCity);
                    alerts.getSuccessNotify("City Update", "City update successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCity() {
        try {
            if (!cmbNearCity.getValue().isEmpty()) {
                Optional<ButtonType> dialog = alerts.getConfirmationDialog("Warning", "City Delete", "Are you sure, you want to delete " + cmbNearCity.getValue() + " ?");
                if (dialog.get().equals(ButtonType.OK)) {
                    int deleteNearCity = dataWriter.deleteNearCity();
                    if (deleteNearCity > 0) {
                        nearCity.resetAll();

                        dataReader.fillNearCityCombo(cmbNearCity);
                        alerts.getSuccessNotify("City Delete", "City delete successful");
                    }
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCity_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveCity();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {
            updateCity();
        } else if (event.getCode().equals(KeyCode.DELETE) && userType.getType().equals("SUPER ADMIN")) {
            deleteCity();
        }
    }

    public void saveSchool() {
        try {
            if (!cmbSchool.getValue().isEmpty()) {
                school.setName(cmbSchool.getValue());
                int saveSchool = dataWriter.saveSchool();
                if (saveSchool > 0) {
                    school.resetAll();

                    dataReader.fillSchoolCombo(cmbSchool);
                    alerts.getSuccessNotify("School Registration", "School Registration successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSchool() {
        try {
            if (!cmbSchool.getValue().isEmpty()) {
                school.setName(cmbSchool.getValue());
                int updateSchool = dataWriter.updateSchool();
                if (updateSchool > 0) {
                    school.resetAll();

                    dataReader.fillSchoolCombo(cmbSchool);
                    alerts.getSuccessNotify("School Update", "School update successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSchool() {
        try {
            if (!cmbSchool.getValue().isEmpty()) {
                Optional<ButtonType> dialog = alerts.getConfirmationDialog("Warning", "School Delete", "Are you sure, you want to delete " + cmbSchool.getValue() + " ?");
                if (dialog.get().equals(ButtonType.OK)) {
                    int deleteSchool = dataWriter.deleteSchool();
                    if (deleteSchool > 0) {
                        school.resetAll();

                        dataReader.fillSchoolCombo(cmbSchool);
                        alerts.getSuccessNotify("School Delete", "School delete successful");
                    }
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSchool_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveSchool();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {
            updateSchool();
        } else if (event.getCode().equals(KeyCode.DELETE) && userType.getType().equals("SUPER ADMIN")) {
            deleteSchool();
        }
    }

    public void saveStream() {
        try {
            if (!cmbStream.getValue().isEmpty()) {
                stream.setStream(cmbStream.getValue());
                int saveStream = dataWriter.saveStream();
                if (saveStream > 0) {
                    stream.resetAll();

                    dataReader.fillStreamCombo(cmbStream);
                    alerts.getSuccessNotify("Stream Registration", "Stream Registration successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStream() {
        try {
            if (!cmbStream.getValue().isEmpty()) {
                stream.setStream(cmbStream.getValue());
                int updateStream = dataWriter.updateStream();
                if (updateStream > 0) {
                    stream.resetAll();

                    dataReader.fillStreamCombo(cmbStream);
                    alerts.getSuccessNotify("Stream Update", "Stream update successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStream() {
        try {
            if (!cmbStream.getValue().isEmpty()) {
                Optional<ButtonType> dialog = alerts.getConfirmationDialog("Warning", "Stream Delete", "Are you sure, you want to delete " + cmbStream.getValue() + " ?");
                if (dialog.get().equals(ButtonType.OK)) {
                    int deleteStream = dataWriter.deleteStream();
                    if (deleteStream > 0) {
                        stream.resetAll();

                        dataReader.fillStreamCombo(cmbStream);
                        alerts.getSuccessNotify("Stream Delete", "Stream delete successful");
                    }
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStream_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveStream();
        } else if (event.isControlDown() && event.getCode().equals(KeyCode.U)) {
            updateStream();
        } else if (event.getCode().equals(KeyCode.DELETE) && userType.getType().equals("SUPER ADMIN")) {
            deleteStream();
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

    public void searchStreamDetailsByName() {
        try {
            if (!cmbStream.getValue().isEmpty()) {
                stream.setStream(cmbStream.getValue());
                dataReader.getStreamByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTeacherCombo() {
        try {
            dataReader.fillTeacherCombo(cmbTeacher, cmbSubject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchSubjectDetailsByName() {
        try {
            if (!cmbSubject.getValue().isEmpty()) {
                subject.setName(cmbSubject.getValue());
                dataReader.getSubjectDetailsByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTeacherDetailsByName() {
        try {
            if (!cmbTeacher.getValue().isEmpty()) {
                String[] name = cmbTeacher.getValue().split(" ");
                teacher.setFname(name[0]);
                teacher.setLname(name[1]);
                dataReader.getTeacherDetailsByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubjectToTable() {
        try {
            if (!cmbSubject.getValue().isEmpty() && !cmbTeacher.getValue().isEmpty()) {
                searchSubjectDetailsByName();
                searchTeacherDetailsByName();

                boolean already = false;
                ObservableList<? extends TableColumn<?, ?>> columns = tblSubjectInfo.getColumns();
                /*
                 * Search duplicates in the tblSubjectInfo */
                if (!tblSubjectInfo.getItems().isEmpty()) {
                    for (int i = 0; i < tblSubjectInfo.getItems().size(); ++i) {
                        if (columns.get(1).getCellObservableValue(i).getValue().equals(cmbSubject.getValue()) && columns.get(3).getCellObservableValue(i).getValue().equals(cmbTeacher.getValue())) {
                            already = true;
                            if (already) {
                                break;
                            }
                        } else {
                            already = false;
                        }
                    }
                }

                if (!already) {
                    ObservableList<AU_StudentController.SubjectList> subjectsList;
                    subjectsList = tblSubjectInfo.getItems();

                    JFXCheckBox cbTheory = new JFXCheckBox();
                    JFXCheckBox cbRevision = new JFXCheckBox();
                    JFXCheckBox cbPaper = new JFXCheckBox();
                    cbTheory.setSelected(true);
                    cbRevision.setSelected(false);
                    cbPaper.setSelected(false);

                    SubjectList subjectList = new AU_StudentController.SubjectList(subject.getId(), subject.getName(), teacher.getId(), (teacher.getFname() + " " + teacher.getLname()), cbTheory, cbRevision, cbPaper);

                    subjectsList.add(subjectList);
                    tblSubjectInfo.setItems(subjectsList);

                    subList.put(subject.getId() + " " + teacher.getId(), subjectList);
                    actionList.put(subject.getId() + " " + teacher.getId(), "Save");

                    subject.resetAll();
                    teacher.resetAll();
                } else {
                    alerts.getWarningNotify("Warning", "This Subject and Teacher already in the table.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubjectToTable_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            addSubjectToTable();
        }
    }

    public void removeSubjectListFromTable() {
        try {
            if (tblSubjectInfo.getItems().isEmpty()) {
                alerts.getWarningNotify("Warning !", "No more rows here...");
            } else {
                AU_StudentController.SubjectList subjectList = tblSubjectInfo.getSelectionModel().getSelectedItem();

                actionList.remove(subjectList.getSubId() + " " + subjectList.getTeacherId());
                actionList.put(subjectList.getSubId() + " " + subjectList.getTeacherId(), "Delete");

                subList.put(subjectList.getSubId() + " " + subjectList.getTeacherId(), subjectList);

                tblSubjectInfo.getItems().remove(subjectList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TblSubjectInfo_KeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            removeSubjectListFromTable();
        }
    }

    public void searchParentDetailsByName() {
        try {
            if (!cmbParents.getValue().isEmpty()) {
                String[] name = cmbParents.getValue().split(" ");
                guardian.setF_name(name[0]);
                guardian.setL_name(name[1]);
                dataReader.getGuardianDetailsByName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addParentToTable() {
        try {
            if (!cmbParents.getValue().isEmpty()) {
                searchParentDetailsByName();

                boolean already = false;
                ObservableList<? extends TableColumn<?, ?>> columns = tblParent.getColumns();
                /*
                 * Search duplicates in the tblSubjectInfo */
                if (!tblParent.getItems().isEmpty()) {
                    for (int i = 0; i < tblParent.getItems().size(); ++i) {
                        if (columns.get(1).getCellObservableValue(i).getValue().equals(cmbParents.getValue())) {
                            already = true;
                            if (already) {
                                break;
                            }
                        } else {
                            already = false;
                        }
                    }
                }

                if (!already) {
                    ObservableList<AU_StudentController.ParentList> parentLists;
                    parentLists = tblParent.getItems();

                    ParentList subjectList = new AU_StudentController.ParentList(guardian.getId(), guardian.getF_name() + " " + guardian.getL_name(), guardian.getHome_number(), guardian.getMobile_number());

                    parentLists.add(subjectList);
                    tblParent.setItems(parentLists);

                    guaList.put(guardian.getId(), subjectList);
                    actionListP.put(guardian.getId(), "Save");

                    guardian.resetAll();
                } else {
                    alerts.getWarningNotify("Warning", "This Parent already in the table.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addParentToTable_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            addParentToTable();
        }
    }

    public void removeParentFromTable() {
        try {
            if (tblParent.getItems().isEmpty()) {
                alerts.getWarningNotify("Warning !", "No more rows here...");
            } else {
                AU_StudentController.ParentList parentList = tblParent.getSelectionModel().getSelectedItem();

                actionListP.remove(parentList.getId());
                actionListP.put(parentList.getId(), "Delete");

                guaList.put(parentList.getId(), parentList);

                tblParent.getItems().remove(parentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TblParent_KeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            removeParentFromTable();
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

    public boolean checkValidate() {
        boolean validate = false;
        if (!txtIndexNumber.getText().isEmpty() && !txtFName.getText().isEmpty() && !txtLName.getText().isEmpty() && !txtNIC.getText().isEmpty() && !cmbStatus.getValue().isEmpty() && !cmbSchool.getValue().isEmpty() && !cmbNearCity.getValue().isEmpty()) {
            validate = true;
        } else {
            txtFName.setStyle("-fx-border-color: crimson");
            txtLName.setStyle("-fx-border-color: crimson");
            txtNIC.setStyle("-fx-border-color: crimson");
            cmbStatus.setStyle("-fx-border-color: crimson");
            cmbSchool.setStyle("-fx-border-color: crimson");
            cmbNearCity.setStyle("-fx-border-color: crimson");
            alerts.getErrorNotify("Error", "Please fill required fields *");
        }
        return validate;
    }

    public void resetBoarders() {
        txtFName.setStyle("-fx-border-color: none");
        txtLName.setStyle("-fx-border-color: none");
        txtNIC.setStyle("-fx-border-color: none");
        cmbStatus.setStyle("-fx-border-color: none");
        cmbSchool.setStyle("-fx-border-color: none");
        cmbNearCity.setStyle("-fx-border-color: none");
    }

    private int saveAcademicCourse() {
        int saveAcademicCourse = 0;
        try {
            exam.setExam(cmbExam.getValue());
            dataReader.getExamByName();

            stream.setStream(cmbStream.getValue());
            dataReader.getStreamByName();

            academicCourse.setExam_year(cmbExamYear.getValue());

            boolean isAlready = dataReader.checkAcademicCourse();

            if (isAlready) {
                saveAcademicCourse = 1;
            } else {
                saveAcademicCourse = dataWriter.saveAcademicCourse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveAcademicCourse;
    }

    public int[] saveAcademicClass() {
        int[] saveAcademicClass = {};
        try {
            status.setStatus(cmbStatus.getValue());
            dataReader.getStatusDetailsByStatus();

            saveAcademicClass = dataWriter.saveACC(tblSubjectInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveAcademicClass;
    }

    public int[] saveAcademicClassTD() {
        int[] saveAcademicClassTD = {};
        try {

            saveAcademicClassTD = dataWriter.saveACCTD(tblSubjectInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveAcademicClassTD;
    }

    public int[] saveAcademicClassTL() {
        int[] saveAcademicClassTD = {};
        try {

            saveAcademicClassTD = dataWriter.saveACCTL(tblSubjectInfo, cmbExam.getValue(), cmbStream.getValue(), cmbExamYear.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveAcademicClassTD;
    }

    public void saveStudent() {
        try {
            if (checkValidate()) {
                searchSchoolDetailsByName();
                searchCityDetailsByCity();

                status.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                ObservableList<? extends TableColumn<?, ?>> columns = tblParent.getColumns();

                /*
                 * Search duplicates in the tblSubjectInfo */
                if (!tblParent.getItems().isEmpty()) {
                    for (int i = 0; i < tblParent.getItems().size(); ++i) {
                        guardian.setId(Integer.parseInt(columns.get(0).getCellObservableValue(0).getValue().toString()));
                    }
                }

                student.setIndexNumber(txtIndexNumber.getText());
                student.setF_name(txtFName.getText());
                student.setL_name(txtLName.getText());
                student.setNic_number(txtNIC.getText());
                student.setAddress(txtAddress.getText());
                student.setContact_number(txtMobile.getText());
                student.setEmail(txtEmail.getText());
                student.setGrade(cmbGrade.getValue());

                int saveStudent = 0;

                if (student.getId() > 0) {
                    saveStudent = dataWriter.updateStudent();
                } else if (student.getId() == 0) {
                    saveStudent = dataWriter.saveStudent();
                }
                if (saveStudent > 0) {
                    alerts.getSuccessNotify("Student Registration", "Student Registration successful");

                    int saveAcademicCourse = saveAcademicCourse();
                    if (saveAcademicCourse > 0) {
                        alerts.getSuccessNotify("Course Registration", "Course Registration successful");

                        int[] saveAcademicClass = saveAcademicClass();
                        if (ac_class.getIds().size() > 0) {

                            int[] saveAcademicClassTD = saveAcademicClassTD();
                            if (ac_typeDetails.getIds().size() > 0) {

                                int[] saveAcademicClassTL = saveAcademicClassTL();
                                if (ac_typeList.getIds().size() > 0) {

                                    alerts.getSuccessNotify("Class Registration", "Class Registration successful");

                                    student.resetAll();
                                    academicCourse.resetAll();
                                    ac_class.resetAll();
                                    ac_typeDetails.resetAll();
                                    ac_typeList.resetAll();

                                    closeMe();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeMe() {
        student.resetAll();
        ac_class.resetAll();
        ac_typeList.resetAll();
        ac_typeDetails.resetAll();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public static class SubjectList {
        SimpleIntegerProperty subId;
        SimpleStringProperty subject;
        SimpleIntegerProperty teacherId;
        SimpleStringProperty teacher;
        JFXCheckBox cbTheory;
        JFXCheckBox cbRevision;
        JFXCheckBox cbPaper;

        public SubjectList(int subId, String subject, int teacherId, String teacher, JFXCheckBox cbTheory, JFXCheckBox cbRevision, JFXCheckBox cbPaper) {
            this.subId = new SimpleIntegerProperty(subId);
            this.subject = new SimpleStringProperty(subject);
            this.teacherId = new SimpleIntegerProperty(teacherId);
            this.teacher = new SimpleStringProperty(teacher);
            this.cbTheory = cbTheory;
            this.cbRevision = cbRevision;
            this.cbPaper = cbPaper;
        }

        public int getSubId() {
            return subId.get();
        }

        public SimpleIntegerProperty subIdProperty() {
            return subId;
        }

        public void setSubId(int subId) {
            this.subId.set(subId);
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

        public int getTeacherId() {
            return teacherId.get();
        }

        public SimpleIntegerProperty teacherIdProperty() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId.set(teacherId);
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

        public JFXCheckBox getCbTheory() {
            return cbTheory;
        }

        public void setCbTheory(JFXCheckBox cbTheory) {
            this.cbTheory = cbTheory;
        }

        public JFXCheckBox getCbRevision() {
            return cbRevision;
        }

        public void setCbRevision(JFXCheckBox cbRevision) {
            this.cbRevision = cbRevision;
        }

        public JFXCheckBox getCbPaper() {
            return cbPaper;
        }

        public void setCbPaper(JFXCheckBox cbPaper) {
            this.cbPaper = cbPaper;
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
