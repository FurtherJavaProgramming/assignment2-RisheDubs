package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class AdminDashboard {
    private Stage primaryStage;

    public AdminDashboard(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void display() {
        VBox root = new VBox();
        Label label = new Label("Welcome to the Admin Dashboard");
        root.getChildren().add(label);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
