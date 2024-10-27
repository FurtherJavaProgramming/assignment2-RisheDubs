import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import controller.LoginController;
import controller.SignupController;
import model.Model;

public class Main extends Application {

    private Model model;

    @Override
    public void init() {
        model = new Model();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the LoginView.fxml file
        	FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/view/LoginView.fxml"), 
                    "LoginView.fxml not found.")
                );
            
            BorderPane root = loader.load();

            // Get the controller instance from the FXML loader
            LoginController loginController = loader.getController();
            
            // Set the stage and model for the controller
            loginController.setStage(primaryStage);
            loginController.setModel(model);

            // Set up the scene and show the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Page");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadSignupView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));
            BorderPane signupRoot = loader.load();

            SignupController signupController = loader.getController();
            signupController.setModel(model);  // Pass the initialized model
            signupController.setStage(stage);

            Scene signupScene = new Scene(signupRoot);
            stage.setScene(signupScene);
            stage.setTitle("Signup Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}