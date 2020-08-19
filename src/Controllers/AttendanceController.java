package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    @FXML
    private JFXDatePicker dpDate;

    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private JFXComboBox<?> cmbStudent;

    @FXML
    private JFXComboBox<?> cmbStatus;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnSave;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
