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
    Teacher teacher;
    TeacherHasSubject teacherHasSubject;
    Subject subject;
    User user;
    UserType userType;
    NearCity nearCity;
    School school;


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
                teacher = ObjectGenerator.getTeacher();
                teacherHasSubject = ObjectGenerator.getTeacherHasSubject();
                subject = ObjectGenerator.getSubject();
                user = ObjectGenerator.getUser();
                userType = ObjectGenerator.getUserType();
                nearCity = ObjectGenerator.getNearCity();
                school = ObjectGenerator.getSchool();
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

    public int updateEmployee() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE employee SET fname = ?, lname = ?, nic_number = ?, address = ?, contact_number = ?, status_id = ? WHERE id = ?");
            pst.setString(1, employee.getFname());
            pst.setString(2, employee.getLname());
            pst.setString(3, employee.getNic_number());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getContact_number());
            pst.setInt(6, status.getId());
            pst.setInt(7, employee.getId());

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

    public int saveSubject() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO subject(name) VALUES (?)");
            pst.setString(1, subject.getName());

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

    public int updateSubject() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE subject SET name = ? WHERE id = ?");
            pst.setString(1, subject.getName());
            pst.setInt(2, subject.getId());

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

    public int deleteSubject() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM subject WHERE id = ?");
            pst.setInt(1, subject.getId());

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

    public int deleteTeacherSubject() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM teacher_has_subject WHERE teacher_id = ? AND  subject_id = ?");
            pst.setInt(1, teacher.getId());
            pst.setInt(2, subject.getId());

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

    public int saveTeacher() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO teacher(fname, lname, nic_number, address, home_number, mobile_number, email, status_id) VALUES (?,?,?,?,?,?,?,?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, teacher.getFname());
            pst.setString(2, teacher.getLname());
            pst.setString(3, teacher.getNic_number());
            pst.setString(4, teacher.getAddress());
            pst.setString(5, teacher.getHome_number());
            pst.setString(6, teacher.getMobile_number());
            pst.setString(7, teacher.getEmail());
            pst.setInt(8, status.getId());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                teacher.setId(rs.getInt(1));
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
        return operation;
    }

    public int saveTeacherSubjectList() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO teacher_has_subject(teacher_id, subject_id) VALUES (?,?)");
            pst.setInt(1, teacher.getId());
            pst.setInt(2, subject.getId());

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

    public int deleteTeacherSubjectListItem() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM teacher_has_subject WHERE teacher_id = ? AND subject_id = ?");
            pst.setInt(1, teacher.getId());
            pst.setInt(2, subject.getId());

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

    public int updateTeacher() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE teacher SET fname = ?, lname = ?, nic_number = ?, address = ?, home_number = ?, mobile_number = ?, email = ?, status_id = ? WHERE id = ?");
            pst.setString(1, teacher.getFname());
            pst.setString(2, teacher.getLname());
            pst.setString(3, teacher.getNic_number());
            pst.setString(4, teacher.getAddress());
            pst.setString(5, teacher.getHome_number());
            pst.setString(6, teacher.getMobile_number());
            pst.setString(7, teacher.getEmail());
            pst.setInt(8, status.getId());
            pst.setInt(9, teacher.getId());

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

    public int saveUser() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("INSERT INTO user(name, password, user_type_id, employee_id, status_id) VALUES (?,?,?,?,?)");
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, userType.getId());
            pst.setInt(4, employee.getId());
            pst.setInt(5, status.getId());

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

    public int updateUser() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE user SET name = ?, password = ?, user_type_id = ?, employee_id = ?, status_id = ? WHERE id  = ?");
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, userType.getId());
            pst.setInt(4, employee.getId());
            pst.setInt(5, status.getId());
            pst.setInt(6, user.getId());

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

    public int saveNearCity() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO near_city(city) VALUES (?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, nearCity.getCity());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                nearCity.setId(rs.getInt(1));
            }
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

    public int updateNearCity() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE near_city SET city = ? WHERE id = ?");
            pst.setString(1, nearCity.getCity());
            pst.setInt(2, nearCity.getId());

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

    public int deleteNearCity() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM near_city WHERE id = ?");
            pst.setInt(1, nearCity.getId());

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

    public int saveSchool() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO school(name) VALUES (?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, school.getName());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                school.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operation;
    }

    public int updateSchool() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE school SET name = ? WHERE id = ?");
            pst.setString(1, school.getName());
            pst.setInt(2, school.getId());

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

    public int deleteSchool() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM school WHERE id = ?");
            pst.setInt(1, school.getId());

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
