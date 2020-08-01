package Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateEmployeeController implements Initializable {

    @FXML
    private FontAwesomeIconView btnCloss;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void clossScen(){
        Stage stage = (Stage) btnCloss.getScene().getWindow();
        stage.close();
    }
}
