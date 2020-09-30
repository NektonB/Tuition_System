package Controllers;

import DB_Conn.ConnectDB;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportViewer {

    Connection conn;

    public ReportViewer() {
        conn = ConnectDB.getConn();
    }

    public void getClassAttendance(String Stream, String ExamYear, String Subject, String ClassType, String Date, String TeacherName, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\Tuition_System\\Reports\\ClassAttendance.jrxml";

            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();

            parameter.put("Stream", Stream);
            parameter.put("ExamYear", ExamYear);
            parameter.put("Subject", Subject);
            parameter.put("ClassType", ClassType);
            parameter.put("Date", Date);
            String[] name = TeacherName.split(" ");
            parameter.put("TeacherFname", name[0]);
            parameter.put("TeacherLname", name[1]);

            JasperPrint printIt = JasperFillManager.fillReport(RI, parameter, conn);

            if (viewType.equals("PRINT")) {
                JasperPrintManager.printReport(printIt, false);
            } else if (viewType.equals("VIEW")) {
                //JasperViewer.viewReport(printIt, false);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JasperViewer.viewReport(printIt, false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();

        }
    }

    public void getClassPayment(String Stream, String ExamYear, String Subject, String ClassType, String payYear, String payMonth, String TeacherName, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\Tuition_System\\Reports\\ClassPayment.jrxml";

            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();

            parameter.put("Stream", Stream);
            parameter.put("ExamYear", ExamYear);
            parameter.put("Subject", Subject);
            parameter.put("ClassType", ClassType);
            parameter.put("PayYear", payYear);
            parameter.put("PayMonth", payMonth);
            String[] name = TeacherName.split(" ");
            parameter.put("TeacherFname", name[0]);
            parameter.put("TeacherLname", name[1]);

            JasperPrint printIt = JasperFillManager.fillReport(RI, parameter, conn);

            if (viewType.equals("PRINT")) {
                JasperPrintManager.printReport(printIt, false);
            } else if (viewType.equals("VIEW")) {
                //JasperViewer.viewReport(printIt, false);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JasperViewer.viewReport(printIt, false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();

        }
    }

    public void getTeacherPayment(String payYear, String payMonth, String TeacherName, String viewType) {
        try {
            String path = "C:\\Program Files\\Common Files\\Tuition_System\\Reports\\TeacherPayment.jrxml";

            JasperReport RI = JasperCompileManager.compileReport(path);
            Map<String, Object> parameter = new HashMap<>();

            parameter.put("PayYear", payYear);
            parameter.put("PayMonth", payMonth);
            String[] name = TeacherName.split(" ");
            parameter.put("TeacherFname", name[0]);
            parameter.put("TeacherLname", name[1]);

            JasperPrint printIt = JasperFillManager.fillReport(RI, parameter, conn);

            if (viewType.equals("PRINT")) {
                JasperPrintManager.printReport(printIt, false);
            } else if (viewType.equals("VIEW")) {
                //JasperViewer.viewReport(printIt, false);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JasperViewer.viewReport(printIt, false);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();

        }
    }
}
