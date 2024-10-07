package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.OrderHistoryController;
import model.User;

public class OrderHistoryScreen {

    private Stage primaryStage;
    private User user;

    // Constructor to accept the current user and primary stage
    public OrderHistoryScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        try {
            // Correct FXML file path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/order_history.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the primaryStage and user to it
            OrderHistoryController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setUser(user);

            // Set the scene and show the stage
            Scene scene = new Scene(root, 800, 400);
            primaryStage.setTitle("Order History");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
