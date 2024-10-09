package repository;

import model.User;

public interface UserRepository {
    User findUserByUsernameAndPassword(String username, String password);
    boolean authenticate(String username, String password);
    User getUser(String username);
    void addUser(User user);
}
