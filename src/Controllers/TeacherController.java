package Controllers;

import DataControllers.DataReader;
import Modules.Employee;
import Modules.Teacher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> cmbOption;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TableView<TeachersList> tblTeacher;

    @FXML
    private TableColumn<TeachersList, Integer> tcId;

    @FXML
    private TableColumn<TeachersList, String> tcName;

    @FXML
    private TableColumn<TeachersList, String> tcNIC;

    @FXML
    private TableColumn<TeachersList, String> tcAddress;

    @FXML
    private TableColumn<TeachersList, String> tcContact;

    @FXML
    private TableColumn<TeachersList, String> tcEmail;

    @FXML
    private TableColumn<TeachersList, String> tcSubject;

    @FXML
    private TableColumn<TeachersList, String> tcStatus;

    DataReader dataReader;
    Alerts alerts;

    Teacher teacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();

            teacher = ObjectGenerator.getTeacher();
            readyEmployeeTable();
//            dataReader.fillEmployeeTable(tblEmployee);
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
        tcContact.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    public void loadAU_Teacher() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAU_Teacher.fxml"));
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


    public static class TeachersList {

        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty nic;
        SimpleStringProperty address;
        SimpleStringProperty mobile;
        SimpleStringProperty email;
        SimpleStringProperty subjects;
        SimpleStringProperty status;

        public TeachersList(int id, String name, String nic, String address, String mobile, String email, String subjects, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
            this.address = new SimpleStringProperty(address);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
            this.subjects = new SimpleStringProperty(subjects);
            this.status = new SimpleStringProperty(status);
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

        public String getAddress() {
            return address.get();
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getMobile() {
            return mobile.get();
        }

        public SimpleStringProperty mobileProperty() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile.set(mobile);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public String getSubjects() {
            return subjects.get();
        }

        public SimpleStringProperty subjectsProperty() {
            return subjects;
        }

        public void setSubjects(String subjects) {
            this.subjects.set(subjects);
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
