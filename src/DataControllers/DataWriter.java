package DataControllers;

import Controllers.Alerts;
import Controllers.ObjectGenerator;
import DB_Conn.ConnectDB;
import Modules.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataWriter {
    PreparedStatement pst;
    Connection conn;


    BackupData backupData;
    ConnectionInfo connectionInfo;
    Alerts alerts;

    Employee employee;
    Status status;


    /**
     * Load Supporting classes by thread
     * All supporting classes load in the thread
     */
    public DataWriter() {
        try {
            Thread readyData = new Thread(() -> {
                conn = ConnectDB.getConn();
                backupData = ObjectGenerator.getBackupData();
                connectionInfo = ObjectGenerator.getConnectionInfo();
                alerts = ObjectGenerator.getAlerts();
                employee = ObjectGenerator.getEmployee();
                status = ObjectGenerator.getStatus();
            });
            readyData.setName("Data Writer");
            readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public int saveEmployee() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO employee(fname, lname, nic_number, address, contact_number, status_id) VALUES(?,?,?,?,?,?) ");
            pst.setString(1, employee.getFname());
            pst.setString(2, employee.getLname());
            pst.setString(3, employee.getNic_number());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getContact_number());
            pst.setInt(6, status.getId());

            operation = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operation;
    }

}
