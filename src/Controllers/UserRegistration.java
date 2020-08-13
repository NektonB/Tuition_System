package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistration implements Initializable {

    @FXML
    private JFXTextField txt_nic;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private Label lbl_update_user;

    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXTextField txt_userName;

    @FXML
    private JFXComboBox<String> cmb_status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Load User update form (PopUp)**********************
    public void loadUserUpdate() {
        try {
            Stage productsStage = new Stage();
            Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmUser_Update.fxml"));
            productsStage.setTitle("User Update");
            Scene scene = new Scene(user);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.UTILITY);
            productsStage.setResizable(false);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
