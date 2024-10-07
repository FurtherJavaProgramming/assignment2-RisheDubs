package model;

import java.util.HashMap;

public class UserManager {

    private HashMap<String, User> users = new HashMap<>();

    public UserManager() {
        // Initialize with a default user
        users.put("user", new User("user", "password"));
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;  // Username already exists
        }
        users.put(username, new User(username, password));
        return true;
    }
}
