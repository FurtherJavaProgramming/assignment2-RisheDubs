import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the login scene on the primary stage
        primaryStage.setScene(getLoginScene(primaryStage));
        primaryStage.setTitle("Login - Bookstore App");
        primaryStage.show();
    }

    // Method to generate the login scene
    public static Scene getLoginScene(Stage primaryStage) {
        // Create labels and input fields
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        // Create Login button
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();  // Label to show error/success messages
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            handleLogin(username, password, messageLabel, primaryStage);  // Call the handleLogin method on button click
        });

        // Create registration link
        Hyperlink registerLink = new Hyperlink("Create a new account");
        registerLink.setOnAction(e -> handleRegistration());

        // Layout configuration
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Adding elements to the grid
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(messageLabel, 1, 3);
        gridPane.add(registerLink, 1, 4);

        // Return the login scene
        return new Scene(gridPane, 300, 200);
    }

    // The handleLogin method
    private static void handleLogin(String username, String password, Label messageLabel, Stage primaryStage) {
        // Simulating a successful login
        if (username.equals("user") && password.equals("password")) {
            // On successful login, navigate to the User Dashboard
            messageLabel.setText("Login successful! Redirecting...");

            // Create the User object (you can update this later to pull from a database)
            User loggedInUser = new User(username, password, "user");

            // Redirect to the User Dashboard
            UserDashboard userDashboard = new UserDashboard(primaryStage, loggedInUser);
            userDashboard.display();

        } else {
            // Show an error message on failed login
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private static void handleRegistration() {
        System.out.println("Opening registration form...");
    }

    // Main method to launch JavaFX application
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
