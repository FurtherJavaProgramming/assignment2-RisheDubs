package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Model;
import model.User;

public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private Label message;

    private Stage stage;
    private Model model;

    public LoginController() {
        // No-argument constructor required by FXMLLoader
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        // Add event handler for login button
        login.setOnAction(event -> {
            // No SQLException should be caught here directly, as handleLogin() may handle it internally
            handleLogin();  // Call handleLogin without the try-catch here
        });

        // Add event handler for signup button (redirect to signup page)
        signup.setOnAction(event -> openRegistration());
    }

    // Handle login validation and logic
 // Handle login validation and logic
    private void handleLogin() {
        if (name.getText().isEmpty() || password.getText().isEmpty()) {
            message.setText("Username or password cannot be empty.");
            message.setTextFill(Color.RED);
        } else {
            try {
                // Fetch user by username and password
                User user = model.getUserDao().getUser(name.getText(), password.getText());

                if (user != null) {
                    model.setCurrentUser(user);  // Set the current user in the model

                    if (user.getUsername().equals("admin")) {
                        loadAdminPage();  // Redirect admin to the admin page
                    } else {
                        loadHomePage();  // Redirect regular users to the home page
                    }
                } else {
                    message.setText("Invalid username or password.");
                    message.setTextFill(Color.RED);
                }
            } catch (SQLException e) {
                message.setText("Database error: " + e.getMessage());
                message.setTextFill(Color.RED);
                e.printStackTrace();
            }
        }
    }

    // Load the admin page (if applicable)
    private void loadAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
            VBox adminRoot = loader.load();  // Ensure the FXML root is of the correct type

            AdminController adminController = loader.getController();
            adminController.setModel(model);
            adminController.setStage(stage);

            Scene adminScene = new Scene(adminRoot);
            stage.setScene(adminScene);
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Open registration window or handle signup logic
    private void openRegistration() {
        try {
            // Load the SignupView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));
            BorderPane signupRoot = loader.load();  // Load as BorderPane since the new design is using BorderPane

            // Get the SignupController and pass the model
            SignupController signupController = loader.getController();
            signupController.setModel(model);  // Pass the model to the signup controller

            // Create and set the new stage in the controller
            Stage signupStage = new Stage();
            signupController.setStage(signupStage);  // Pass the stage to the SignupController

            // Create a new scene for the registration page
            Scene signupScene = new Scene(signupRoot);

            // Set the scene and show the new stage
            signupStage.setScene(signupScene);
            signupStage.setTitle("Register");
            signupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadHomePage() {
    	try {
            // Load the HomeView.fxml, make sure the path is correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));

            // Since the root is an AnchorPane, cast it correctly
            AnchorPane homeRoot = loader.load();  

            // Get the HomeController and set the model and stage
            HomeController homeController = loader.getController();
            homeController.setModel(model);
            homeController.setStage(stage);

            // Create a new scene with the correct root node
            Scene homeScene = new Scene(homeRoot);
            stage.setScene(homeScene);
            stage.setTitle("Home");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showStage() {
        stage.show();
    }
}
