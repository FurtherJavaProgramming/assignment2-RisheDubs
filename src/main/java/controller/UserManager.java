package controller;

import java.util.HashMap;
import model.User;

public class UserManager {
    // Simulating a user database with HashMap
    private HashMap<String, User> users = new HashMap<>();

    // Constructor to populate some default users
    public UserManager() {
        // Adding some users for demonstration purposes
        users.put("admin", new User("admin", "reading_admin", "admin"));
        users.put("user", new User("user", "password", "user"));
    }

    // Method to authenticate users
    public boolean authenticate(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            return user.getPassword().equals(password);
        }
        return false;
    }

    // Method to retrieve user details by username
    public User getUser(String username) {
        return users.get(username);
    }
}
