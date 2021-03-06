package Controllers;

import DataControllers.DataReader;
import Modules.AC_Class;
import Modules.Student;
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

public class StudentController implements Initializable {

    DataReader dataReader;
    Alerts alerts;
    Student student;
    AC_Class ac_class;
    @FXML
    private TextField txt_search;
    @FXML
    private ComboBox<String> cmb_option;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private TableView<StudentList> tblStudent;
    @FXML
    private TableColumn<StudentList, Integer> tcId;
    @FXML
    private TableColumn<StudentList, String> tcName;
    @FXML
    private TableColumn<StudentList, String> tcNIC;
    @FXML
    private TableColumn<StudentList, String> tcAddress;
    @FXML
    private TableColumn<StudentList, String> tcMobile;
    @FXML
    private TableColumn<StudentList, String> tcEmail;
    @FXML
    private TableColumn<StudentList, String> tcStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            ac_class = ObjectGenerator.getAc_class();

            student = ObjectGenerator.getStudent();
            readyStudentTable();
            dataReader.fillStudentTable(tblStudent);
            fillcmbOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillcmbOption() {
        cmb_option.setItems(FXCollections.observableArrayList("First Name", "Index Number"));
        cmb_option.setValue("First Name");
    }

    private void readyStudentTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcMobile.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void selectStudent() {
        try {
            if (!tblStudent.getItems().isEmpty()) {
                StudentList studentList = tblStudent.getSelectionModel().getSelectedItem();
                student.setId(studentList.getId());
                dataReader.getStudentDetailsById();
                dataReader.getACTL_DetailsByStudentId();
                dataReader.getACTD_DetailsByStudentId();
                dataReader.getACC_DetailsByClassId();
                dataReader.getAC_DetailsByCourseId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectStudent_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            selectStudent();
            loadAU_Student();
        }
    }

    public void loadAU_Student() {
        try {
            Stage productsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/frmAU_Student.fxml"));
            Parent frmCustomer = loader.load();
            //productsStage.setTitle("Add New Employee");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UNDECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);

            AU_StudentController au = loader.getController();
            if (student.getId() == 0) {
                au.setWindowData("Add New Student", "Save", tblStudent);
            } else if (student.getId() > 0) {
                au.setWindowData("Update Student", "Update", tblStudent);
            }

            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterStudentTableByIndexNumber(KeyEvent event) {
        try {
            student.setIndexNumber(txt_search.getText());
            dataReader.filterStudentTableByIndexNumber(tblStudent);
            student.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterStudentTableByName(KeyEvent event) {
        try {
            student.setF_name(txt_search.getText());
            dataReader.filterStudentTableByName(tblStudent);
            student.resetAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtSearchKeyReleased(KeyEvent event) {
        if (cmb_option.getValue().equals("First Name")) {
            filterStudentTableByName(event);
        } else if (cmb_option.getValue().equals("Index Number")) {
            filterStudentTableByIndexNumber(event);
        }
    }

    public static class StudentList {
        SimpleIntegerProperty id;
        SimpleStringProperty name;
        SimpleStringProperty nic;
        SimpleStringProperty address;
        SimpleStringProperty mobileNumber;
        SimpleStringProperty email;
        SimpleStringProperty status;

        public StudentList(int id, String name, String nic, String address, String mobileNumber, String email, String status) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.nic = new SimpleStringProperty(nic);
            this.address = new SimpleStringProperty(address);
            this.mobileNumber = new SimpleStringProperty(mobileNumber);
            this.email = new SimpleStringProperty(email);
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

        public String getMobileNumber() {
            return mobileNumber.get();
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber.set(mobileNumber);
        }

        public SimpleStringProperty mobileNumberProperty() {
            return mobileNumber;
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
