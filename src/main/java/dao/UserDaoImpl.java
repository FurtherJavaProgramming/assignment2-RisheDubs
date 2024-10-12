package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDaoImpl implements UserDao {
    private final String TABLE_NAME = "users";

    @Override
    public void setup() throws SQLException {
        // Ensure the table exists
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                   + "username TEXT PRIMARY KEY, "
                   + "password TEXT NOT NULL, "
                   + "first_name TEXT NOT NULL, "
                   + "last_name TEXT NOT NULL)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        }
    }

    @Override
    public User getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create and return a User object if the credentials match
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    return user;
                }
            }
        }
        return null;  // Return null if no user is found
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName) throws SQLException {
        // Check if the username already exists
        if (userExists(username)) {
            throw new SQLException("Username already exists.");
        }

        String sql = "INSERT INTO users (username, password, first_name, last_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.executeUpdate();
        }

        return new User(username, password, firstName, lastName);
    }
    
    // Helper method to check if a user exists
    private boolean userExists(String username) throws SQLException {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // If a result is returned, the user exists
            }
        }
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        // Query to check if a user exists with the given username
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Return a User object if found
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    return user;
                }
            }
        }
        return null;  // Return null if no user is found
    }
    
    @Override
    public boolean updateUserProfile(String username, String firstName, String lastName, String password) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, password);  // Assume passwords are stored in plain text, consider hashing for production
            stmt.setString(4, username);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;  // Return true if update is successful
        }
    }
}
