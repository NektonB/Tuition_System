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
import javafx.scene.control.TableView;
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

    Employee employee;
    Status status;

    TableView tblEmployee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataWriter = ObjectGenerator.getDataWriter();
            dataReader = ObjectGenerator.getDataReader();

            employee = ObjectGenerator.getEmployee();
            status = ObjectGenerator.getStatus();

            dataReader.fillStatusCombo(cmbStatus);
            loadContent();
            cmbStatus.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadContent() {
        if (employee.getId() > 0) {
            txtFirstName.setText(employee.getFname());
            txtLastName.setText(employee.getLname());
            txtNIC.setText(employee.getNic_number());
            txtAddress.setText(employee.getAddress());
            txtContactNum.setText(employee.getContact_number());
            cmbStatus.setValue(status.getStatus());
            //employee.resetAll();
            //status.resetAll();
        }
    }

    public void setWindowData(String title, String actionName, TableView tblEmployee) {
        lblFrameTitle.setText(title);
        btnSave.setText(actionName);
        this.tblEmployee = tblEmployee;
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

                if (btnSave.getText().equals("Save")) {
                    int saveEmployee = dataWriter.saveEmployee();
                    if (saveEmployee > 0) {
                        employee.resetAll();
                        status.resetAll();

                        dataReader.fillEmployeeTable(tblEmployee);
                        //alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                        alerts.getSuccessNotify("Employee Registration", "Congratulation Chief..!\nEmployee registration successful");

                        closeMe();
                    }
                } else if (btnSave.getText().equals("Update")) {
                    int updateEmployee = dataWriter.updateEmployee();
                    if (updateEmployee > 0) {
                        employee.resetAll();
                        status.resetAll();

                        dataReader.fillEmployeeTable(tblEmployee);
                        //alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                        alerts.getSuccessNotify("Employee Update", "Congratulation Chief..!\nEmployee update successful");

                        closeMe();
                    }
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
        employee.resetAll();
        status.resetAll();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
