package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ShoppingCartController;
import model.User;

public class ShoppingCartScreen {

    private Stage primaryStage;
    private User user;

    public ShoppingCartScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        try {
            // Ensure the path to FXML is correct
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/shopping_cart.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the primaryStage and user to it
            ShoppingCartController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setUser(user);

            // Set the scene and show the stage
            Scene scene = new Scene(root, 800, 400);
            primaryStage.setTitle("Shopping Cart");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
