package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClassPaymentController implements Initializable {

    DateFormatConverter dateFormatConverter;
    TimeFormatConverter timeFormatConverter;
    Alerts alerts;
    DataWriter dataWriter;
    DataReader dataReader;
    User user;
    Student student;
    AC_Payment ac_payment;
    ACP_Details acp_details;
    AC_TypeDetails ac_typeDetails;
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
    private JFXComboBox<String> cmbYear;
    @FXML
    private JFXComboBox<String> cmbMonth;
    @FXML
    private JFXTextField txtIndexNumber;
    @FXML
    private JFXComboBox<String> cmbStudent;
    @FXML
    private JFXComboBox<String> cmbStatus;
    @FXML
    private JFXButton btnPay;
    @FXML
    private JFXButton btnPayAll;
    @FXML
    private TableView<PaymentList> tblClassPayment;
    @FXML
    private TableColumn<PaymentList, Integer> tcPaymentId;
    @FXML
    private TableColumn<PaymentList, Integer> tcSId;
    @FXML
    private TableColumn<PaymentList, String> tcStudent;
    @FXML
    private TableColumn<PaymentList, String> tcStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();
            timeFormatConverter = ObjectGenerator.getTimeFormatConverter();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            user = ObjectGenerator.getUser();
            student = ObjectGenerator.getStudent();
            alerts = ObjectGenerator.getAlerts();
            ac_payment = ObjectGenerator.getAc_payment();
            acp_details = ObjectGenerator.getAcp_details();
            ac_typeDetails = ObjectGenerator.getAc_typeDetails();

            readyPaymentTable();
            dataReader.fillStudentCombo(cmbStudent);
            fillYearCombo();
            fillMonthCombo();
            filStatusCombo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readyPaymentTable() {
        tcPaymentId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        tcSId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        tcStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void fillYearCombo() {
        cmbYear.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
    }

    private void fillMonthCombo() {
        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    private void filStatusCombo() {
        cmbStatus.getItems().addAll("Payed", "Not Payed");
        cmbStatus.setValue("Payed");
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

    public void payFees() {
        try {
            if (!cmbYear.getValue().equals("") && !cmbMonth.getValue().equals("") && !txtIndexNumber.getText().equals("")) {

                ac_payment.setYear(cmbYear.getValue());
                ac_payment.setMonth(cmbMonth.getValue());
                user.setId(1);

                student.setIndexNumber(txtIndexNumber.getText());
                dataReader.getStudentDetailsByIndexNumber();

                boolean isAlready = dataReader.checkClassPayment();

                acp_details.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
                acp_details.setTime(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()));
                acp_details.setPayStatus(cmbStatus.getValue());

                if (!isAlready) {
                    int saveClassPayment = dataWriter.saveClassPayment();

                    if (saveClassPayment > 0) {

                        int saveCP_details = dataWriter.saveCP_Details();
                        if (saveCP_details > 0) {

                            alerts.getSuccessNotify("Class Fees", "Payed successful");
                            dataReader.fillClassFeesTable(tblClassPayment);

//                            cmbYear.setValue("");
//                            cmbMonth.setValue("");
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Payed");
                        }
                    }
                } else {

                    boolean isAlreadyCPD = dataReader.checkCP_Details();

                    if (isAlreadyCPD) {

                        int updateCP_details = dataWriter.updateCP_Details();
                        if (updateCP_details > 0) {

                            alerts.getSuccessNotify("Class Fees", "Payed successful");
                            dataReader.fillClassFeesTable(tblClassPayment);

//                            cmbYear.setValue("");
//                            cmbMonth.setValue("");
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Payed");
                            txtIndexNumber.requestFocus();
                        }
                    } else {

                        int saveCP_details = dataWriter.saveCP_Details();
                        if (saveCP_details > 0) {

                            alerts.getSuccessNotify("Class Fees", "Payed successful");
                            dataReader.fillClassFeesTable(tblClassPayment);

//                            cmbYear.setValue("");
//                            cmbMonth.setValue("");
                            txtIndexNumber.setText("");
                            cmbStudent.setValue("");
                            cmbStatus.setValue("Payed");
                            txtIndexNumber.requestFocus();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payFees_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            payFees();
        }
    }

    public void payAll() {
        try {
            int[] saveAll_cp_details = dataWriter.saveAll_CP_Details();
            if (saveAll_cp_details.length > 0) {
                ac_payment.resetAll();
                acp_details.resetAll();
                student.resetAll();
                lblExam.setText("Exam");
                lblSubject.setText("Subject");
                lblTeacher.setText("Teacher");
                lblClass.setText("Class");
                txtIndexNumber.setText("");
                cmbStudent.setValue("");
                tblClassPayment.getItems().removeAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payAll_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.F)) {
            payAll();
        }
    }

    public void selectPayDetail() {
        try {
            if (!tblClassPayment.getItems().isEmpty()) {
                PaymentList paymentList = tblClassPayment.getSelectionModel().getSelectedItem();

                String[] name = paymentList.getName().split(" ");
                student.setF_name(name[0]);
                student.setL_name(name[1]);
                dataReader.getStudentDetailsByName();

                cmbStudent.setValue(paymentList.getName());
                cmbStatus.setValue(paymentList.getStatus());
                txtIndexNumber.setText(student.getIndexNumber());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectPayDetail_key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectPayDetail();
        }
    }

    public static class PaymentList {
        SimpleIntegerProperty payId;
        SimpleIntegerProperty sId;
        SimpleStringProperty name;
        SimpleStringProperty status;

        public PaymentList(int payId, int sId, String name, String status) {
            this.payId = new SimpleIntegerProperty(payId);
            this.sId = new SimpleIntegerProperty(sId);
            this.name = new SimpleStringProperty(name);
            this.status = new SimpleStringProperty(status);
        }

        public int getPayId() {
            return payId.get();
        }

        public void setPayId(int payId) {
            this.payId.set(payId);
        }

        public SimpleIntegerProperty payIdProperty() {
            return payId;
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
