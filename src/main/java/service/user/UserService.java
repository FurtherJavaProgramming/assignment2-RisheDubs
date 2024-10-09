// UserService.java
package service.user;

import model.User;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public void registerUser(User user) {
        userRepository.addUser(user);
    }
}
