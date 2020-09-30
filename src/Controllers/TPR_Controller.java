package Controllers;

import DataControllers.DataReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TPR_Controller implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    ReportViewer reportViewer;

    @FXML
    private JFXComboBox<String> cmbYear;

    @FXML
    private JFXComboBox<String> cmbMonth;

    @FXML
    private JFXTextField txtTeacherName;

    @FXML
    private JFXButton btnView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            reportViewer = ObjectGenerator.getReportViewer();

            fillYearCombo();
            fillMonthCombo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillYearCombo() {
        cmbYear.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030");
    }

    private void fillMonthCombo() {
        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    public void viewReport() {
        try {
            reportViewer.getTeacherPayment(cmbYear.getValue(), cmbMonth.getValue(), txtTeacherName.getText(), "VIEW");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewReport_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            viewReport();
        }
    }
}
