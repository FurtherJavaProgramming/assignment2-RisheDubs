import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application {
    private UserManager userManager = new UserManager();  // UserManager instance to manage users

    @Override
    public void start(Stage primaryStage) {
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
            handleLogin(username, password, messageLabel);
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

        // Scene setup
        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - Bookstore App");
        primaryStage.show();
    }

    private void handleLogin(String username, String password, Label messageLabel) {
        // Using UserManager to authenticate the user
        if (userManager.authenticate(username, password)) {
            User user = userManager.getUser(username);
            messageLabel.setText("Login successful: " + user.getRole());
            // Load the appropriate dashboard based on user role (will implement later)
        } else {
            messageLabel.setText("Invalid credentials.");
        }
    }

    private void handleRegistration() {
        // Logic to open a registration screen (to be implemented later)
        System.out.println("Opening registration form...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
