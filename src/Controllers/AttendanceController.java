package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.ACA_Details;
import Modules.AC_Attendance;
import Modules.Student;
import Modules.User;
import com.jfoenix.controls.*;
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

public class AttendanceController implements Initializable {

    @FXML
    private JFXButton btnSelectClass;

    @FXML
    private Label lblExam;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblTeacher;

    @FXML
    private Label lblClass;

    @FXML
    private JFXDatePicker dpDate;

    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private JFXTextField txtIndexNumber;

    DateFormatConverter dateFormatConverter;
    TimeFormatConverter timeFormatConverter;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnSave;
    Alerts alerts;
    DataWriter dataWriter;
    DataReader dataReader;
    AC_Attendance ac_attendance;
    ACA_Details aca_details;
    User user;
    Student student;
    @FXML
    private JFXComboBox<String> cmbStudent;
    @FXML
    private JFXComboBox<String> cmbStatus;
    @FXML
    private TableView<AttendList> tblAttendance;
    @FXML
    private TableColumn<AttendList, Integer> tcSId;
    @FXML
    private TableColumn<AttendList, String> tcStudent;
    @FXML
    private TableColumn<AttendList, String> tcStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();
            timeFormatConverter = ObjectGenerator.getTimeFormatConverter();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            ac_attendance = ObjectGenerator.getAc_attendance();
            aca_details = ObjectGenerator.getAca_details();
            user = ObjectGenerator.getUser();
            student = ObjectGenerator.getStudent();
            alerts = ObjectGenerator.getAlerts();

            dateFormatConverter.convert(dpDate, "yyyy-MM-dd");
            timeFormatConverter.convert(tpTime, "hh:mm");
            fillStatusCombo();
            readyAttendanceTable();
            dataReader.fillStudentCombo(cmbStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readyAttendanceTable() {
        tcSId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        tcStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void fillStatusCombo() {
        cmbStatus.getItems().addAll("Attended", "Not Attended");
        cmbStatus.setValue("Attended");
    }

    public void load_Class() {
        try {
            Stage productsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/frmSelectClass.fxml"));
            Parent frmCustomer = loader.load();
            //productsStage.setTitle("Add New Employee");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.DECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);

            SelectClassController scc = loader.getController();
            scc.loadComponents(lblExam, lblSubject, lblTeacher, lblClass);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAttendanceDetail() {
        try {
            if (!tblAttendance.getItems().isEmpty()) {
                AttendList classList = tblAttendance.getSelectionModel().getSelectedItem();

                String[] name = classList.getName().split(" ");
                student.setF_name(name[0]);
                student.setL_name(name[1]);
                dataReader.getStudentDetailsByName();

                cmbStudent.setValue(classList.getName());
                cmbStatus.setValue(classList.getStatus());
                txtIndexNumber.setText(student.getIndexNumber());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAttendanceDetail_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectAttendanceDetail();
        }
    }

    public void markAttendance() {
        try {
            if (!txtIndexNumber.getText().equals("")) {
                ac_attendance.setDate(dpDate.getValue().toString());
                ac_attendance.setTime(tpTime.getValue().toString());
                user.setId(1);

                student.setIndexNumber(txtIndexNumber.getText());
                dataReader.getStudentDetailsByIndexNumber();

                boolean isAlreadyA = dataReader.checkAttendance();

                if (ac_attendance.getId() == 0) {
                    int saveAttendance = dataWriter.saveAttendance();

                    if (saveAttendance > 0) {
                        aca_details.setStatus(cmbStatus.getValue());
                        int saveAttendanceDetails = dataWriter.saveAttendanceDetails();

                        if (saveAttendanceDetails > 0) {
                            alerts.getSuccessNotify("Attendance Mark", "Mark successful");
                            dataReader.fillAttendanceTable(tblAttendance);
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Attended");
                        }
                    }
                } else {

                    boolean isAlready = dataReader.checkAttendanceDetails();

                    if (isAlready) {
                        aca_details.setStatus(cmbStatus.getValue());
                        int updateAttendanceDetails = dataWriter.updateAttendanceDetails();

                        if (updateAttendanceDetails > 0) {
                            alerts.getSuccessNotify("Attendance Mark", "Mark successful");
                            dataReader.fillAttendanceTable(tblAttendance);
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Attended");
                        }
                    } else {
                        aca_details.setStatus(cmbStatus.getValue());
                        int saveAttendanceDetails = dataWriter.saveAttendanceDetails();

                        if (saveAttendanceDetails > 0) {
                            alerts.getSuccessNotify("Attendance Mark", "Mark successful");
                            dataReader.fillAttendanceTable(tblAttendance);
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Attended");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAttendance_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            markAttendance();
        }
    }

    public void finishMark() {
        try {
            int[] allAttendanceDetails = dataWriter.saveAllAttendanceDetails();
            if (allAttendanceDetails.length > 0) {
                ac_attendance.resetAll();
                aca_details.resetAll();
                student.resetAll();
                lblExam.setText("Exam");
                lblSubject.setText("Subject");
                lblTeacher.setText("Teacher");
                lblClass.setText("Class");
                txtIndexNumber.setText("");
                cmbStudent.setValue("");
                tblAttendance.getItems().removeAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishMark_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.F)) {
            finishMark();
        }
    }

    public static class AttendList {
        SimpleIntegerProperty sId;
        SimpleStringProperty name;
        SimpleStringProperty status;

        public AttendList(int sId, String name, String status) {
            this.sId = new SimpleIntegerProperty(sId);
            this.name = new SimpleStringProperty(name);
            this.status = new SimpleStringProperty(status);
        }

        public int getsId() {
            return sId.get();
        }

        public void setsId(int sId) {
            this.sId.set(sId);
        }

        public SimpleIntegerProperty sIdProperty() {
            return sId;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }
    }
}
