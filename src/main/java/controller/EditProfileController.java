package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class EditProfileController {

    private Model model;
    private Stage stage;

    @FXML
    private TextField username;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField password;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    public void setModel(Model model) {
        this.model = model;
        User currentUser = model.getCurrentUser();

        // Initialize the form with current user details
        username.setText(currentUser.getUsername());
        firstName.setText(currentUser.getFirstName());
        lastName.setText(currentUser.getLastName());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Handle save button click
        saveButton.setOnAction(event -> handleSave());

        // Handle cancel button click
        cancelButton.setOnAction(event -> stage.close());
    }

    private void handleSave() {
        String updatedFirstName = firstName.getText();
        String updatedLastName = lastName.getText();
        String updatedPassword = password.getText();

        // Ensure all fields are filled
        if (!updatedFirstName.isEmpty() && !updatedLastName.isEmpty() && !updatedPassword.isEmpty()) {
            try {
                // Call updateUserProfile and check if the update is successful
                boolean success = model.getUserDao().updateUserProfile(
                    username.getText(), updatedFirstName, updatedLastName, updatedPassword);

                if (success) {
                    // Update the current user object in the model
                    User currentUser = model.getCurrentUser();
                    currentUser.setFirstName(updatedFirstName);
                    currentUser.setLastName(updatedLastName);
                    currentUser.setPassword(updatedPassword);

                    // Show success message
                    showAlert(AlertType.INFORMATION, "Success", "Profile updated successfully.");

                    // Close the window after successful update
                    stage.close();

                } else {
                    // Show error message if update fails
                    showAlert(AlertType.ERROR, "Error", "Failed to update profile.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Show error alert in case of an exception
                showAlert(AlertType.ERROR, "Error", "An error occurred while updating the profile.");
            }
        } else {
            // Show error message if fields are missing
            showAlert(AlertType.WARNING, "Input Error", "All fields must be filled.");
        }
    }

    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
