package Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ParantController implements Initializable {

    @FXML
    private FontAwesomeIconView btnClose;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void closeMe(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }


}
