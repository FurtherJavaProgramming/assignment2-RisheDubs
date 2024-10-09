package service;

import model.User;
import repository.UserRepository;

public class UserManager {
    private UserRepository userRepository;

    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        return userRepository.authenticate(username, password);
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    public String getUserRole(String username) {
        User user = getUser(username);
        return user.getRole();
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }
}
