package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.UserDashboardController;
import model.User;

public class UserDashboard {

    private Stage primaryStage;
    private User user;

    public UserDashboard(Stage stage, User loggedInUser) {
        this.primaryStage = stage;
        this.user = loggedInUser;
    }

    public void display() {
        try {
            // Load the FXML file for the User Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/user_dashboard.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the primaryStage and user to it
            UserDashboardController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setUser(user);

            // Set the scene and show the stage
            Scene scene = new Scene(root, 400, 300);
            primaryStage.setTitle("User Dashboard");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
