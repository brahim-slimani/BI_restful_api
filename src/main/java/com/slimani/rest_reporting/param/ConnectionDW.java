package com.slimani.rest_reporting.param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDW {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/datamart_branchement";
            Class.forName("org.postgresql.Driver");
            connection = (Connection) DriverManager.getConnection(url, "postgres", "postgres");

        } catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }
}
