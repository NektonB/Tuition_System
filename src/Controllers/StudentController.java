package Controllers;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loadAU_Student() {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmAU_Student.fxml"));
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
