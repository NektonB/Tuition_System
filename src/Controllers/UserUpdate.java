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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserUpdate implements Initializable {

    DataReader dataReader;
    DataWriter dataWriter;
    Alerts alerts;
    User user;
    UserType userType;
    Employee employee;
    Status status;
    @FXML
    private JFXTextField txt_nic;
    @FXML
    private JFXPasswordField txt_password;
    @FXML
    private Label lblRegister;
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField txt_userName;
    @FXML
    private JFXComboBox<String> cmb_status;
    @FXML
    private JFXComboBox<String> cmb_userType;
    @FXML
    private TableView<UserList> tblUser;
    @FXML
    private TableColumn<UserList, Integer> tcID;
    @FXML
    private TableColumn<UserList, String> tcNIC;
    @FXML
    private TableColumn<UserList, String> tcUserName;
    @FXML
    private TableColumn<UserList, String> tcStatus;

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

            readyUserTable();
            dataReader.fillUserTable(tblUser);

            dataReader.fillUserTypeCombo(cmb_userType);
            dataReader.fillStatusCombo(cmb_status);
            cmb_status.setValue("ACTIVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readyUserTable() {
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tcUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public void selectUser() {
        try {
            if (!tblUser.getItems().isEmpty()) {
                UserList userList = tblUser.getSelectionModel().getSelectedItem();
                user.setId(userList.getId());
                dataReader.getUserDetailsById();
                txt_nic.setText(employee.getNic_number());
                txt_userName.setText(user.getUserName());
                txt_password.setText(user.getPassword());

                cmb_userType.setValue(userType.getType());
                cmb_status.setValue(status.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectUser_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectUser();
            txt_nic.requestFocus();
        }
    }

    public void updateUser() {
        try {
            if (checkValidate()) {

                user.setUserName(txt_userName.getText());

                userType.setType(cmb_userType.getValue());
                dataReader.getUserTypeByType();

                user.setPassword(txt_password.getText());

                status.setStatus(cmb_status.getValue());
                dataReader.getStatusDetailsByStatus();

                int updateUser = dataWriter.updateUser();
                if (updateUser > 0) {
                    employee.resetAll();
                    status.resetAll();
                    user.resetAll();
                    userType.resetAll();
                    resetText();
                    dataReader.fillUserTable(tblUser);
                    alerts.getSuccessNotify("User Update", "Congratulation Chief..!\nUser update successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            updateUser();
        }
    }

    public static class UserList {

        SimpleIntegerProperty id;
        SimpleStringProperty nic;
        SimpleStringProperty userName;
        SimpleStringProperty status;

        public UserList(int id, String nic, String userName, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.nic = new SimpleStringProperty(nic);
            this.userName = new SimpleStringProperty(nic);
            this.status = new SimpleStringProperty(status);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getNic() {
            return nic.get();
        }

        public void setNic(String nic) {
            this.nic.set(nic);
        }

        public SimpleStringProperty nicProperty() {
            return nic;
        }

        public String getUserName() {
            return userName.get();
        }

        public void setUserName(String userName) {
            this.userName.set(userName);
        }

        public SimpleStringProperty userNameProperty() {
            return userName;
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
