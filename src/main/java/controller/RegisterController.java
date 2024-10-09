package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserService;

public class RegisterController {

    private UserService userService = new UserService();
    private Stage primaryStage;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Label messageLabel;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void display() {
        // Code to display registration form (FXML loading logic)
        // Assuming you have a registration.fxml file
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userService.registerUser(username, password)) {
            messageLabel.setText("Registration successful!");
            // Redirect to login screen
        } else {
            messageLabel.setText("Registration failed.");
        }
    }
}
