package controller;

import model.Book;
import model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderBooksController {

    private Model model;
    private Stage stage;

    @FXML
    private TableView<Book> availableBooksTableView;  // Update to TableView<Book>

    @FXML
    private TableColumn<Book, String> titleColumn;
    
    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    @FXML
    private TableColumn<Book, Integer> soldCopiesColumn;

    @FXML
    private TextField quantityField;  // Text field to enter the quantity of books

    @FXML
    private Label statusLabel;  // Label to display status messages

    // Setter for Model to access book data and shopping cart
    public void setModel(Model model) {
        this.model = model;
        loadAllBooks();  // Load all available books on page load
    }

    // Setter for Stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to initialize the TableView columns
    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        soldCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("soldCopies"));
    }

    // Method to load all books from the database and display in TableView
    private void loadAllBooks() {
        try {
            List<Book> availableBooks = model.getBookDao().getAllBooks();  // Fetch books from the database
            availableBooksTableView.getItems().clear();
            availableBooksTableView.getItems().addAll(availableBooks);  // Add all books to the TableView
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading books.");
        }
    }

    // Method to handle adding the selected book to the shopping cart
    @FXML
    private void handleAddToCart() {
        Book selectedBook = availableBooksTableView.getSelectionModel().getSelectedItem();  // Get the selected book
        String quantityText = quantityField.getText();  // Get quantity input from the user

        if (selectedBook == null || quantityText.isEmpty()) {
            statusLabel.setText("Please select a book and specify a quantity.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);  // Parse quantity as integer

            // Check if enough stock is available
            if (selectedBook.getPhysicalCopies() >= quantity) {
                model.getShoppingCart().addBook(selectedBook, quantity);  // Add book to the cart
                statusLabel.setText("Book added to cart!");
            } else {
                statusLabel.setText("Not enough stock available.");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid quantity. Please enter a number.");
        }
    }

    // Method to navigate to the shopping cart page
    @FXML
    private void goToCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShoppingCartView.fxml"));  // Load shopping cart view
            Pane cartRoot = loader.load();

            ShoppingCartController cartController = loader.getController();  // Get the controller for ShoppingCartView
            cartController.setModel(model);  // Set the model for the shopping cart

            Scene cartScene = new Scene(cartRoot);  // Create a new scene for the cart
            Stage cartStage = new Stage();  // Create a new stage for the cart view
            cartController.setStage(cartStage);  // Set the stage in ShoppingCartController

            cartStage.setScene(cartScene);  // Set the scene on the stage
            cartStage.setTitle("Shopping Cart");
            cartStage.show();  // Show the cart stage

            // Close the current order books stage
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error opening the cart.");
        }
    }
}
