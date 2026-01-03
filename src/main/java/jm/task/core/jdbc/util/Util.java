package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "A95782153sql#";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Подключение выполнено\n");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    /*
    public static void setConnection(Connection connection) {
        Util.connection = connection;
    }
     */
}