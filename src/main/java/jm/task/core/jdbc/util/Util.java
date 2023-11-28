package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String USER_NAME = "root";
    private static final String PASSWORD = "001528";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/katatest_db";

    private static Connection connection = null;
    private static SessionFactory sessionFactory;

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

    public static SessionFactory getConnectionHibernate() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.URL, CONNECTION_URL + "?useSSL=false");
                settings.put(Environment.USER, USER_NAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create");
                sessionFactory = new Configuration()
                        .addProperties(settings)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
                System.out.println("Успешное соединение с БД");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка соединения с БД");
            }
        }
        return sessionFactory;
    }

    public static void closeConnectionHibernate() {
        try {
            getConnectionHibernate().close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Ошибка при закрытии соединения с БД");
        }
    }
}
