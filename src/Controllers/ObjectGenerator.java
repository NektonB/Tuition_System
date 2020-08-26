package Controllers;

import DataControllers.DataReader;
import DataControllers.DataWriter;
import Modules.*;

public class ObjectGenerator {

    private static DataReader dataReader;
    private static DataWriter dataWriter;
    private static Alerts alerts;
    private static BackupData backupData;
    private static ConnectionInfo connectionInfo;
    private static AutoBackup autoBackup;
    private static User user;
    private static UserType userType;
    private static ComponentSwitcher componentSwitcher;
    private static DateFormatConverter dateFormatConverter;
    private static Employee employee;
    private static Status status;
    private static Teacher teacher;
    private static TeacherHasSubject teacherHasSubject;
    private static Subject subject;
    private static NearCity nearCity;
    private static School school;
    private static Stream stream;
    private static Exam exam;
    private static Guardian guardian;
    private static Student student;
    private static AcademicCourse academicCourse;
    private static AC_Class ac_class;
    private static AC_TypeDetails ac_typeDetails;
    private static ACC_Type acc_type;

    private static TextValidator textValidator;
    private static TimeFormatConverter timeFormatConverter;
    private static ReportViewer reportViewer;

    public static synchronized void readyAll() {
        try {
            String ready = "ready";
            alerts = new Alerts();
            dataReader = new DataReader();
            dataWriter = new DataWriter();
            backupData = new BackupData();

        } catch (Exception e) {
            e.printStackTrace();
            alerts.getWarningAlert("Error Alert", "Something went wrong..", e.toString());
        }
    }

    public static synchronized Alerts getAlerts() {
        if (alerts == null) {
            alerts = new Alerts();
        }
        return alerts;
    }

    public static synchronized User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static synchronized UserType getUserType() {
        if (userType == null) {
            userType = new UserType();
        }
        return userType;
    }

    public static synchronized DataReader getDataReader() {
        if (dataReader == null) {
            dataReader = new DataReader();
        }
        return dataReader;
    }

    public static synchronized DataWriter getDataWriter() {
        if (dataWriter == null) {
            dataWriter = new DataWriter();
        }
        return dataWriter;
    }

    public static synchronized BackupData getBackupData() {
        if (backupData == null) {
            backupData = new BackupData();
        }
        return backupData;
    }

    public static synchronized ConnectionInfo getConnectionInfo() {
        if (connectionInfo == null) {
            connectionInfo = new ConnectionInfo();
        }
        return connectionInfo;
    }

    public static synchronized AutoBackup getAutoBackup() {
        if (autoBackup == null) {
            autoBackup = new AutoBackup();
        }
        return autoBackup;
    }

    public static synchronized ComponentSwitcher getComponentSwitcher() {
        if (componentSwitcher == null) {
            componentSwitcher = new ComponentSwitcher();
        }
        return componentSwitcher;
    }

    public static synchronized DateFormatConverter getDateFormatConverter() {
        if (dateFormatConverter == null) {
            dateFormatConverter = new DateFormatConverter();
        }
        return dateFormatConverter;
    }

    public static synchronized TextValidator getTextValidator() {
        if (textValidator == null) {
            textValidator = new TextValidator();
        }
        return textValidator;
    }

    public static synchronized TimeFormatConverter getTimeFormatConverter() {
        if (timeFormatConverter == null) {
            timeFormatConverter = new TimeFormatConverter();
        }
        return timeFormatConverter;
    }

    public static synchronized ReportViewer getReportViewer() {
        if (reportViewer == null) {
            reportViewer = new ReportViewer();
        }
        return reportViewer;
    }

    public static synchronized Employee getEmployee() {
        if (employee == null) {
            employee = new Employee();
        }
        return employee;
    }

    public static synchronized Status getStatus() {
        if (status == null) {
            status = new Status();
        }
        return status;
    }

    public static synchronized Teacher getTeacher() {
        if (teacher == null) {
            teacher = new Teacher();
        }
        return teacher;
    }

    public static synchronized TeacherHasSubject getTeacherHasSubject() {
        if (teacherHasSubject == null) {
            teacherHasSubject = new TeacherHasSubject();
        }
        return teacherHasSubject;
    }

    public static synchronized Subject getSubject() {
        if (subject == null) {
            subject = new Subject();
        }
        return subject;
    }

    public static synchronized NearCity getNearCity() {
        if (nearCity == null) {
            nearCity = new NearCity();
        }
        return nearCity;
    }

    public static synchronized School getSchool() {
        if (school == null) {
            school = new School();
        }
        return school;
    }

    public static synchronized Stream getStream() {
        if (stream == null) {
            stream = new Stream();
        }
        return stream;
    }

    public static synchronized Exam getExam() {
        if (exam == null) {
            exam = new Exam();
        }
        return exam;
    }

    public static synchronized Guardian getGuardian() {
        if (guardian == null) {
            guardian = new Guardian();
        }
        return guardian;
    }

    public static synchronized Student getStudent() {
        if (student == null) {
            student = new Student();
        }
        return student;
    }

    public static synchronized AcademicCourse getAcademicCourse() {
        if (academicCourse == null) {
            academicCourse = new AcademicCourse();
        }
        return academicCourse;
    }

    public static synchronized AC_Class getAc_class() {
        if (ac_class == null) {
            ac_class = new AC_Class();
        }
        return ac_class;
    }

    public static synchronized AC_TypeDetails getAc_typeDetails() {
        if (ac_typeDetails == null) {
            ac_typeDetails = new AC_TypeDetails();
        }
        return ac_typeDetails;
    }

    public static ACC_Type getAcc_type() {
        if (acc_type == null) {
            acc_type = new ACC_Type();
        }
        return acc_type;
    }
}
