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
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> cmbOption;

    @FXML
    private TableView<EmployeeList> tblEmployee;

    @FXML
    private TableColumn<EmployeeList, Integer> tcId;

    @FXML
    private TableColumn<EmployeeList, String> tcName;

    @FXML
    private TableColumn<EmployeeList, String> tcNIC;

    @FXML
    private TableColumn<EmployeeList, String> tcAddress;

    @FXML
    private TableColumn<EmployeeList, String> tcContact;

    @FXML
    private TableColumn<EmployeeList, String> tcStatus;


    DataReader dataReader;
    Alerts alerts;

    Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();

            employee = ObjectGenerator.getEmployee();
            readyEmployeeTable();
            dataReader.fillEmployeeTable(tblEmployee);
            fillcmbOption();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillcmbOption() {
        cmbOption.setItems(FXCollections.observableArrayList("First Name", "NIC Number"));
        cmbOption.setValue("First Name");
    }

    private void readyEmployeeTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadAU_Employee() {
        try {
            Stage productsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/frmAddEmployee.fxml"));
            Parent frmEmployee = loader.load();
            //productsStage.setTitle("Add New Employee");

            Scene scene = new Scene(frmEmployee);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UNDECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);

            AU_EmployeeController au = loader.getController();

            if (employee.getId() == 0) {
                au.setWindowData("Add New Employee", "Save", tblEmployee);
            } else if (employee.getId() > 0) {
                au.setWindowData("Update Employee", "Update", tblEmployee);
            }

            productsStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectEmployee() {
        try {
            if (!tblEmployee.getSelectionModel().isEmpty()) {
                EmployeeList employeeList = tblEmployee.getSelectionModel().getSelectedItem();
                employee.setId(employeeList.getId());
                dataReader.getEmployeeDetailsById();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectEmployee_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectEmployee();
            loadAU_Employee();
        }
    }

    public void filterEmployeeTableByNic(KeyEvent event) {
        try {
            employee.setNic_number(txtSearch.getText());
            dataReader.fillEmployeeTableByNIC(tblEmployee);
            employee.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterEmployeeTableByFirstName(KeyEvent event) {
        try {
            employee.setFname(txtSearch.getText());
            dataReader.fillEmployeeTableByFirstName(tblEmployee);
            employee.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtSearchKeyReleased(KeyEvent event) {
        if (cmbOption.getValue().equals("First Name")) {
            filterEmployeeTableByFirstName(event);
        } else if (cmbOption.getValue().equals("NIC Number")) {
            filterEmployeeTableByNic(event);
        }
    }

    public static class EmployeeList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty nic;
        SimpleStringProperty address;
        SimpleStringProperty contact;
        SimpleStringProperty status;

        public EmployeeList(int id, String name, String nic, String address, String contact, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
            this.address = new SimpleStringProperty(address);
            this.contact = new SimpleStringProperty(contact);
            this.status = new SimpleStringProperty(status);
        }

        public String getAddress() {
            return address.get();
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public void setId(int id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getNic() {
            return nic.get();
        }

        public SimpleStringProperty nicProperty() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic.set(nic);
        }

        public String getContact() {
            return contact.get();
        }

        public SimpleStringProperty contactProperty() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact.set(contact);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }
    }
}
