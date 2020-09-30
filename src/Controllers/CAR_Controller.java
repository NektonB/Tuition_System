package Controllers;

import DataControllers.DataReader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CAR_Controller implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;
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
    private JFXDatePicker dpDate;
    @FXML
    private JFXTextField txtTeacherName;
    @FXML
    private JFXButton bntView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();
            reportViewer = ObjectGenerator.getReportViewer();

            dataReader.fillStreamCombo(cmbStream);
            dataReader.fillExamYearCombo(cmbExamYear);
            dataReader.fillSubjectCombo(cmbSubject);
            dataReader.fillClassTypeCombo(cmbClassType);
            dateFormatConverter.convert(dpDate, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewReport() {
        try {
            reportViewer.getClassAttendance(cmbStream.getValue(), cmbExamYear.getValue(), cmbSubject.getValue(), cmbClassType.getValue(), dpDate.getValue().toString(), txtTeacherName.getText(), "VIEW");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
