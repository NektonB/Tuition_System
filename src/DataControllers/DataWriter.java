package DataControllers;

import Controllers.AU_StudentController;
import Controllers.Alerts;
import Controllers.ObjectGenerator;
import DB_Conn.ConnectDB;
import Modules.*;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

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
    Stream stream;
    Student student;
    Guardian guardian;
    AcademicCourse academicCourse;
    Exam exam;
    AC_Class ac_class;
    AC_TypeDetails ac_typeDetails;
    ACC_Type acc_type;
    DataReader dataReader;
    AC_TypeList ac_typeList;
    AC_Attendance ac_attendance;
    ACA_Details aca_details;


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
                stream = ObjectGenerator.getStream();
                student = ObjectGenerator.getStudent();
                guardian = ObjectGenerator.getGuardian();
                academicCourse = ObjectGenerator.getAcademicCourse();
                exam = ObjectGenerator.getExam();
                ac_class = ObjectGenerator.getAc_class();
                ac_typeDetails = ObjectGenerator.getAc_typeDetails();
                acc_type = ObjectGenerator.getAcc_type();
                dataReader = ObjectGenerator.getDataReader();
                ac_typeList = ObjectGenerator.getAc_typeList();
                ac_attendance = ObjectGenerator.getAc_attendance();
                aca_details = ObjectGenerator.getAca_details();
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

    public int saveStream() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO stream(stream) VALUES (?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, stream.getStream());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                stream.setId(rs.getInt(1));
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

    public int updateStream() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("UPDATE stream SET stream = ? WHERE id = ?");
            pst.setString(1, stream.getStream());
            pst.setInt(2, stream.getId());

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

    public int deleteStream() {
        int operation = 0;
        try {
            pst = conn.prepareStatement("DELETE FROM stream WHERE id = ?");
            pst.setInt(1, stream.getId());

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

    public int saveStudent() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO student(index_number,fname, lname, nic_number, address, contact_number, email, school_id, grade, near_city_id, gardien_id, status_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, student.getIndexNumber());
            pst.setString(2, student.getF_name());
            pst.setString(3, student.getL_name());
            pst.setString(4, student.getNic_number());
            pst.setString(5, student.getAddress());
            pst.setString(6, student.getContact_number());
            pst.setString(7, student.getEmail());
            pst.setInt(8, school.getId());
            pst.setString(9, student.getGrade());
            pst.setInt(10, nearCity.getId());
            pst.setInt(11, guardian.getId());
            pst.setInt(12, status.getId());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                student.setId(rs.getInt(1));
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

    public int saveAcademicCourse() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO academic_course(exam_id, stream_id, exam_year) VALUES (?,?,?)", pst.RETURN_GENERATED_KEYS);
            pst.setInt(1, exam.getId());
            pst.setInt(2, stream.getId());
            pst.setString(3, academicCourse.getExam_year());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                academicCourse.setId(rs.getInt(1));
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

    public int[] saveACC(TableView<AU_StudentController.SubjectList> tblSubjectList) {
        int[] operation = {};
        ResultSet rs = null;

        Vector<Integer> ids = new Vector();

        try {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("INSERT INTO ac_class(ac_id, teacher_has_subject_id, status_id) VALUES (?,?,?)", pst.RETURN_GENERATED_KEYS);

            ObservableList<? extends TableColumn<?, ?>> columns = tblSubjectList.getColumns();

            for (int i = 0; i < tblSubjectList.getItems().size(); ++i) {

                subject.setId(Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString()));
                teacher.setId(Integer.parseInt(columns.get(2).getCellObservableValue(i).getValue().toString()));
                dataReader.getTHS_IdByName();

                boolean isAlready = dataReader.checkACC();
                if (!isAlready) {
                    pst.setInt(1, academicCourse.getId());
                    pst.setInt(2, teacherHasSubject.getId());
                    pst.setInt(3, status.getId());

                    pst.addBatch();
                } else {
                    ids.add(ac_class.getId());
                }
            }

            operation = pst.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

            //System.out.println("Key List : " + operation);
            rs = pst.getGeneratedKeys();

            while (rs.next()) {
                ids.add(rs.getInt(1));
                //System.out.println("Key : " + rs.getInt(1));
            }
            ac_class.setIds(ids);
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

    public int[] saveACCTD(TableView<AU_StudentController.SubjectList> tblSubjectList) {
        int[] operation = {};
        ResultSet rs = null;

        Vector<Integer> ids = new Vector<>();
        try {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("INSERT INTO ac_type_details(ac_class_id, tbl_acc_type_id) VALUES (?,?)", pst.RETURN_GENERATED_KEYS);

            ObservableList<? extends TableColumn<?, ?>> columns = tblSubjectList.getColumns();

            for (int i = 0; i < tblSubjectList.getItems().size(); ++i) {

                for (int j = 1; j < 4; j++) {

                    //int subId = Integer.parseInt(columns.get(0).getCellObservableValue(i).getValue().toString());
                    //int teacherId = Integer.parseInt(columns.get(2).getCellObservableValue(i).getValue().toString());

                    boolean isAlready = dataReader.checkACCTD(ac_class.getIds().get(i), j);

                    if (!isAlready) {
                        //System.out.println("AC Class ID: " + ac_class.getIds().get(i));
                        pst.setInt(1, ac_class.getIds().get(i));
                        pst.setInt(2, j);

                        pst.addBatch();
                    } else {
                        ids.add(ac_typeDetails.getId());
                    }
                }
            }

            operation = pst.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

            //System.out.println("Key List : " + operation);
            rs = pst.getGeneratedKeys();

            while (rs.next()) {
                ids.add(rs.getInt(1));
                //System.out.println("Key : " + rs.getInt(1));
            }
            ac_typeDetails.setIds(ids);
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

    public int[] saveACCTL(TableView<AU_StudentController.SubjectList> tblSubjectList, String exam, String stream, String year) {
        int[] operation = {};
        ResultSet rs = null;
        PreparedStatement pstUpdate = null;

        Vector<Integer> ids = new Vector<>();
        try {
            conn.setAutoCommit(false);
            pst = conn.prepareStatement("INSERT INTO ac_type_list( tbl_student_id, ac_type_details_id,status,absent_count) VALUES (?,?,?,?)", pst.RETURN_GENERATED_KEYS);
            pstUpdate = conn.prepareStatement("UPDATE ac_type_list SET status = ? WHERE id = ?", pst.RETURN_GENERATED_KEYS);

            ObservableList<? extends TableColumn<?, ?>> columns = tblSubjectList.getColumns();

            int k = 0;

            for (int i = 0; i < tblSubjectList.getItems().size(); ++i) {

                for (int j = 0; j < 3; j++) {

                    boolean isAlready = dataReader.checkACCTL(ac_typeDetails.getIds().get(k), student.getId());
                    boolean isChecked = ((JFXCheckBox) columns.get(4 + j).getCellObservableValue(i).getValue()).isSelected();

                    if ((!isAlready) && (isChecked)) {
                        System.out.println("Call");
                        pst.setInt(1, student.getId());
                        pst.setInt(2, ac_typeDetails.getIds().get(k));
                        pst.setInt(3, 1);
                        pst.setInt(4, 0);

                        pst.addBatch();
                    } else {
                        if (isChecked) {
                            pstUpdate.setInt(1, 1);
                            pstUpdate.setInt(2, ac_typeList.getId());

                            pstUpdate.addBatch();
                        } else {
                            pstUpdate.setInt(1, 0);
                            pstUpdate.setInt(2, ac_typeList.getId());

                            pstUpdate.addBatch();
                        }
                    }

                    if (isAlready) {
                        ids.add(ac_typeList.getId());
                    }
                    ++k;
                }
            }

            operation = pst.executeBatch();
            conn.commit();
            //conn.setAutoCommit(true);

            operation = pstUpdate.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

            //System.out.println("Key List : " + operation);
            rs = pst.getGeneratedKeys();

            while (rs.next()) {
                ids.add(rs.getInt(1));
                //System.out.println("Key : " + rs.getInt(1));
            }
            ac_typeList.setIds(ids);
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

    public int updateStudent() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("UPDATE student SET index_number = ?, fname = ?, lname = ?, nic_number = ?, address = ?, contact_number = ?, email = ?, school_id = ?, grade = ?, near_city_id = ?, gardien_id = ?, status_id = ? WHERE id = ?", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, student.getIndexNumber());
            pst.setString(2, student.getF_name());
            pst.setString(3, student.getL_name());
            pst.setString(4, student.getNic_number());
            pst.setString(5, student.getAddress());
            pst.setString(6, student.getContact_number());
            pst.setString(7, student.getEmail());
            pst.setInt(8, school.getId());
            pst.setString(9, student.getGrade());
            pst.setInt(10, nearCity.getId());
            pst.setInt(11, guardian.getId());
            pst.setInt(12, status.getId());
            pst.setInt(13, student.getId());

            operation = pst.executeUpdate();

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
        return operation;
    }

    public int saveAttendance() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("INSERT INTO ac_attendence(date, time, tbl_user_id, tbl_ac_type_details_id) VALUES (?,?,?,?)", pst.RETURN_GENERATED_KEYS);
            pst.setString(1, ac_attendance.getDate());
            pst.setString(2, ac_attendance.getTime());
            pst.setInt(3, user.getId());
            pst.setInt(4, ac_typeDetails.getId());

            operation = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                ac_attendance.setId(rs.getInt(1));
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

    public int saveAttendanceDetails() {
        int operation = 0;
        ResultSet rs = null;
        Vector<Integer> ids = new Vector();
        try {
            pst = conn.prepareStatement("INSERT INTO aca_details(ac_attendence_id, student_id, status) VALUES (?,?,?)", pst.RETURN_GENERATED_KEYS);
            pst.setInt(1, ac_attendance.getId());
            pst.setInt(2, student.getId());
            pst.setString(3, aca_details.getStatus());

            operation = pst.executeUpdate();
            /*rs = pst.getGeneratedKeys();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
            aca_details.setIds(ids);*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
                /*if (!rs.isClosed()) {
                    rs.close();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operation;
    }

    public int[] saveAllAttendanceDetails() {
        int[] operation = {};
        ResultSet rs = null;
        PreparedStatement pstStudentList;
        Vector<Integer> ids = new Vector();
        try {
            conn.setAutoCommit(false);

            pst = conn.prepareStatement("INSERT INTO aca_details(ac_attendence_id, student_id, status) VALUES (?,?,?)", pst.RETURN_GENERATED_KEYS);

            pstStudentList = conn.prepareStatement("SELECT tbl_student_id FROM ac_type_list WHERE ac_type_details_id = ? AND status = '1' ");
            pstStudentList.setInt(1, ac_typeDetails.getId());
            rs = pstStudentList.executeQuery();
            while (rs.next()) {
                student.setId(rs.getInt(1));
                boolean isAlready = dataReader.checkAttendanceDetails();
                if (!isAlready) {
                    pst.setInt(1, ac_attendance.getId());
                    pst.setInt(2, rs.getInt(1));
                    pst.setString(3, "Not Attended");
                    pst.addBatch();
                }
            }


            operation = pst.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            /*rs = pst.getGeneratedKeys();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
            aca_details.setIds(ids);*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
                /*if (!rs.isClosed()) {
                    rs.close();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operation;
    }

    public int updateAttendanceDetails() {
        int operation = 0;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("UPDATE aca_details SET status = ? WHERE id = ?");
            pst.setString(1, aca_details.getStatus());
            pst.setInt(2, aca_details.getId());

            operation = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
                /*if (!rs.isClosed()) {
                    rs.close();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return operation;
    }
}
