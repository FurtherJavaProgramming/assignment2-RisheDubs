package service.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String filePath = "resources/sql/initialize_db.sql";  // Ensure this path matches your project structure

        try (Connection conn = DatabaseConnection.getConnection();
             BufferedReader br = new BufferedReader(new FileReader(filePath));
             Statement stmt = conn.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line);
            }

            // Execute the SQL commands
            stmt.executeUpdate(sql.toString());
            System.out.println("Database tables created successfully.");

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("File read error: " + e.getMessage());
        }
    }
}
