package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "001528";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/katatest_db";

    private Util() {}

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных");
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
