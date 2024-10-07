package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.UserManager;

public class RegisterController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label registerMessageLabel;

    private UserManager userManager = new UserManager();  // Simulating user database

    @FXML
    public void handleRegister() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        if (userManager.addUser(newUsername, newPassword)) {
            registerMessageLabel.setText("Registration successful!");
        } else {
            registerMessageLabel.setText("Username already taken. Please try a different one.");
        }
    }
}
