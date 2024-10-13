package controller;

import model.Book;
import model.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderBooksController {

    private Model model;
    private Stage stage;

    @FXML
    private ListView<String> availableBooksListView;

    @FXML
    private TextField quantityField;

    @FXML
    private Label statusLabel;

    public void setModel(Model model) {
        this.model = model;
        loadAllBooks();  // Load all available books on page load
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadAllBooks() {
        try {
            List<Book> availableBooks = model.getBookDao().getAllBooks();
            availableBooksListView.getItems().clear();
            for (Book book : availableBooks) {
                availableBooksListView.getItems().add(book.getTitle() + " - Available: " + book.getPhysicalCopies());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading books.");
        }
    }

    // Handle adding the selected book to the shopping cart
    @FXML
    private void handleAddToCart() {
        String selectedBookInfo = availableBooksListView.getSelectionModel().getSelectedItem();
        if (selectedBookInfo == null || quantityField.getText().isEmpty()) {
            statusLabel.setText("Please select a book and specify a quantity.");
            return;
        }

        try {
            String selectedBookTitle = selectedBookInfo.split(" - ")[0];  // Extract book title
            Book selectedBook = model.getBookDao().getBookByTitle(selectedBookTitle);
            int quantity = Integer.parseInt(quantityField.getText());

            if (selectedBook.getPhysicalCopies() >= quantity) {
                model.getShoppingCart().addBook(selectedBook, quantity);
                statusLabel.setText("Book added to cart!");
            } else {
                statusLabel.setText("Not enough stock available.");
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            statusLabel.setText("Error adding book to cart.");
        }
    }

    // Navigate to the shopping cart page
    @FXML
    private void goToCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShoppingCartView.fxml"));
            VBox cartRoot = loader.load();

            ShoppingCartController cartController = loader.getController();
            cartController.setModel(model);

            Scene cartScene = new Scene(cartRoot);
            Stage cartStage = new Stage();
            cartController.setStage(cartStage);

            cartStage.setScene(cartScene);
            cartStage.setTitle("Shopping Cart");
            cartStage.show();

            // Close the current window
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
