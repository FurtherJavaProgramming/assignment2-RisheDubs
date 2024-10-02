import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class ProfileEditScreen {

    private Stage primaryStage;
    private User user;

    // Constructor to accept the current user and primary stage
    public ProfileEditScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        // Create labels and text fields for profile details
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField(user.getUsername());  // Placeholder for first name

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField(user.getUsername());  // Placeholder for last name

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(user.getPassword());  // Placeholder, will use real data later

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveProfileChanges(firstNameField.getText(), lastNameField.getText(), passwordField.getText()));

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> returnToDashboard());

        // Layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(firstNameLabel, 0, 0);
        layout.add(firstNameField, 1, 0);
        layout.add(lastNameLabel, 0, 1);
        layout.add(lastNameField, 1, 1);
        layout.add(passwordLabel, 0, 2);
        layout.add(passwordField, 1, 2);
        layout.add(saveButton, 1, 3);
        layout.add(backButton, 1, 4);

        // Scene setup
        Scene profileEditScene = new Scene(layout, 400, 300);
        primaryStage.setScene(profileEditScene);
        primaryStage.setTitle("Edit Profile");
        primaryStage.show();
    }

    private void saveProfileChanges(String firstName, String lastName, String newPassword) {
        // Placeholder: Save changes to the user's profile
        System.out.println("Profile updated: " + firstName + " " + lastName);
    }

    private void returnToDashboard() {
        // Return to the user dashboard
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
