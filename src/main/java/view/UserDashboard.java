package view;

import model.User;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class UserDashboard {
    private Stage primaryStage;
    private User user;

    public UserDashboard(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
    }

    public void display() {
        VBox root = new VBox();
        Label label = new Label("Welcome " + user.getUsername() + " to your Dashboard");
        root.getChildren().add(label);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("User Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
