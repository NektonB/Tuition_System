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


    DataReader dataReader;
    Alerts alerts;
    DateFormatConverter dateFormatConverter;

    Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();
            dateFormatConverter = ObjectGenerator.getDateFormatConverter();

            employee = ObjectGenerator.getEmployee();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openNewEmployee() {
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
            au.setWindowData("Add New Employee", "Save Employee");

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
