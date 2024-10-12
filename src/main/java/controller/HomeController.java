package controller;

import model.Book;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController {

    private Model model;
    private Stage stage;

    @FXML
    private Label welcomeLabel;  // To display the personalized message

    @FXML
    private ListView<String> booksListView;  // ListView to display top 5 books

    // No-argument constructor for FXMLLoader
    public HomeController() {
    }

    @FXML
    public void initialize() {
        // Initialization logic without accessing model (for example, setting up listeners, etc.)
    }

    // Set the personalized welcome message
    public void showWelcomeMessage() {
        if (model != null) {
            User currentUser = model.getCurrentUser();
            if (currentUser != null) {
                welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
            }
        }
    }

    // Load top 5 books based on sold copies
    private void loadTop5Books() throws SQLException {
        if (model != null) {
            List<Book> topBooks = model.getBookDao().getTop5Books();

            for (Book book : topBooks) {
                booksListView.getItems().add(book.getTitle() + " - Sold: " + book.getSoldCopies());
            }
        }
    }

    // Setter for model
    public void setModel(Model model) {
        this.model = model;
        showWelcomeMessage();  // Refresh welcome message after model is set

        try {
            loadTop5Books();  // Load top books once the model is set
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
