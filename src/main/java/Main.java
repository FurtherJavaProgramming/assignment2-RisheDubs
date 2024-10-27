import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import controller.LoginController;
import model.Model;

public class Main extends Application {

    private Model model;

    @Override
    public void init() {
        model = new Model();  // Initialize your model
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the LoginView.fxml file
        	FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/view/LoginView.fxml"), 
                    "LoginView.fxml not found.")
                );
            
            // Since the new LoginView.fxml uses BorderPane as the root, cast it to BorderPane
            BorderPane root = loader.load();  // Changed from GridPane to BorderPane

            // Get the controller instance from the FXML loader
            LoginController loginController = loader.getController();
            
            // Set the stage and model for the controller
            loginController.setStage(primaryStage);
            loginController.setModel(model);

            // Set up the scene and show the stage
            Scene scene = new Scene(root);  // No need to change this part
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Page");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
