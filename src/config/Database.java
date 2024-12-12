/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author hieun
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static boolean checkConnection() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu!", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(URL, USER, PASSWORD);

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
