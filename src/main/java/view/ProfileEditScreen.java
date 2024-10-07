package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ProfileEditController;
import model.User;

public class ProfileEditScreen {

    private Stage primaryStage;
    private User user;

    // Constructor to accept the current user and primary stage
    public ProfileEditScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        try {
            // Correct the path to the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profile_edit.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the primaryStage and user to it
            ProfileEditController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setUser(user);

            // Set the scene and show the stage
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("Edit Profile");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
