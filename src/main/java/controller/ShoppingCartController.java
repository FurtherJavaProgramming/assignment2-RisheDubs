package controller;

import model.Book;
import model.Order;
import model.ShoppingCart;
import model.Model;
import dao.OrderDao;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class ShoppingCartController {

    private ShoppingCart cart;
    private Model model;
    private Stage stage;
    private OrderDao orderDao = new OrderDao();  // Add DAO instance for orders

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
    
    @FXML
    private Button placeOrderButton;  // Button to place the order

    public ShoppingCartController() {
        this.cart = new ShoppingCart();
    }

    public void setModel(Model model) {
        this.model = model;
        this.cart = model.getShoppingCart();  // Ensure the cart from model is used
        initializeTableColumns();  // Initialize table columns
        updateCartView();  // Refresh the cart view when the model is set
        updateTotalCost();  // Calculate and display the total cost
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initializeTableColumns() {
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
        quantityColumn.setCellValueFactory(data -> {
            Book book = data.getValue();
            Integer quantity = cart.getCartItems().get(book);
            return new SimpleStringProperty(String.valueOf(quantity));
        });
        costColumn.setCellValueFactory(data -> {
            Book book = data.getValue();
            Integer quantity = cart.getCartItems().get(book);
            Double cost = book.getPrice() * quantity;
            return new SimpleStringProperty(String.format("$%.2f", cost));
        });
    }

    private void updateCartView() {
        cartTableView.getItems().clear();
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Cart is empty.");
            return;
        }
        cartTableView.getItems().addAll(cart.getCartItems().keySet());
    }

    private void updateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost));
    }

    @FXML
    private void handleRemoveBook() {
        Book selectedBook = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            statusLabel.setText("Please select a book to remove.");
            return;
        }
        cart.removeBook(selectedBook);
        updateCartView();
        updateTotalCost();
        statusLabel.setText("Book removed from cart.");
    }

    @FXML
    private void handleUpdateQuantity() {
        Book selectedBook = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null || quantityField.getText().isEmpty()) {
            statusLabel.setText("Please select a book and specify a new quantity.");
            return;
        }
        try {
            int newQuantity = Integer.parseInt(quantityField.getText());
            if (newQuantity > 0 && selectedBook.getPhysicalCopies() >= newQuantity) {
                cart.updateQuantity(selectedBook, newQuantity);
                updateCartView();
                updateTotalCost();
                statusLabel.setText("Quantity updated.");
            } else {
                statusLabel.setText("Not enough stock available or invalid quantity.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid quantity. Please enter a valid number.");
        }
    }

    // Method to handle order placement (new code)
    @FXML
    private void handlePlaceOrder() {
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Your cart is empty. Please add some books before placing the order.");
            return;
        }

        double totalPrice = cart.getTotalCost();  // Use the getTotalCost method
        Order newOrder = new Order(null, LocalDateTime.now(), totalPrice);  // Create a new order with the correct constructor

        // Add books and their quantities to the new order
        for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
            newOrder.addBook(entry.getKey(), entry.getValue());
        }

        try {
            // Ensure orderDao is properly initialized in the controller
            if (model != null && model.getOrderDao() != null) {
                model.getOrderDao().insertOrder(newOrder);  // Save the order to the database
                statusLabel.setText("Order placed successfully!");

                cart.clearCart();  // Clear the cart after placing the order
                updateCartView();  // Refresh the cart view
                updateTotalCost();  // Update the total cost label
            } else {
                statusLabel.setText("Error: Order DAO is not initialized.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to place order.");
        }
    }

    
    @FXML
    private void handleCheckout() {
        if (cart.getCartItems().isEmpty()) {
            statusLabel.setText("Your cart is empty. Please add some books before checking out.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckoutView.fxml"));
            BorderPane checkoutRoot = loader.load();

            CheckoutController checkoutController = loader.getController();
            checkoutController.setCart(cart);
            checkoutController.setModel(this.model);  // Pass the model to access the user ID
            checkoutController.setStage(stage);

            Scene checkoutScene = new Scene(checkoutRoot);
            Stage checkoutStage = new Stage();
            checkoutStage.setScene(checkoutScene);
            checkoutStage.setTitle("Checkout");
            checkoutStage.show();

            // Close the shopping cart window
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading checkout page.");
        }
    }


}
