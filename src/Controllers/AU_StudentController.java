package Controllers;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AU_StudentController implements Initializable {

    @FXML
    private Label lblTitle;

    @FXML
    private FontAwesomeIconView btnClose;

    @FXML
    private JFXTextField txtFName;

    @FXML
    private JFXTextField txtLName;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtHome;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXComboBox<?> cmbNearCity;

    @FXML
    private JFXComboBox<?> cmbSchool;

    @FXML
    private JFXComboBox<?> cmbStream;

    @FXML
    private JFXComboBox<?> cmbGrade;

    @FXML
    private JFXComboBox<?> cmbExamYear;

    @FXML
    private JFXComboBox<?> cmbSubject;

    @FXML
    private JFXComboBox<?> cmbTeacher;

    @FXML
    private TableView<?> tblSubjectInfo;

    @FXML
    private JFXComboBox<?> cmbParents;

    @FXML
    private FontAwesomeIconView btnAddParent;

    @FXML
    private TableView<?> tblParent;

    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void load_parent() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmParant.fxml"));
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

    public void closeMe() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

}
