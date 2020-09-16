package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.Guardian;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ParantController implements Initializable {

    DataReader dataReader;

    @FXML
    private FontAwesomeIconView btnClose;
    DataWriter dataWriter;
    Alerts alerts;
    Guardian guardian;
    JFXComboBox<String> cmbParents;
    @FXML
    private Label lblFrameTitle;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtHomeNum;
    @FXML
    private JFXTextField txtContactNum;
    @FXML
    private JFXButton btnSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataReader = ObjectGenerator.getDataReader();
            dataWriter = ObjectGenerator.getDataWriter();
            alerts = ObjectGenerator.getAlerts();
            guardian = ObjectGenerator.getGuardian();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadContent(JFXComboBox<String> cmbParents) {
        this.cmbParents = cmbParents;
    }

    public void saveParent() {
        try {
            if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("")) {
                guardian.setF_name(txtFirstName.getText());
                guardian.setL_name(txtLastName.getText());
                guardian.setHome_number(txtHomeNum.getText());
                guardian.setMobile_number(txtContactNum.getText());
                int saveParent = dataWriter.saveParent();
                if (saveParent > 0) {
                    dataReader.fillParentCombo(cmbParents);
                    alerts.getSuccessNotify("Parent Registration", "Parent registration successful");
                    cmbParents.setValue(guardian.getF_name() + " " + guardian.getL_name());
                    closeMe();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveParent_Key(KeyEvent event) {
        if (event.isControlDown() && event.getCode().equals(KeyCode.S)) {
            saveParent();
        }
    }

    public void closeMe() {
        cmbParents.requestFocus();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }


}
