package service;

public class UserService {

    // Placeholder for actual authentication logic (could be a DB check)
    public boolean authenticateUser(String username, String password) {
        // For now, we're just using a hardcoded check
        return username.equals("admin") && password.equals("admin");
    }

    // Future method for user registration
    public boolean registerUser(String username, String password) {
        // Logic for registering a user
        System.out.println("User registered: " + username);
        return true;
    }
}
