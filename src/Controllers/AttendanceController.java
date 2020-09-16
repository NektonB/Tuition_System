package Controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    @FXML
    private JFXButton btnSelectClass;

    @FXML
    private Label lblExam;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblTeacher;

    @FXML
    private Label lblClass;

    @FXML
    private JFXDatePicker dpDate;

    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private JFXTextField txtIndexNumber;

    @FXML
    private JFXComboBox<?> cmbStudent;

    @FXML
    private JFXComboBox<?> cmbStatus;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<?> tblAttendance;

    @FXML
    private TableColumn<?, ?> tcSId;

    @FXML
    private TableColumn<?, ?> tcStudent;

    @FXML
    private TableColumn<?, ?> tcStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_Class() {
        try {
            Stage productsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Views/frmSelectClass.fxml"));
            Parent frmCustomer = loader.load();
            //productsStage.setTitle("Add New Employee");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.DECORATED);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);

            SelectClassController scc = loader.getController();
            scc.loadComponents(lblExam, lblSubject, lblTeacher, lblClass);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
