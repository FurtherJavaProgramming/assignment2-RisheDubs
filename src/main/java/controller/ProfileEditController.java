package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.User;

public class ProfileEditController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    private Stage primaryStage;
    private User user;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        passwordField.setText(user.getPassword());
    }

    @FXML
    private void saveProfileChanges() {
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setPassword(passwordField.getText());
        System.out.println("Profile updated: " + user.getFirstName() + " " + user.getLastName());
    }

    @FXML
    private void returnToDashboard() {
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
