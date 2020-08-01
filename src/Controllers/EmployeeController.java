package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Employee;
import Modules.Status;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtContactNum;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cmbStatus;


    @FXML
    private JFXTextField txtEmployeeNIC_Search;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TableColumn<?, ?> tc_first_name;

    @FXML
    private TableColumn<?, ?> tc_last_name;

    @FXML
    private TableColumn<?, ?> tc_nic;

    @FXML
    private TableColumn<?, ?> tc_contact;

    @FXML
    private TableColumn<?, ?> tc_status;

    @FXML
    private JFXButton btnUpdate;

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;

    Employee employee;
    Status status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alerts = ObjectGenerator.getAlerts();
        dataWriter = ObjectGenerator.getDataWriter();
        dataReader = ObjectGenerator.getDataReader();
        dateFormatConverter = ObjectGenerator.getDateFormatConverter();

        employee = ObjectGenerator.getEmployee();
        status = ObjectGenerator.getStatus();

       // dataReader.fillStatusCombo(cmbStatus);
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
                if (!cmbStatus.getValue().isEmpty()) {
                    status.setStatus(cmbStatus.getValue());
                    dataReader.getStatusDetailsByStatus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void saveEmployee() {
        try {
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

                alerts.getInformationAlert("Information", "Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
                //alerts.getSuccessNotify("Employee Registration", "Congratulation Chief..!\nEmployee registration successful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openNewEmployee() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAddEmployee.fxml"));
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

    public void openUpdateEmployee() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmUpdateEmployee.fxml"));
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
}
