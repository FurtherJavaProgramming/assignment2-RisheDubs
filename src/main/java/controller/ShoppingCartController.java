package controller;

import model.Book;
import model.ShoppingCart;
import model.User;
import model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Map;

public class ShoppingCartController {

    private ShoppingCart cart;
    private Model model;
    private Stage stage;

    @FXML
    private ListView<String> cartListView;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalCostLabel;  // Label to show total cost

    @FXML
    private TextField quantityField;  // Field for updating the quantity of books

    public ShoppingCartController() {
        this.cart = new ShoppingCart();
    }

    // Setter for model, to be called by a parent controller
    public void setModel(Model model) {
        this.model = model;
        this.cart = model.getShoppingCart();  // Ensure the cart from model is used
        updateCartView();  // Refresh the cart view when the model is set
        updateTotalCost();  // Calculate and display the total cost
    }

    // Setter for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to add a book to the cart
    public void addToCart(Book book, int quantity) {
        if (book.getPhysicalCopies() >= quantity) {
            cart.addBook(book, quantity);
            updateCartView();  // Refresh the cart view after adding the book
            updateTotalCost();  // Update the total cost
            statusLabel.setText("Book added to cart.");
        } else {
            statusLabel.setText("Not enough stock available.");
        }
    }

    // Method to update the cart view (ListView) with the current cart contents
    private void updateCartView() {
        cartListView.getItems().clear();
        if (cart.getCartItems().isEmpty()) {
            cartListView.getItems().add("Cart is empty.");
            return;
        }

        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            double cost = book.getPrice() * quantity;  // Calculate cost for each book
            cartListView.getItems().add(book.getTitle() + " - Quantity: " + quantity + " - Cost: $" + String.format("%.2f", cost));
        }
    }

    // Method to calculate and update the total cost of the cart
    private void updateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost));
    }

    // Method to handle removing a book from the cart
    @FXML
    private void handleRemoveBook() {
        String selectedBookInfo = cartListView.getSelectionModel().getSelectedItem();
        if (selectedBookInfo == null) {
            statusLabel.setText("Please select a book to remove.");
            return;
        }

        try {
            String selectedBookTitle = selectedBookInfo.split(" - ")[0];  // Extract book title
            Book selectedBook = getBookByTitleInCart(selectedBookTitle);  // Get the Book object from the cart

            if (selectedBook != null) {
                cart.removeBook(selectedBook);  // Remove the book from the cart
                updateCartView();  // Refresh the cart view
                updateTotalCost();  // Recalculate the total cost
                statusLabel.setText("Book removed from cart.");
            } else {
                statusLabel.setText("Error: Book not found in cart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error removing book from cart.");
        }
    }

    // Method to handle updating the quantity of a book in the cart
    @FXML
    private void handleUpdateQuantity() {
        String selectedBookInfo = cartListView.getSelectionModel().getSelectedItem();
        if (selectedBookInfo == null || quantityField.getText().isEmpty()) {
            statusLabel.setText("Please select a book and specify a new quantity.");
            return;
        }

        try {
            String selectedBookTitle = selectedBookInfo.split(" - ")[0];  // Extract book title
            Book selectedBook = getBookByTitleInCart(selectedBookTitle);  // Get the Book object from the cart
            int newQuantity = Integer.parseInt(quantityField.getText());  // Parse the new quantity

            // Validate if new quantity is within stock limits
            if (newQuantity > 0 && selectedBook != null && selectedBook.getPhysicalCopies() >= newQuantity) {
                cart.updateQuantity(selectedBook, newQuantity);  // Update the book's quantity in the cart
                updateCartView();  // Refresh the cart view
                updateTotalCost();  // Recalculate the total cost
                statusLabel.setText("Quantity updated.");
            } else {
                statusLabel.setText("Not enough stock available or invalid quantity.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid quantity. Please enter a valid number.");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error updating quantity.");
        }
    }

    // Helper method to find a book in the cart by title
    private Book getBookByTitleInCart(String title) {
        for (Book book : cart.getCartItems().keySet()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // Method to handle checkout
    public void handleCheckout() {
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Cart is empty. Add some books first.");
            return;
        }

        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            try {
                // Check if the stock is available for each book in the cart
                if (model.getBookDao().isStockAvailable(entry.getKey(), entry.getValue())) {
                    model.getBookDao().updateStock(entry.getKey(), entry.getValue());
                } else {
                    statusLabel.setText("Some books are no longer available.");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                statusLabel.setText("Error during checkout.");
                return;
            }
        }
        // Clear the cart after a successful checkout
        cart.clearCart();
        updateCartView();
        updateTotalCost();  // Reset total cost after checkout
        statusLabel.setText("Checkout successful.");
    }
}
