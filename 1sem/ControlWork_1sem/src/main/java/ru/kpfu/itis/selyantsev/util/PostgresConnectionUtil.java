package ru.kpfu.itis.selyantsev.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresConnectionUtil.class);
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/controlwork_sem1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "rootroot";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}