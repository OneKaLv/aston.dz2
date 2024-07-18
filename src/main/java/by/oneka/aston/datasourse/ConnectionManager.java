package by.oneka.aston.datasourse;

import lombok.experimental.FieldNameConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@FieldNameConstants
public class ConnectionManager {
    String URL = "jdbc:postgresql://localhost:5432/postgres";
    String USERNAME = "sa";
    String PASSWORD = "";
    private static ConnectionManager connectionManager;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static synchronized ConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver not loaded");
            }
        }

        return connectionManager;
    }
}
