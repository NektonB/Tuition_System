package Controllers;

import DataControllers.DataReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CPR_Controller implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    ReportViewer reportViewer;

    @FXML
    private JFXComboBox<String> cmbStream;

    @FXML
    private JFXComboBox<String> cmbExamYear;

    @FXML
    private JFXComboBox<String> cmbSubject;

    @FXML
    private JFXComboBox<String> cmbClassType;

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

            dataReader.fillStreamCombo(cmbStream);
            dataReader.fillExamYearCombo(cmbExamYear);
            dataReader.fillSubjectCombo(cmbSubject);
            dataReader.fillClassTypeCombo(cmbClassType);
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

}
