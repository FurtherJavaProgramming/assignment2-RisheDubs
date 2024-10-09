package controller;

import model.Admin;
import model.Customer;
import model.User;
import service.user.UserService;
import view.AdminDashboard;
import view.UserDashboard;
import injector.DependencyInjector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    private UserService userService;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    public LoginController() {
        this.userService = DependencyInjector.getUserService();
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userService.loginUser(username, password);

        if (user != null) {
            if (user instanceof Admin) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.display();
            } else if (user instanceof Customer) {
                UserDashboard userDashboard = new UserDashboard(user);
                userDashboard.display();
            }
        } else {
            // Handle invalid login (e.g., show error message)
            System.out.println("Invalid username or password.");
        }
    }
}
