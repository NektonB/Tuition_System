package Controllers;

import Modules.User;
import Modules.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    AnchorPane pnlGRN;
    AnchorPane pnlInvoice;
    ReportViewer reportViewer;
    Alerts alerts;
    User user;
    UserType userType;

    @FXML
    private StackPane rootPane;

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
            userType = ObjectGenerator.getUserType();
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

    public void loadEmployee(ActionEvent event) throws IOException {
        if (userType.getType().equals("Admin") || userType.getType().equals("Super Admin")) {
            StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlEmployee.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            alerts.getErrorNotify("Login Error", "Access Denied");
        }
    }

    public void loadTeacher(ActionEvent event) throws IOException {
        if (userType.getType().equals("Admin") || userType.getType().equals("Super Admin")) {
            StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlTeacher.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            alerts.getErrorNotify("Login Error", "Access Denied");
        }
    }

    public void loadStudent(ActionEvent event) throws IOException {
        if (userType.getType().equals("Admin") || userType.getType().equals("Super Admin")) {
            StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlStudent.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            alerts.getErrorNotify("Login Error", "Access Denied");
        }
    }

    public void loadAttendant(ActionEvent event) throws IOException {
        StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlAttendans.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    public void loadClassPayment(ActionEvent event) throws IOException {
        /*StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlClassPayment.fxml"));
        rootPane.getChildren().setAll(pane);*/

        Stage classPaymentStage = new Stage();
        Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlClassPayment.fxml"));
        classPaymentStage.setTitle("Class Payments");
        Scene scene = new Scene(user);
        classPaymentStage.setScene(scene);
        classPaymentStage.initStyle(StageStyle.DECORATED);
        //productsStage.getIcons().add(new Image("/images/Main_01.png"));
        classPaymentStage.setResizable(false);
        classPaymentStage.initModality(Modality.WINDOW_MODAL);
        classPaymentStage.show();
    }

    public void loadTeacherPayment(ActionEvent event) throws IOException {
        if (userType.getType().equals("Admin") || userType.getType().equals("Super Admin")) {
            StackPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/pnlTeacherPayment.fxml"));
            rootPane.getChildren().setAll(pane);
        } else {
            alerts.getErrorNotify("Login Error", "Access Denied");
        }
    }


    //Load User form (PopUp)**********************
    public void loadUser() {
        try {
            if (userType.getType().equals("Admin") || userType.getType().equals("Super Admin")) {
                Stage productsStage = new Stage();
                Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmUser.fxml"));
                productsStage.setTitle("User Management");
                Scene scene = new Scene(user);
                productsStage.setScene(scene);
                productsStage.initStyle(StageStyle.UTILITY);
                //productsStage.getIcons().add(new Image("/images/Main_01.png"));
                productsStage.setResizable(false);
                productsStage.initModality(Modality.APPLICATION_MODAL);
                productsStage.show();
            } else {
                alerts.getErrorNotify("Login Error", "Access Denied");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadClassPay() {
        try {
            Stage productsStage = new Stage();
            Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmCPR.fxml"));
            productsStage.setTitle("Student Class Fees Report");
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

    public void loadClassAttendance() {
        try {
            Stage productsStage = new Stage();
            Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmSAR.fxml"));
            productsStage.setTitle("Class Attendance Report");
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

    public void loadTeacherPaymentReport() {
        try {
            Stage productsStage = new Stage();
            Parent user = FXMLLoader.load(getClass().getClassLoader().getResource("Views/frmTPR.fxml"));
            productsStage.setTitle("Teacher Payment Report");
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
