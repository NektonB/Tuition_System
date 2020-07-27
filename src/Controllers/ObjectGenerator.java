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
}
