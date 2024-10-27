package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import model.Model;

public class AdminController {

    @FXML
    private Label welcomeLabel;  // Label to display the welcome message

    private Model model;
    private Stage stage;

    public void setModel(Model model) {
        this.model = model;
        displayWelcomeMessage();  // Display the message when model is set
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void displayWelcomeMessage() {
        User currentUser = model.getCurrentUser();  // Get the logged-in user
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        }
    }

    @FXML
    private void viewOrders() {
        // Code to open the orders view
    }

    @FXML
    private void logout() {
        stage.close();  // Close the admin dashboard
    }
}
