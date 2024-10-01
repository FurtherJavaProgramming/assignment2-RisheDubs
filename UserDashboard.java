import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserDashboard {

    private Stage primaryStage;
    private User user;

    // Constructor to pass the primaryStage and the logged-in user
    public UserDashboard(Stage stage, User loggedInUser) {
        this.primaryStage = stage;
        this.user = loggedInUser;
    }

    // Method to display the dashboard
    public void display() {
        // Welcome message
        Label welcomeLabel = new Label("Welcome, " + user.getUsername() + "!");

        // Buttons for user actions
        Button viewBooksButton = new Button("View Books");
        Button shoppingCartButton = new Button("Shopping Cart");
        Button viewOrdersButton = new Button("View Orders");
        Button logoutButton = new Button("Logout");

        // Layout
        VBox layout = new VBox(10);  // 10px spacing between elements
        layout.getChildren().addAll(welcomeLabel, viewBooksButton, shoppingCartButton, viewOrdersButton, logoutButton);

        // Scene setup
        Scene userDashboardScene = new Scene(layout, 400, 300);
        primaryStage.setScene(userDashboardScene);
        primaryStage.setTitle("User Dashboard");
        primaryStage.show();

        // Set up actions for buttons (currently placeholders)
        logoutButton.setOnAction(e -> logout());
    }

    // Placeholder methods for actions
    private void viewBooks() {
        System.out.println("Navigating to book list...");
    }

    private void manageShoppingCart() {
        System.out.println("Opening shopping cart...");
    }

    private void viewOrderHistory() {
        System.out.println("Viewing order history...");
    }

    // Logout method to return to the login screen
    private void logout() {
        primaryStage.setScene(Login.getLoginScene(primaryStage));
    }
}
