package DataControllers;

import Controllers.*;
import DB_Conn.ConnConfig;
import DB_Conn.ConnectDB;
import Modules.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.NotNull;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DataReader {

    PreparedStatement pst;
    Connection conn;

    BackupData backupData;
    ConnectionInfo connectionInfo;
    Alerts alerts;

    Status status;
    Employee employee;

    public DataReader() {
        try {
            Thread readyData = new Thread(() -> {
                conn = ConnectDB.getConn();
                backupData = ObjectGenerator.getBackupData();
                connectionInfo = ObjectGenerator.getConnectionInfo();
                alerts = ObjectGenerator.getAlerts();

                status = ObjectGenerator.getStatus();
                employee = ObjectGenerator.getEmployee();

            });
            readyData.setName("DataReader");
            readyData.start();
        } catch (Exception e) {
            e.printStackTrace();
            alerts.getErrorAlert(e);
        }
    }

    public void getSQLDumpPath() {
        ResultSet rs = null;
        try {
            pst = ConnConfig.getServerConfig().prepareStatement("SELECT * FROM tbl_exepath WHERE id = ? ");
            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                backupData.setMysqlDumpPath(rs.getString("path"));
            }
        } catch (Exception e) {
            //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            alerts.getErrorAlert(e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void getConnectionInformation() {
        ResultSet rs = null;
        try {
            pst = ConnConfig.getServerConfig().prepareStatement("SELECT * FROM tbl_connection WHERE Id = ? ");
            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                connectionInfo.setServerIP(rs.getString("server_ip"));
                connectionInfo.setPort(rs.getString("server_port"));
                connectionInfo.setDatabase(rs.getString("database"));
                connectionInfo.setUsername(rs.getString("user_name"));
                connectionInfo.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                //WebOptionPane.showMessageDialog(null, e, "Error", WebOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                alerts.getErrorAlert(e);
            }
        }
    }

    public void fillStatusCombo(JFXComboBox cmbStatus) {
        ResultSet rs = null;
        cmbStatus.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT status FROM status");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbStatus.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getStatusDetailsByStatus() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM status WHERE status = ?");
            pst.setString(1, status.getStatus());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                status.resetAll();
            }
            while (rs.next()) {
                status.setId(rs.getInt(1));
                status.setStatus(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillEmployeeTable(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number,employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                employeeList.add(
                        new EmployeeController.EmployeeList(
                                rs.getInt("employee.id"),
                                rs.getString("employee.fname") + " " + rs.getString("employee.lname"),
                                rs.getString("employee.nic_number"),
                                rs.getString("employee.address"),
                                rs.getString("employee.contact_number"),
                                rs.getString("status.status")
                        )
                );
                tblEmployee.setItems(employeeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getEmployeeDetailsById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number, employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.id = ?");
            pst.setInt(1, employee.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                employee.resetAll();
                status.resetAll();
            }
            while (rs.next()) {
                //employee.setId(rs.getInt(1));
                employee.setFname(rs.getString(2));
                employee.setLname(rs.getString(3));
                employee.setNic_number(rs.getString(4));
                employee.setAddress(rs.getString(5));
                employee.setContact_number(rs.getString(6));
                status.setStatus(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getEmployeeDetailsByFirstName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number, employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.fname LIKE ?");
            pst.setString(1, employee.getFname() + "%");
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                employee.resetAll();
                status.resetAll();
            }
            while (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setFname(rs.getString(2));
                employee.setLname(rs.getString(3));
                employee.setNic_number(rs.getString(4));
                employee.setAddress(rs.getString(5));
                employee.setContact_number(rs.getString(6));
                status.setStatus(rs.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillEmployeeTableByNIC(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number,employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.fname LIKE ?");
            pst.setString(1, employee.getNic_number() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                //userType.resetAll();
            }
            while (rs.next()) {
                employeeList.add(
                        new EmployeeController.EmployeeList(
                                rs.getInt("employee.id"),
                                rs.getString("employee.fname") + " " + rs.getString("employee.lname"),
                                rs.getString("employee.nic_number"),
                                rs.getString("employee.address"),
                                rs.getString("employee.contact_number"),
                                rs.getString("status.status")
                        )
                );
                tblEmployee.setItems(employeeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
