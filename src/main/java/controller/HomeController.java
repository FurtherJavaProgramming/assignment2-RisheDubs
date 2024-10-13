package controller;

import model.Book;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

    @FXML
    private MenuItem updateProfile;
    
    @FXML
    private Button shoppingCartButton;

    @FXML
    private Button orderBooksButton;  // New button for ordering books

    // No-argument constructor for FXMLLoader
    public HomeController() {}

    @FXML
    public void initialize() {
        updateProfile.setOnAction(event -> openEditProfile());
        shoppingCartButton.setOnAction(event -> openShoppingCart());
        orderBooksButton.setOnAction(event -> openOrderBooksPage());  // Handle the new button's action
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
    
    // Edit profile 
    private void openEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfileView.fxml"));
            BorderPane editProfileRoot = loader.load();  // Change VBox to BorderPane or your root layout type

            EditProfileController editProfileController = loader.getController();
            editProfileController.setModel(model);

            Scene editProfileScene = new Scene(editProfileRoot);
            Stage editProfileStage = new Stage();
            editProfileController.setStage(editProfileStage);

            editProfileStage.setScene(editProfileScene);
            editProfileStage.setTitle("Edit Profile");
            editProfileStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to open the shopping cart view
    private void openShoppingCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShoppingCartView.fxml"));
            VBox shoppingCartRoot = loader.load();

            ShoppingCartController shoppingCartController = loader.getController();
            shoppingCartController.setModel(model);  // Pass the model to the cart

            Scene shoppingCartScene = new Scene(shoppingCartRoot);
            Stage shoppingCartStage = new Stage();
            shoppingCartController.setStage(shoppingCartStage);

            shoppingCartStage.setScene(shoppingCartScene);
            shoppingCartStage.setTitle("Shopping Cart");
            shoppingCartStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to open the order books view
    private void openOrderBooksPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderBooksView.fxml"));  // Ensure you have this view
            VBox orderBooksRoot = loader.load();

            OrderBooksController orderBooksController = loader.getController();
            orderBooksController.setModel(model);  // Pass the model to the order books controller

            Scene orderBooksScene = new Scene(orderBooksRoot);
            Stage orderBooksStage = new Stage();
            orderBooksController.setStage(orderBooksStage);

            orderBooksStage.setScene(orderBooksScene);
            orderBooksStage.setTitle("Order Books");
            orderBooksStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
