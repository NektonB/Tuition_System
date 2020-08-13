package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Employee;
import Modules.Status;
import Modules.User;
import Modules.UserType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistration implements Initializable {

    @FXML
    private JFXTextField txt_nic;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private Label lbl_update_user;

    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXTextField txt_userName;

    @FXML
    private JFXComboBox<String> cmb_status;

    @FXML
    private JFXComboBox<String> cmb_userType;

    DataReader dataReader;
    DataWriter dataWriter;
    Alerts alerts;

    User user;
    UserType userType;
    Employee employee;
    Status status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            dataWriter = ObjectGenerator.getDataWriter();
            user = ObjectGenerator.getUser();
            userType = ObjectGenerator.getUserType();
            employee = ObjectGenerator.getEmployee();
            status = ObjectGenerator.getStatus();

            dataReader.filluserTypeCombo(cmb_userType);
            dataReader.fillStatusCombo(cmb_status);
            cmb_status.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetText() {
        txt_nic.setText("");
        txt_userName.setText("");
        txt_password.setText("");
        cmb_userType.setValue("");
        cmb_userType.setValue("ACTIVE");
    }

    public boolean checkValidate() {
        boolean validate = false;
        if (!txt_userName.getText().isEmpty() && !txt_nic.getText().isEmpty() && !txt_password.getText().isEmpty() && !cmb_userType.getValue().isEmpty() && !cmb_status.getValue().isEmpty()) {
            System.out.println("Ok");
            employee.setNic_number(txt_nic.getText());
            dataReader.getEmployeeDetailsByNIC();
            if (employee.getId() > 0) {
                validate = true;
            }
        } else {
            txt_userName.setStyle("-fx-border-color: crimson");
            txt_password.setStyle("-fx-border-color: crimson");
            txt_nic.setStyle("-fx-border-color: crimson");
            cmb_status.setStyle("-fx-border-color: crimson");
            cmb_userType.setStyle("-fx-border-color: crimson");
            alerts.getErrorNotify("Error", "Please fill required fields *");
        }
        return validate;
    }

    public void resetBoarders() {
        txt_userName.setStyle("-fx-border-color: none");
        txt_password.setStyle("-fx-border-color: none");
        txt_nic.setStyle("-fx-border-color: none");
        cmb_status.setStyle("-fx-border-color: none");
        cmb_userType.setStyle("-fx-border-color: none");
    }

    public void saveUser() {
        try {
            //System.out.println(checkValidate());
            if (checkValidate()) {

                user.setUserName(txt_userName.getText());

                userType.setType(cmb_userType.getValue());
                dataReader.getUserTypeByType();

                user.setPassword(txt_password.getText());

                status.setStatus(cmb_status.getValue());
                dataReader.getStatusDetailsByStatus();

                int saveUser = dataWriter.saveUser();
                if (saveUser > 0) {
                    employee.resetAll();
                    status.resetAll();
                    user.resetAll();
                    userType.resetAll();
                    resetText();
                    alerts.getSuccessNotify("User Registration", "Congratulation Chief..!\nUser registration successful");
                    closeMe();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveUser();
        }
    }

    //Load User update form (PopUp)**********************
    public void loadUserUpdate() {
        try {
            Stage productsStage = new Stage();
            Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmUser_Update.fxml"));
            productsStage.setTitle("User Update");
            Scene scene = new Scene(user);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UTILITY);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeMe() {
        employee.resetAll();
        status.resetAll();
        user.resetAll();
        userType.resetAll();
        Stage stage = (Stage) btn_login.getScene().getWindow();
        stage.close();
    }
}
