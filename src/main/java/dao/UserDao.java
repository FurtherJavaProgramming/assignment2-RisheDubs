package dao;

import java.sql.SQLException;

import model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * The DAO maps application calls to the persistence layer and provides specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
    // Set up the database schema or connection (if needed)
    void setup() throws SQLException;
    
    // Retrieve a user by username and password (for login)
    User getUser(String username, String password) throws SQLException;
    
    // Create a new user (for registration)
    User createUser(String username, String password, String firstName, String lastName) throws SQLException;

    // Add this method to check if a username already exists
    User getUserByUsername(String username) throws SQLException;
}
