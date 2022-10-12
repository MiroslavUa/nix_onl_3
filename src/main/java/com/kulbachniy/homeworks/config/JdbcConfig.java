package com.kulbachniy.homeworks.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConfig {
    private static final String URL = "jdbc:postgresql://localhost/trading";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Miroslav";

    private static Connection connection;

    private JdbcConfig() {}

    @SneakyThrows
    public static Connection getConnection() {
        Class.forName("org.postgresql.Driver");
        if(connection == null){
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
