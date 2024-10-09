package repository;

import model.User;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users;

    public InMemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Other method implementations...
}
