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
        Button editProfileButton = new Button("Edit Profile");
        Button logoutButton = new Button("Logout");

        // Layout
        VBox layout = new VBox(10);  // 10px spacing between elements
        layout.getChildren().addAll(welcomeLabel, viewBooksButton, shoppingCartButton, viewOrdersButton, editProfileButton, logoutButton);

        // Scene setup
        Scene userDashboardScene = new Scene(layout, 400, 300);
        primaryStage.setScene(userDashboardScene);
        primaryStage.setTitle("User Dashboard");
        primaryStage.show();

        // Set up actions for buttons (View Books, Shopping Cart, Edit Profile, etc.)
        viewBooksButton.setOnAction(e -> {
            // Navigate to the Book List Screen
            BookListScreen bookListScreen = new BookListScreen(primaryStage, user);
            bookListScreen.display();
        });

        shoppingCartButton.setOnAction(e -> manageShoppingCart());
        viewOrdersButton.setOnAction(e -> viewOrderHistory());
        editProfileButton.setOnAction(e -> editProfile());
        logoutButton.setOnAction(e -> logout());
    }

    // Placeholder method to simulate managing the shopping cart
    private void manageShoppingCart() {
        System.out.println("Opening shopping cart...");
        // Implement the shopping cart functionality in the future
    }

    // Placeholder method to simulate viewing order history
    private void viewOrderHistory() {
        System.out.println("Viewing order history...");
        // Implement the order history functionality in the future
    }

    // Placeholder method to simulate editing the profile
    private void editProfile() {
        System.out.println("Opening profile edit screen...");
        ProfileEditScreen profileEditScreen = new ProfileEditScreen(primaryStage, user);
        profileEditScreen.display();
    }

    // Logout method to return to the login screen
    private void logout() {
        primaryStage.setScene(Login.getLoginScene(primaryStage));
    }
}
