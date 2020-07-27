/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Conn;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {

    private static Connection conn;
    private static ResultSet rs;
    private static String serverIP, port, database, username, password;
    //To Connect DB.........

    public static Connection getConn() {
        if (conn == null) {
            try {
                serverIP = ConnConfig.getServerIP();
                port = ConnConfig.getServerPort();
                database = ConnConfig.getDatabase();
                username = ConnConfig.getLoggedUser();
                password = ConnConfig.getLoggedPassword();
                //Class.forName("com.mysql.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + serverIP + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", "" + username + "", "" + password + "");
            } catch (ClassNotFoundException e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "No Database Driver found", "Database Driver Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                Toolkit.getDefaultToolkit().beep();
                //e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Connection fail", "SQL Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return conn;
    }

    //close ResultSet
    public static void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
