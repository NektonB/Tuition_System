package Controllers;

import DataControllers.DataReader;
import Modules.Teacher;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    Teacher teacher;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();

            teacher = ObjectGenerator.getTeacher();
            readyEmployeeTable();
            dataReader.fillTeacherTable(tblTeacher);
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

    public void selectTeacher() {
        try {
            if (!tblTeacher.getItems().isEmpty()) {
                TeachersList teachersList = tblTeacher.getSelectionModel().getSelectedItem();
                teacher.setId(teachersList.getId());
                dataReader.getTeacherDetailsById();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectEmployee_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectTeacher();
            loadAU_Teacher();
        }
    }

    public void filterTeacherTableByNic(KeyEvent event) {
        try {
            teacher.setNic_number(txtSearch.getText());
            dataReader.filterTeacherTableByNIC(tblTeacher);
            teacher.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterTeacherTableByName(KeyEvent event) {
        try {
            teacher.setFname(txtSearch.getText());
            dataReader.filterTeacherTableByName(tblTeacher);
            teacher.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtSearchKeyReleased(KeyEvent event) {
        if (cmbOption.getValue().equals("First Name")) {
            filterTeacherTableByName(event);
        } else if (cmbOption.getValue().equals("NIC Number")) {
            filterTeacherTableByNic(event);
        }
    }

    public void loadAU_Teacher() {
        try {
            Stage productsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/frmAU_Teacher.fxml"));
            Parent frmCustomer = loader.load();
            //productsStage.setTitle("Add New Employee");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UNDECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);

            AU_TeacherController au = loader.getController();
            if (teacher.getId() == 0) {
                au.setWindowData("Add New Teacher", "Save", tblTeacher);
            } else if (teacher.getId() > 0) {
                au.setWindowData("Update Teacher", "Update", tblTeacher);
            }

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

        public void setId(int id) {
            this.id.set(id);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public SimpleStringProperty nameProperty() {
            return name;
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

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public String getMobile() {
            return mobile.get();
        }

        public void setMobile(String mobile) {
            this.mobile.set(mobile);
        }

        public SimpleStringProperty mobileProperty() {
            return mobile;
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String email) {
            this.email.set(email);
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public String getSubjects() {
            return subjects.get();
        }

        public void setSubjects(String subjects) {
            this.subjects.set(subjects);
        }

        public SimpleStringProperty subjectsProperty() {
            return subjects;
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
