/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author hieun
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3307/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

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
