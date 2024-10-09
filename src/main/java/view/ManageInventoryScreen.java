// ManageInventoryScreen.java
package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageInventoryScreen {
    private Stage primaryStage;

    public ManageInventoryScreen(Stage stage) {
        this.primaryStage = stage;
    }

    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/manage_inventory.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Manage Inventory");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
