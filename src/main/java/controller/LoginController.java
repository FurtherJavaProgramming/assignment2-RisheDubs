package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("user") && password.equals("password")) {
            // Successful login, load the user dashboard
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/user_dashboard.fxml"));
                Scene dashboardScene = new Scene(loader.load());

                // Set the dashboard scene
                primaryStage.setScene(dashboardScene);
                primaryStage.show();

            } catch (Exception e) {
                e.printStackTrace();
                messageLabel.setText("Failed to load dashboard.");
            }
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }

    @FXML
    public void handleRegister() {
        try {
            // Ensure the path to the FXML file is correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/register.fxml"));

            // Load the registration screen
            Scene registerScene = new Scene(loader.load());

            // Set the new scene on the primary stage
            primaryStage.setScene(registerScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();  // Print the error stack trace to help diagnose issues
        }
    }
}
