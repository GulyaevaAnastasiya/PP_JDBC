package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "001528";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/katatest_db";

    private static Connection connection = null;

    private Util() {
    }

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных");
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
