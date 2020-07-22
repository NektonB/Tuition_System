package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private JFXTextField txt_userName;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXButton btn_login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // this code part is Load contact form*****************************
    public void loadContact() throws IOException {
        try {
            Stage productsStage = new Stage();
            Parent frmCustomer = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Home.fxml"));
            productsStage.setTitle("Class Menagment System");
            Scene scene = new Scene(frmCustomer);
            productsStage.setScene(scene);
            productsStage.initStyle(StageStyle.DECORATED);
            productsStage.initModality(Modality.APPLICATION_MODAL);
            productsStage.getIcons().add(new Image("Graphics/Main_01.png"));
            productsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
