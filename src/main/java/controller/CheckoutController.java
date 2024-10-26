package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order;
import model.ShoppingCart;

import model.Book;
import model.Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Random;

public class CheckoutController {

    private ShoppingCart cart;
    private Model model;
    private Stage stage;  // Add a field to store the stage

    @FXML
    private Text totalPriceLabel;  // Changed to Text for the new FXML design

    @FXML
    private TextField cardNumberField;  // For the card number input

    @FXML
    private TextField expiryDateField;  // For the expiry date input

    @FXML
    private TextField cvvField;  // For the CVV input

    @FXML
    private Label statusLabel;  // Label to show status messages (error/success)

    // Set the shopping cart for this controller and update the total price
    public void setCart(ShoppingCart cart) {
        this.cart = cart;
        updateTotalPrice();  // Display the total price on the page
    }
    
    // Setter for Model
    public void setModel(Model model) {
        this.model = model;  // Store the model instance
    }

    
    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to update the total price on the view
    private void updateTotalPrice() {
        double totalCost = cart.getTotalCost();
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalCost));  // Update the price using Text
    }

    // Method to handle the payment confirmation
    @FXML
    private void handleConfirmPayment() {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        if (!validateCardNumber(cardNumber)) {
            statusLabel.setText("Invalid card number. It must be 16 digits.");
        } else if (!validateExpiryDate(expiryDate)) {
            statusLabel.setText("Invalid expiry date. Must be a future date.");
        } else if (!validateCVV(cvv)) {
            statusLabel.setText("Invalid CVV. It must be 3 digits.");
        } else {
            processPayment();  // Process the payment if all validations pass
        }
    }

    // Method to validate card number (must be 16 digits)
    private boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    // Method to validate the expiry date (must be a future date)
    private boolean validateExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            LocalDate date = LocalDate.parse("01/" + expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Method to validate the CVV (must be 3 digits)
    private boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    // Method to process the payment and generate a random order number
    private void processPayment() {
        Order newOrder = new Order(LocalDateTime.now(), cart.getTotalCost());

        // Add books from the cart to the new order
        cart.getCartItems().forEach(newOrder::addBook);

        try {
            model.getOrderDao().insertOrder(newOrder);  // Store order in the database
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Payment successful! Order placed.");

            cart.clearCart();  // Clear the cart after successful payment
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Payment failed. Try again.");
        }
    }

    // Method to generate a random order number
    private String generateOrderNumber() {
        Random random = new Random();
        return "ORD" + (random.nextInt(900000) + 100000);  // Generates a random 6-digit order number
    }
}
