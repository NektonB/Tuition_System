package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Employee implements Initializable {

    DataWriter dataWriter;
    DataReader dataReader;
    Alerts alerts;
    Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alerts = ObjectGenerator.getAlerts();
        dataWriter = ObjectGenerator.getDataWriter();
        dataReader = ObjectGenerator.getDataReader();
        employee = ObjectGenerator.getEmployee();
    }
}
