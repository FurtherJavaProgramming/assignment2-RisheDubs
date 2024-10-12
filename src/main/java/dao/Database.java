package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // URL pattern for database
    private static final String DB_URL = "jdbc:sqlite:application.db";

    // Method to establish and return a connection to the SQLite database
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Establish the connection
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            // Log the exception (you could use a logger here instead of printing)
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw e;  // Propagate the exception upwards
        }
        return connection;  // Return the established connection
    }
}
