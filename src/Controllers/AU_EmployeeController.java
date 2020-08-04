package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Employee;
import Modules.Status;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AU_EmployeeController implements Initializable {

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtContactNum;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblFrameTitle;


    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;

    Employee employee;
    Status status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();

            employee = ObjectGenerator.getEmployee();
            status = ObjectGenerator.getStatus();

            dataReader.fillStatusCombo(cmbStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWindowData(String title, String actionName) {
        lblFrameTitle.setText(title);
        btnSave.setText(actionName);
    }

    public void searchStatusDetailsByStatus() {
        try {
            if (!cmbStatus.getValue().isEmpty()) {
                status.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void searchStatusDetailsByStatus_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                searchStatusDetailsByStatus();
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public boolean checkValidate() {
        boolean validate = false;
        if (!txtFirstName.getText().isEmpty() && !txtLastName.getText().isEmpty() && !txtNIC.getText().isEmpty() && !cmbStatus.getValue().isEmpty()) {
            validate = true;
        } else {
            txtFirstName.setStyle("-fx-border-color: crimson");
            txtLastName.setStyle("-fx-border-color: crimson");
            txtNIC.setStyle("-fx-border-color: crimson");
            cmbStatus.setStyle("-fx-border-color: crimson");
            alerts.getErrorNotify("Error", "Please fill required fields *");
        }
        return validate;
    }

    public void saveEmployee() {
        try {
            if (checkValidate()) {
                employee.setFname(txtFirstName.getText());
                employee.setLname(txtLastName.getText());
                employee.setNic_number(txtNIC.getText());
                employee.setAddress(txtAddress.getText());
                employee.setContact_number(txtContactNum.getText());

                status.setStatus(cmbStatus.getValue());
                dataReader.getStatusDetailsByStatus();

                int saveEmployee = dataWriter.saveEmployee();
                if (saveEmployee > 0) {
                    employee.resetAll();
                    status.resetAll();

                    //alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                    alerts.getSuccessNotify("Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                    closeMe();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEmployee_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveEmployee();
        }
    }

    public void resetBoarders() {
        txtFirstName.setStyle("-fx-border-color: none");
        txtLastName.setStyle("-fx-border-color: none");
        txtNIC.setStyle("-fx-border-color: none");
        cmbStatus.setStyle("-fx-border-color: none");
    }

    public void closeMe() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
