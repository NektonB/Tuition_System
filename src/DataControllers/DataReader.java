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
import java.util.Vector;

public class DataReader {

    PreparedStatement pst;
    Connection conn;

    BackupData backupData;
    ConnectionInfo connectionInfo;
    Alerts alerts;

    Status status;
    Employee employee;
    Teacher teacher;
    TeacherHasSubject teacherHasSubject;
    Subject subject;
    User user;
    UserType userType;

    public DataReader() {
        try {
            Thread readyData = new Thread(() -> {
                conn = ConnectDB.getConn();
                backupData = ObjectGenerator.getBackupData();
                connectionInfo = ObjectGenerator.getConnectionInfo();
                alerts = ObjectGenerator.getAlerts();

                status = ObjectGenerator.getStatus();
                employee = ObjectGenerator.getEmployee();
                teacher = ObjectGenerator.getTeacher();
                teacherHasSubject = ObjectGenerator.getTeacherHasSubject();
                subject = ObjectGenerator.getSubject();
                user = ObjectGenerator.getUser();
                userType = ObjectGenerator.getUserType();

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
                employee.resetAll();
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
            }
            tblEmployee.setItems(employeeList);
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

    public void getEmployeeDetailsByNIC() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number, employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.nic_number = ?");
            pst.setString(1, employee.getNic_number());
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
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number,employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.nic_number LIKE ?");
            pst.setString(1, employee.getNic_number() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                employee.resetAll();
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
            }
            tblEmployee.setItems(employeeList);
//            System.out.println("Called");
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

    public void fillEmployeeTableByFirstName(TableView tblEmployee) {
        ResultSet rs = null;
        ObservableList<EmployeeController.EmployeeList> employeeList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT employee.id, employee.fname, employee.lname, employee.nic_number,employee.address, employee.contact_number, status.status FROM employee INNER JOIN status on employee.status_id = status.id WHERE employee.fname LIKE ?");
            pst.setString(1, employee.getFname() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                employee.resetAll();
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
            }
            tblEmployee.setItems(employeeList);
//            System.out.println("Called");
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

    public void getSubjectDetailsByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT id, name FROM subject WHERE name = ?");
            pst.setString(1, subject.getName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                subject.resetAll();
            }
            while (rs.next()) {
                subject.setId(rs.getInt(1));
                subject.setName(rs.getString(2));
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

    public boolean checkSubjectIsAvailable(String name) {
        ResultSet rs = null;
        boolean isAvailable = false;
        try {
            pst = conn.prepareStatement("SELECT id, name FROM subject WHERE name = ?");
            pst.setString(1, name);
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                subject.resetAll();
            }
            while (rs.next()) {
                subject.setId(rs.getInt(1));
                subject.setName(rs.getString(2));
            }

            if (name.contentEquals(subject.getName())) {
                isAvailable = true;
            } else {
                isAvailable = false;
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
        return isAvailable;
    }

    public void fillSubjectCombo(JFXComboBox cmbSubject) {
        ResultSet rs = null;
        cmbSubject.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT name FROM subject");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbSubject.getItems().add(rs.getString(1));
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

    public void fillTeacherTable(TableView tblTeacher) {
        ResultSet rs = null;
        ObservableList<TeacherController.TeachersList> subjectList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT teacher.id, teacher.fname, teacher.lname, teacher.nic_number, teacher.address, teacher.mobile_number, teacher.email, GROUP_CONCAT(subject.name) subjectlist, status.status FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject on ths.subject_id = subject.id INNER JOIN status on teacher.status_id = status.id GROUP BY teacher.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                teacher.resetAll();
            }
            while (rs.next()) {
                subjectList.add(
                        new TeacherController.TeachersList(
                                rs.getInt("teacher.id"),
                                rs.getString("teacher.fname") + " " + rs.getString("teacher.lname"),
                                rs.getString("teacher.nic_number"),
                                rs.getString("teacher.address"),
                                rs.getString("teacher.mobile_number"),
                                rs.getString("teacher.email"),
                                rs.getString("subjectlist"),
                                rs.getString("status.status")
                        )
                );
            }
            tblTeacher.setItems(subjectList);
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

    public void filterTeacherTableByNIC(TableView tblTeacher) {
        ResultSet rs = null;
        ObservableList<TeacherController.TeachersList> subjectList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT teacher.id, teacher.fname, teacher.lname, teacher.nic_number, teacher.address, teacher.mobile_number, teacher.email, GROUP_CONCAT(subject.name) subjectlist, status.status FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject on ths.subject_id = subject.id INNER JOIN status on teacher.status_id = status.id WHERE teacher.nic_number LIKE ? GROUP BY teacher.id");
            pst.setString(1, teacher.getNic_number() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                teacher.resetAll();
            }
            while (rs.next()) {
                subjectList.add(
                        new TeacherController.TeachersList(
                                rs.getInt("teacher.id"),
                                rs.getString("teacher.fname") + " " + rs.getString("teacher.lname"),
                                rs.getString("teacher.nic_number"),
                                rs.getString("teacher.address"),
                                rs.getString("teacher.mobile_number"),
                                rs.getString("teacher.email"),
                                rs.getString("subjectlist"),
                                rs.getString("status.status")
                        )
                );
            }
            tblTeacher.setItems(subjectList);
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

    public void filterTeacherTableByName(TableView tblTeacher) {
        ResultSet rs = null;
        ObservableList<TeacherController.TeachersList> subjectList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT teacher.id, teacher.fname, teacher.lname, teacher.nic_number, teacher.address, teacher.mobile_number, teacher.email, GROUP_CONCAT(subject.name) subjectlist, status.status FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject on ths.subject_id = subject.id INNER JOIN status on teacher.status_id = status.id WHERE teacher.fname LIKE ? GROUP BY teacher.id");
            pst.setString(1, teacher.getFname() + "%");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                teacher.resetAll();
            }
            while (rs.next()) {
                subjectList.add(
                        new TeacherController.TeachersList(
                                rs.getInt("teacher.id"),
                                rs.getString("teacher.fname") + " " + rs.getString("teacher.lname"),
                                rs.getString("teacher.nic_number"),
                                rs.getString("teacher.address"),
                                rs.getString("teacher.mobile_number"),
                                rs.getString("teacher.email"),
                                rs.getString("subjectlist"),
                                rs.getString("status.status")
                        )
                );
            }
            tblTeacher.setItems(subjectList);
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

    public void getTeacherDetailsById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT teacher.id, teacher.fname, teacher.lname, teacher.nic_number, teacher.home_number, teacher.mobile_number, teacher.email, teacher.address, status.status, GROUP_CONCAT(subject.id), GROUP_CONCAT(subject.name) FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject on ths.subject_id = subject.id INNER JOIN status on teacher.status_id = status.id WHERE teacher.id = ? GROUP BY teacher.id");
            pst.setInt(1, teacher.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                teacher.resetAll();
                status.resetAll();
                teacherHasSubject.resetAll();
            }
            while (rs.next()) {
                teacher.setId(rs.getInt(1));
                teacher.setFname(rs.getString(2));
                teacher.setLname(rs.getString(3));
                teacher.setNic_number(rs.getString(4));
                teacher.setHome_number(rs.getString(5));
                teacher.setMobile_number(rs.getString(6));
                teacher.setEmail(rs.getString(7));
                teacher.setAddress(rs.getString(8));
                status.setStatus(rs.getString(9));

                String[] subjectIds = rs.getString("GROUP_CONCAT(subject.id)").split(",");
                String[] subjectNames = rs.getString("GROUP_CONCAT(subject.name)").split(",");
                HashMap<Integer, String> subjectList = new HashMap<>();

                for (int i = 0; i < subjectIds.length; ++i) {
                    subjectList.put(Integer.parseInt(subjectIds[i]), subjectNames[i]);
                }
                teacherHasSubject.setSubjectList(subjectList);
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

    public void filluserTypeCombo(JFXComboBox cmbUserType) {
        ResultSet rs = null;
        cmbUserType.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT type FROM user_type");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbUserType.getItems().add(rs.getString(1));
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

    public void getUserTypeByType() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM user_type WHERE type = ?");
            pst.setString(1, userType.getType());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                userType.resetAll();
            }
            while (rs.next()) {
                userType.setId(rs.getInt(1));
                userType.setType(rs.getString(2));
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
