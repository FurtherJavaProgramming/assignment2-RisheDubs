package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.BookListController;
import model.User;

public class BookListScreen {

    private Stage primaryStage;
    private User user;

    // Constructor to accept the current user and primary stage
    public BookListScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        try {
            // Correct FXML file path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/book_list.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the primaryStage and user to it
            BookListController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setUser(user);

            // Set the scene and show the stage
            Scene scene = new Scene(root, 800, 400);
            primaryStage.setTitle("Book List");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
