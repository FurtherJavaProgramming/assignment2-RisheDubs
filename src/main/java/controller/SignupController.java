package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.SQLException;
import model.Model;
import model.User;

public class SignupController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button createUser;

    @FXML
    private Button close;

    @FXML
    private Label status;

    private Stage stage;
    private Model model;

    public SignupController() {
        // No-argument constructor required by FXMLLoader
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        createUser.setOnAction(event -> handleCreateUser());
        close.setOnAction(event -> handleClose());
    }

    private void handleCreateUser() {
        if (username.getText().isEmpty() || password.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
            status.setText("All fields are required.");
            status.setTextFill(Color.RED);
        } else {
            try {
                // Attempt to create the user
                User user = model.getUserDao().createUser(username.getText(), password.getText(), firstName.getText(), lastName.getText());
                status.setText("User created successfully.");
                status.setTextFill(Color.GREEN);

                // close the registration window and open login or dashboard page
                stage.close();

            } catch (SQLException e) {
                
                if (e.getMessage().contains("Username already exists")) {
                    status.setText("Username already taken. Please choose another.");
                } else {
                    status.setText("Database error: " + e.getMessage());
                }
                status.setTextFill(Color.RED);
            }
        }
    }

    private void handleClose() {
        if (stage != null) {
            stage.close();
        }
    }

    public void showStage() {
        stage.show();
    }
}
