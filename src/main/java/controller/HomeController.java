package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Model;
import model.User;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    private Model model;

    public void setModel(Model model) {
        this.model = model;
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        User currentUser = model.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + "!");
        }
    }
}
