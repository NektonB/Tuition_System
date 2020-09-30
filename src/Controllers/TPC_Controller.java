package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TPC_Controller implements Initializable {

    Alerts alerts;

    @FXML
    private JFXTextField txtClassFee;

    @FXML
    private JFXTextField txtCardAmount;

    @FXML
    private JFXTextField txtPayPercentage;

    @FXML
    private JFXButton btnCal;

    private JFXTextField txtPayAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadComponent(JFXTextField txtPayAmount) {
        this.txtPayAmount = txtPayAmount;
    }

    public void calPayAmount() {
        try {
            double classFee = Double.parseDouble(txtClassFee.getText());
            double cardCount = Double.parseDouble(txtCardAmount.getText());
            double PayPercentage = Double.parseDouble(txtPayPercentage.getText());

            double payAmount = classFee * cardCount * (PayPercentage / 100);
            txtPayAmount.setText(String.valueOf(payAmount));
            closeMe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calPayAmount_Key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            calPayAmount();
        }
    }

    public void closeMe() {
        Stage stage = (Stage) btnCal.getScene().getWindow();
        stage.close();
    }
}
