package controller;

import model.Book;
import model.ShoppingCart;
import model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ShoppingCartController {

    private ShoppingCart cart;
    private Model model;
    private Stage stage;

    @FXML
    private TableView<Book> cartTableView;  // TableView to display the cart items

    @FXML
    private TableColumn<Book, String> titleColumn;  // Column for book titles

    @FXML
    private TableColumn<Book, String> quantityColumn;  // Column for quantity

    @FXML
    private TableColumn<Book, String> costColumn;  // Column for cost per book

    @FXML
    private Label statusLabel;  // Label to show status messages (error, success)

    @FXML
    private Label totalCostLabel;  // Label to show the total cost of the cart

    @FXML
    private TextField quantityField;  // Field for updating the quantity of books

    public ShoppingCartController() {
        this.cart = new ShoppingCart();
    }

    // Setter for model, to be called by a parent controller
    public void setModel(Model model) {
        this.model = model;
        this.cart = model.getShoppingCart();  // Ensure the cart from model is used
        initializeTableColumns();  // Initialize table columns
        updateCartView();  // Refresh the cart view when the model is set
        updateTotalCost();  // Calculate and display the total cost
    }

    // Setter for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to initialize table columns for the cart view
    private void initializeTableColumns() {
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        // Custom CellValueFactory for quantity
        quantityColumn.setCellValueFactory(data -> {
            Book book = data.getValue();
            Integer quantity = cart.getCartItems().get(book);  // Get quantity from the cart
            return new SimpleStringProperty(String.valueOf(quantity));
        });

        // Custom CellValueFactory for cost
        costColumn.setCellValueFactory(data -> {
            Book book = data.getValue();
            Integer quantity = cart.getCartItems().get(book);
            Double cost = book.getPrice() * quantity;  // Calculate total cost for this book
            return new SimpleStringProperty(String.format("$%.2f", cost));
        });
    }

    // Method to update the cart view (TableView) with the current cart contents
    private void updateCartView() {
        cartTableView.getItems().clear();  // Clear the existing items
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Cart is empty.");
            return;
        }

        for (Book book : cart.getCartItems().keySet()) {
            cartTableView.getItems().add(book);  // Add book to the table
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
        Book selectedBook = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            statusLabel.setText("Please select a book to remove.");
            return;
        }

        try {
            cart.removeBook(selectedBook);  // Remove the book from the cart
            updateCartView();  // Refresh the cart view
            updateTotalCost();  // Recalculate the total cost
            statusLabel.setText("Book removed from cart.");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error removing book from cart.");
        }
    }

    // Method to handle updating the quantity of a book in the cart
    @FXML
    private void handleUpdateQuantity() {
        Book selectedBook = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null || quantityField.getText().isEmpty()) {
            statusLabel.setText("Please select a book and specify a new quantity.");
            return;
        }

        try {
            int newQuantity = Integer.parseInt(quantityField.getText());  // Parse the new quantity

            // Validate if new quantity is within stock limits
            if (newQuantity > 0 && selectedBook.getPhysicalCopies() >= newQuantity) {
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

    // Method to handle checkout
    @FXML
    private void handleCheckout() {
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Cart is empty. Add some books first.");
            return;
        }

        // Show the checkout view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
            VBox checkoutRoot = loader.load();

            // Get the CheckoutController and pass the ShoppingCart
            CheckoutController checkoutController = loader.getController();
            checkoutController.setCart(cart);

            // Create a new stage for the payment window
            Stage checkoutStage = new Stage();
            checkoutController.setStage(checkoutStage);

            Scene checkoutScene = new Scene(checkoutRoot);
            checkoutStage.setScene(checkoutScene);
            checkoutStage.setTitle("Checkout");
            checkoutStage.show();

            // Close the current cart window
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error during checkout.");
        }
    }

    
    @FXML
    private void handleProceedToCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
            VBox checkoutRoot = loader.load();

            CheckoutController checkoutController = loader.getController();
            checkoutController.setCart(cart);  // Pass the cart to the checkout

            Scene checkoutScene = new Scene(checkoutRoot);
            Stage checkoutStage = new Stage();
            checkoutStage.setScene(checkoutScene);
            checkoutStage.setTitle("Checkout");
            checkoutStage.show();

            // Close the current shopping cart stage
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
