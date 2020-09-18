package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleDoubleProperty;
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

public class TeacherPaymentController implements Initializable {

    Alerts alerts;
    DataWriter dataWriter;
    DataReader dataReader;
    User user;
    Teacher teacher;
    AC_TypeDetails ac_typeDetails;
    TeacherPayment teacherPayment;
    TP_Details tp_details;
    @FXML
    private JFXComboBox<String> cmbTeacher;
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
    private JFXTextField txtPayAmount;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnPallAll;
    @FXML
    private TableView<PaymentList> tblTeacherPayment;
    @FXML
    private TableColumn<PaymentList, Integer> tcPayId;
    @FXML
    private TableColumn<PaymentList, Integer> tcTeacherId;
    @FXML
    private TableColumn<PaymentList, String> tcTeacher;
    @FXML
    private TableColumn<PaymentList, Double> tcAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            user = ObjectGenerator.getUser();
            alerts = ObjectGenerator.getAlerts();
            ac_typeDetails = ObjectGenerator.getAc_typeDetails();
            teacher = ObjectGenerator.getTeacher();
            teacherPayment = ObjectGenerator.getTeacherPayment();
            tp_details = ObjectGenerator.getTp_details();

            readyPaymentTable();
            fillYearCombo();
            fillMonthCombo();
            dataReader.fillTeacherCombo(cmbTeacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readyPaymentTable() {
        tcPayId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        tcTeacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void fillYearCombo() {
        cmbYear.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
    }

    private void fillMonthCombo() {
        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    public void load_Class() {
        try {
            payAll();
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
            scc.loadComponents(lblExam, lblSubject, lblTeacher, lblClass, cmbTeacher);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pay() {
        try {

            if (!lblTeacher.getText().equals("Teacher") && !cmbYear.getValue().equals("") && !cmbMonth.getValue().equals("") && !txtPayAmount.getText().equals("")) {
                String[] name = cmbTeacher.getValue().split(" ");
                teacher.setFname(name[0]);
                teacher.setLname(name[1]);
                dataReader.getTeacherDetailsByName();

                teacherPayment.setYear(cmbYear.getValue());
                teacherPayment.setMonth(cmbMonth.getValue());

                user.setId(1);

                boolean isAlready = dataReader.checkTeacherPayment();

                if (isAlready) {
                    payDetails();
                } else {
                    int saveTeacherPayment = dataWriter.saveTeacherPayment();
                    if (saveTeacherPayment > 0) {
                        payDetails();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payDetails() {
        try {
            tp_details.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
            tp_details.setTime(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()));
            tp_details.setPayAmount(Double.parseDouble(txtPayAmount.getText()));

            boolean isAlreadyTPD = dataReader.checkTP_Details();
            if (isAlreadyTPD) {

                int updateTP_details = dataWriter.updateTP_Details();
                if (updateTP_details > 0) {
                    if (updateTP_details > 0) {
                        alerts.getSuccessNotify("Teacher Payment", "Update successful");
                        dataReader.fillTeacherPaymentTable(tblTeacherPayment);

                        txtPayAmount.setText("");
                        cmbTeacher.setValue("");
                    }
                }

            } else {

                int saveTP_details = dataWriter.saveTP_Details();
                if (saveTP_details > 0) {
                    alerts.getSuccessNotify("Teacher Payment", "Payed successful");
                    dataReader.fillTeacherPaymentTable(tblTeacherPayment);

                    txtPayAmount.setText("");
                    cmbTeacher.setValue("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pay_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            pay();
        }
    }

    public void selectPayDetail() {
        try {
            if (!tblTeacherPayment.getItems().isEmpty()) {
                TeacherPaymentController.PaymentList paymentList = tblTeacherPayment.getSelectionModel().getSelectedItem();

                String[] name = paymentList.getName().split(" ");
                teacher.setFname(name[0]);
                teacher.setLname(name[1]);
                dataReader.getTeacherDetailsByName();

                cmbTeacher.setValue(paymentList.getName());
                txtPayAmount.setText(String.valueOf(paymentList.getAmount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectPayDetail_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                selectPayDetail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void payAll() {
        teacherPayment.resetAll();
        tp_details.resetAll();
        teacher.resetAll();
        ac_typeDetails.resetAll();
        cmbYear.setValue("");
        cmbMonth.setValue("");
        cmbTeacher.setValue("");
        txtPayAmount.setText("");
        tblTeacherPayment.getItems().clear();
    }

    public static class PaymentList {
        SimpleIntegerProperty payId;
        SimpleIntegerProperty teacherId;
        SimpleStringProperty name;
        SimpleDoubleProperty amount;

        public PaymentList(int payId, int teacherId, String name, double amount) {
            this.payId = new SimpleIntegerProperty(payId);
            this.teacherId = new SimpleIntegerProperty(teacherId);
            this.name = new SimpleStringProperty(name);
            this.amount = new SimpleDoubleProperty(amount);
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

        public int getTeacherId() {
            return teacherId.get();
        }

        public void setTeacherId(int teacherId) {
            this.teacherId.set(teacherId);
        }

        public SimpleIntegerProperty teacherIdProperty() {
            return teacherId;
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

        public double getAmount() {
            return amount.get();
        }

        public void setAmount(double amount) {
            this.amount.set(amount);
        }

        public SimpleDoubleProperty amountProperty() {
            return amount;
        }
    }
}
