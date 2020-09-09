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
import java.util.*;

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
    NearCity nearCity;
    School school;
    Stream stream;
    Guardian guardian;
    AcademicCourse academicCourse;
    Exam exam;
    ACC_Type acc_type;
    AC_TypeDetails ac_typeDetails;
    Student student;
    AC_Class ac_class;
    AC_TypeList ac_typeList;

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
                nearCity = ObjectGenerator.getNearCity();
                school = ObjectGenerator.getSchool();
                stream = ObjectGenerator.getStream();
                guardian = ObjectGenerator.getGuardian();
                exam = ObjectGenerator.getExam();
                academicCourse = ObjectGenerator.getAcademicCourse();
                acc_type = ObjectGenerator.getAcc_type();
                ac_typeDetails = ObjectGenerator.getAc_typeDetails();
                student = ObjectGenerator.getStudent();
                ac_class = ObjectGenerator.getAc_class();
                ac_typeList = ObjectGenerator.getAc_typeList();

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

    public void getTeacherDetailsByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT teacher.id, teacher.fname, teacher.lname, teacher.nic_number, teacher.home_number, teacher.mobile_number, teacher.email, teacher.address, status.status, GROUP_CONCAT(subject.id), GROUP_CONCAT(subject.name) FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject on ths.subject_id = subject.id INNER JOIN status on teacher.status_id = status.id WHERE teacher.fname = ? AND teacher.lname = ? GROUP BY teacher.id");
            pst.setString(1, teacher.getFname());
            pst.setString(2, teacher.getLname());
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

    public void fillUserTypeCombo(JFXComboBox cmbUserType) {
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

    public void fillUserTable(TableView tblUser) {
        ResultSet rs = null;
        ObservableList<UserUpdate.UserList> userLists = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT user.id, employee.nic_number, user.name, status.status FROM user INNER JOIN user_type ut on user.user_type_id = ut.id INNER JOIN employee on user.employee_id = employee.id INNER JOIN status on user.status_id = status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                teacher.resetAll();
            }
            while (rs.next()) {
                userLists.add(
                        new UserUpdate.UserList(
                                rs.getInt("user.id"),
                                rs.getString("employee.nic_number"),
                                rs.getString("user.name"),
                                rs.getString("status.status")
                        )
                );
            }
            tblUser.setItems(userLists);
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

    public void getUserDetailsById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT user.id, user.name, user.password, ut.id, ut.type, employee.id, employee.nic_number, status.id, status.status FROM user INNER JOIN user_type ut on user.user_type_id = ut.id INNER JOIN employee on user.employee_id = employee.id INNER JOIN status on user.status_id = status.id WHERE user.id = ?");
            pst.setInt(1, user.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                user.resetAll();
                userType.resetAll();
                status.resetAll();
                employee.resetAll();
            }
            while (rs.next()) {
                user.setId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));

                userType.setId(rs.getInt(4));
                userType.setType(rs.getString(5));

                employee.setId(rs.getInt(6));
                employee.setNic_number(rs.getString(7));

                status.setId(rs.getInt(8));
                status.setStatus(rs.getString(9));
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

    public void getNearCityByCity() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM near_city WHERE city = ?");
            pst.setString(1, nearCity.getCity());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                nearCity.resetAll();
            }
            while (rs.next()) {
                nearCity.setId(rs.getInt(1));
                nearCity.setCity(rs.getString(2));
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

    public void fillNearCityCombo(JFXComboBox cmbNearCity) {
        ResultSet rs = null;
        cmbNearCity.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT city FROM near_city");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbNearCity.getItems().add(rs.getString(1));
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

    public void fillSchoolCombo(JFXComboBox cmbSchool) {
        ResultSet rs = null;
        cmbSchool.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT name FROM school");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbSchool.getItems().add(rs.getString(1));
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

    public void getSchoolByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM school WHERE name = ?");
            pst.setString(1, school.getName());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                school.resetAll();
            }
            while (rs.next()) {
                school.setId(rs.getInt(1));
                school.setName(rs.getString(2));
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

    public void fillStreamCombo(JFXComboBox cmbStream) {
        ResultSet rs = null;
        cmbStream.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT stream FROM stream");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbStream.getItems().add(rs.getString(1));
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

    public void getStreamByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM stream WHERE stream = ?");
            pst.setString(1, stream.getStream());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                stream.resetAll();
            }
            while (rs.next()) {
                stream.setId(rs.getInt(1));
                stream.setStream(rs.getString(2));
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

    public void fillExamCombo(JFXComboBox cmbExam) {
        ResultSet rs = null;
        cmbExam.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT exam FROM exam");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbExam.getItems().add(rs.getString(1));
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

    public void fillTeacherCombo(JFXComboBox cmbTeacher, JFXComboBox<String> cmbSubject) {
        ResultSet rs = null;
        cmbTeacher.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT teacher.fname,teacher.lname FROM teacher INNER JOIN teacher_has_subject ths on teacher.id = ths.teacher_id INNER JOIN subject s on ths.subject_id = s.id WHERE s.name = ?");
            pst.setString(1, cmbSubject.getValue());
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbTeacher.getItems().add(rs.getString(1) + " " + rs.getString(2));
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

    public void getGuardianDetailsByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM gardien WHERE fname = ? AND lname = ?");
            pst.setString(1, guardian.getF_name());
            pst.setString(2, guardian.getL_name());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                guardian.resetAll();
            }
            while (rs.next()) {
                guardian.setId(rs.getInt(1));
                guardian.setF_name(rs.getString(2));
                guardian.setL_name(rs.getString(3));
                guardian.setHome_number(rs.getString(4));
                guardian.setMobile_number(rs.getString(5));
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

    public void fillParentCombo(JFXComboBox cmbParent) {
        ResultSet rs = null;
        cmbParent.getItems().clear();
        try {
            pst = conn.prepareStatement("SELECT fname,lname FROM gardien ");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {

            }
            while (rs.next()) {
                cmbParent.getItems().add(rs.getString(1) + " " + rs.getString(2));
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

    public void getExamByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM exam WHERE exam = ?");
            pst.setString(1, exam.getExam());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                school.resetAll();
            }
            while (rs.next()) {
                exam.setId(rs.getInt(1));
                exam.setExam(rs.getString(2));
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

    public void getTHS_IdByName() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM teacher_has_subject WHERE teacher_id = ? AND subject_id = ?");
            pst.setInt(1, teacher.getId());
            pst.setInt(2, subject.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                teacherHasSubject.resetAll();
            }
            while (rs.next()) {
                teacherHasSubject.setId(rs.getInt(1));
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

    public boolean checkAcademicCourse() {
        boolean isAlready = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT academic_course.id FROM academic_course INNER JOIN exam e on academic_course.exam_id = e.id INNER JOIN stream st on academic_course.stream_id = st.id WHERE e.exam = ? AND st.stream = ? AND academic_course.exam_year = ?");
            pst.setString(1, exam.getExam());
            pst.setString(2, stream.getStream());
            pst.setString(3, academicCourse.getExam_year());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                //academicCourse.resetAll();
            }
            if (rs.next()) {
                academicCourse.setId(rs.getInt(1));
                isAlready = true;
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
        return isAlready;
    }

    public boolean checkACC() {
        boolean isAlready = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ac_class INNER JOIN teacher_has_subject ths on ac_class.teacher_has_subject_id = ths.id WHERE ac_class.ac_id = ? AND ths.subject_id = ? AND ths.teacher_id = ?");
            pst.setInt(1, academicCourse.getId());
            pst.setInt(2, subject.getId());
            pst.setInt(3, teacher.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                ac_class.resetAll();
            }
            if (rs.next()) {
                ac_class.setId(rs.getInt(1));
                isAlready = true;
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
        return isAlready;
    }

    public boolean checkACCTD(int accId, int acctId) {
        boolean isAlready = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ac_type_details WHERE ac_class_id = ? AND tbl_acc_type_id = ?");
            pst.setInt(1, accId);
            pst.setInt(2, acctId);

            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                ac_typeDetails.resetAll();
            }
            if (rs.next()) {
                ac_typeDetails.setId(rs.getInt(1));
                isAlready = true;
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
        return isAlready;
    }

    public boolean checkACCTL(int acctdId, int studentId) {
        boolean isAlready = false;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ac_type_list WHERE ac_type_details_id = ? AND tbl_student_id = ?");
            pst.setInt(1, acctdId);
            pst.setInt(2, studentId);

            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                ac_typeList.resetAll();
            }
            if (rs.next()) {
                ac_typeList.setId(rs.getInt(1));
                isAlready = true;
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
        return isAlready;
    }

    public void fillStudentTable(TableView tblStudent) {
        ResultSet rs = null;
        ObservableList<StudentController.StudentList> studentList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT student.id,student.fname,student.lname,student.nic_number,student.address,student.contact_number,student.email,status.status FROM student INNER JOIN status on student.status_id = status.id");
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                student.resetAll();
            }
            while (rs.next()) {
                studentList.add(
                        new StudentController.StudentList(
                                rs.getInt("student.id"),
                                rs.getString("student.fname") + " " + rs.getString("student.lname"),
                                rs.getString("student.nic_number"),
                                rs.getString("student.address"),
                                rs.getString("student.contact_number"),
                                rs.getString("student.email"),
                                rs.getString("status.status")
                        )
                );
            }
            tblStudent.setItems(studentList);
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

    public void getStudentDetailsById() {
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM student INNER JOIN status on student.status_id = status.id INNER JOIN school on student.school_id = school.id INNER JOIN near_city on student.near_city_id = near_city.id INNER JOIN gardien on student.gardien_id = gardien.id WHERE student.id = ?");
            pst.setInt(1, student.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                student.resetAll();
                status.resetAll();
                nearCity.resetAll();
                school.resetAll();
            }
            while (rs.next()) {
                student.setId(rs.getInt("student.id"));
                student.setF_name(rs.getString("student.fname"));
                student.setL_name(rs.getString("student.lname"));
                student.setNic_number(rs.getString("student.nic_number"));
                student.setAddress(rs.getString("student.address"));
                student.setContact_number(rs.getString("student.contact_number"));
                student.setEmail(rs.getString("student.email"));
                student.setGrade(rs.getString("student.grade"));

                status.setId(rs.getInt("status.id"));
                status.setStatus(rs.getString("status.status"));

                school.setId(rs.getInt("school.id"));
                school.setName(rs.getString("school.name"));

                nearCity.setId(rs.getInt("near_city.id"));
                nearCity.setCity(rs.getString("near_city.city"));

                guardian.setId(rs.getInt("gardien.id"));
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

    public void fillGuardianTable(TableView tblParent) {
        ResultSet rs = null;
        ObservableList<AU_StudentController.ParentList> parentList = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement("SELECT id, fname, lname, home_number, mobile_number FROM gardien WHERE id = ?");
            pst.setInt(1, guardian.getId());

            rs = pst.executeQuery();
            if (!rs.isBeforeFirst()) {
                student.resetAll();
            }
            while (rs.next()) {
                parentList.add(
                        new AU_StudentController.ParentList(
                                rs.getInt("gardien.id"),
                                rs.getString("gardien.fname") + " " + rs.getString("gardien.lname"),
                                rs.getString("gardien.home_number"),
                                rs.getString("gardien.mobile_number")
                        )
                );
            }
            tblParent.setItems(parentList);
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

    public void getACTL_DetailsByStudentId() {
        ResultSet rs = null;
        int[][] actl = null;

        try {
            pst = conn.prepareStatement("SELECT atl.id,atl.ac_type_details_id,atl.status,atd.ac_class_id FROM ac_type_list atl INNER JOIN ac_type_details atd on atl.ac_type_details_id = atd.id WHERE atl.tbl_student_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, student.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                //school.resetAll();
            }

            if (rs.last()) {
                actl = new int[rs.getRow()][4];
                rs.beforeFirst();
            }

            int r = 0;
            while (rs.next()) {
                actl[r][0] = rs.getInt(1);
                actl[r][1] = rs.getInt(2);
                actl[r][2] = rs.getInt(3);
                actl[r][3] = rs.getInt(4);
                ++r;
            }

            /*for (int[] j : actl) {
                for (int k : j) {
                    System.out.print(k + " ");
                }
                System.out.println("");
            }*/

            ac_typeList.setActl(actl);
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

    public void getACTD_DetailsByStudentId() {
        ResultSet rs = null;
        int[][] actd = null;

        try {

            int preElm = 0;
            Vector<Integer> classId = new Vector<>();

            for (int[] j : ac_typeList.getActl()) {

                int elm = j[3];

                if (elm != preElm) {
                    classId.add(elm);
                    preElm = elm;
                }
            }

            actd = new int[classId.size() * 3][3];

            ac_class.setIds(classId);

            pst = conn.prepareStatement("SELECT id, ac_class_id, tbl_acc_type_id FROM ac_type_details WHERE ac_class_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            int r = 0;
            for (int elm : classId) {

                pst.setInt(1, elm);
                rs = pst.executeQuery();

                if (!rs.isBeforeFirst()) {
                    //school.resetAll();
                }

                while (rs.next()) {
                    actd[r][0] = rs.getInt(1);
                    actd[r][1] = rs.getInt(2);
                    actd[r][2] = rs.getInt(3);
                    ++r;
                }

                /*for (int[] j : actd) {
                    for (int k : j) {
                        System.out.print(k + " ");
                    }
                    System.out.println("");
                }*/
            }
            ac_typeDetails.setActd(actd);

           /* for (int[] j : actd) {
                for (int k : j) {
                    System.out.print(k + " ");
                }
                System.out.println("");
            }*/

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

    public void getACC_DetailsByClassId() {
        ResultSet rs = null;
        String[][] acc = null;

        try {

            acc = new String[ac_class.getIds().size()][5];

            pst = conn.prepareStatement("SELECT acc.id,acc.ac_id,sub.id,sub.name,tea.id, concat(tea.fname,' ',tea.lname) teaName FROM ac_class acc INNER JOIN teacher_has_subject ths on acc.teacher_has_subject_id = ths.id INNER JOIN subject sub on ths.subject_id = sub.id INNER JOIN teacher tea on ths.teacher_id = tea.id WHERE acc.id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            Vector<Integer> classId = ac_class.getIds();
            int r = 0;
            for (int elm : classId) {

                pst.setInt(1, elm);
                rs = pst.executeQuery();

                if (!rs.isBeforeFirst()) {
                    //school.resetAll();
                }

                while (rs.next()) {
                    acc[r][0] = rs.getString(1);
                    academicCourse.setId(rs.getInt(2));
                    acc[r][1] = rs.getString(3);
                    acc[r][2] = rs.getString(4);
                    acc[r][3] = rs.getString(5);
                    acc[r][4] = rs.getString(6);
                    ++r;
                }

            }

            ac_class.setAcc(acc);

            /*String[][] accd = ac_class.getAcc();

            for (String[] j : accd) {
                for (String k : j) {
                    System.out.print(k + " ");
                }
                System.out.println("");
            }*/

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

    public void getAC_DetailsByCourseId() {
        ResultSet rs = null;

        try {

            pst = conn.prepareStatement("SELECT * FROM academic_course ac INNER JOIN exam ex on ac.exam_id = ex.id INNER JOIN stream st on ac.stream_id = st.id WHERE ac.id = ?");

            pst.setInt(1, academicCourse.getId());
            rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                //school.resetAll();
            }

            while (rs.next()) {
                academicCourse.setId(rs.getInt("ac.id"));
                academicCourse.setExam_year(rs.getString("ac.exam_year"));

                exam.setId(rs.getInt("ex.id"));
                exam.setExam(rs.getString("ex.exam"));

                stream.setId(rs.getInt("st.id"));
                stream.setStream(rs.getString("st.stream"));
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

    public void fillSubjectInfoTable(TableView tblSubjectInfo) {
        ObservableList<AU_StudentController.SubjectList> subjectList = FXCollections.observableArrayList();
        try {
            String[][] acc = ac_class.getAcc();
            int[][] actd = ac_typeDetails.getActd();
            int[][] actl = ac_typeList.getActl();

            Map<Integer, Integer> classList = new HashMap();

            for (int[] row : actd) {
                //System.out.println(row[0]);
                classList.put(row[0], 0);
            }

            for (int[] row : actl) {
                //System.out.println(row[1]);
                classList.put(row[1], row[1]);
            }

            for (int val : classList.values()) {
                //System.out.print(val + " ");
            }
            System.out.println("");

            int row = 0;
            int lasPosition = 0;
            int count = 0;

            for (String[] raw : acc) {
                //System.out.println(raw[0]);
                JFXCheckBox cmbTheory = new JFXCheckBox();
                JFXCheckBox cmbRevision = new JFXCheckBox();
                JFXCheckBox cmbPaper = new JFXCheckBox();

                int position = 0;
                int round = 0;
                for (int val : classList.values()) {

                    if (position == lasPosition) {
                        System.out.print(val + " ");

                        ++round;
                        if (round == 3) {
                            System.out.println(" ");
                            break;
                        }
                    }
                    ++position;
                }
                lasPosition = position;

                subjectList.add(
                        new AU_StudentController.SubjectList(Integer.parseInt(raw[1]), raw[2], Integer.parseInt(raw[3]), raw[4], cmbTheory, cmbRevision, cmbPaper)
                );
            }
            tblSubjectInfo.setItems(subjectList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*try {
                if (!rs.isClosed()) {
                    rs.close();
                }
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }

}
