package Controllers;

import DataControllers.DataReader;
import Modules.Guardian;
import Modules.Student;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private TextField txt_search;

    @FXML
    private ComboBox<?> cmb_option;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TableView<?> tblStudent;

    @FXML
    private TableColumn<?, ?> tcId;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcNIC;

    @FXML
    private TableColumn<?, ?> tcAddress;

    @FXML
    private TableColumn<?, ?> tcMobile;

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcStatus;

    DataReader dataReader;
    Alerts alerts;

    Student student;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            alerts = ObjectGenerator.getAlerts();
            dataReader = ObjectGenerator.getDataReader();

            student = ObjectGenerator.getStudent();
            //readyEmployeeTable();
            //dataReader.fillTeacherTable(tblTeacher);
            //fillcmbOption();
        } catch (Exception e) {
            e.printStackTrace();
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

}
