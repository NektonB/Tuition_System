package Controllers;

import Modules.User;
import eu.hansolo.enzo.notification.Notification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    AnchorPane pnlGRN;
    AnchorPane pnlInvoice;
    ReportViewer reportViewer;
    Alerts alerts;
    User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Thread readyData = new Thread(() -> {
                ObjectGenerator.getAutoBackup().AutoGet();
            });
            readyData.setName("Main Controller");
            readyData.start();
            reportViewer = ObjectGenerator.getReportViewer();
            alerts = ObjectGenerator.getAlerts();
            user = ObjectGenerator.getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitNow(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Attention !, This is a System Exit conformation.");
        alert.setContentText("Hi User, Do you really want to exit ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {

        }
    }

}
