package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ShoppingCart;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class CheckoutController {

    private ShoppingCart cart;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvvField;

    @FXML
    private Label statusLabel;

	private Stage stage;

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
        updateTotalPrice();  // Display the total price
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    private void updateTotalPrice() {
        double totalCost = cart.getTotalCost();
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalCost));
    }

    @FXML
    private void handleConfirmPayment() {
        String cardNumber = cardNumberField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();

        // Validate the credit card information
        if (!validateCardNumber(cardNumber)) {
            statusLabel.setText("Invalid card number. It must be 16 digits.");
        } else if (!validateExpiryDate(expiryDate)) {
            statusLabel.setText("Invalid expiry date. Must be a future date.");
        } else if (!validateCVV(cvv)) {
            statusLabel.setText("Invalid CVV. It must be 3 digits.");
        } else {
            processPayment();
        }
    }

    private boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    private boolean validateExpiryDate(String expiryDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            LocalDate date = LocalDate.parse("01/" + expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    private void processPayment() {
        String orderNumber = generateOrderNumber();
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Payment successful! Order number: " + orderNumber);

        cart.clearCart();  // Clear the cart after successful payment
    }

    private String generateOrderNumber() {
        Random random = new Random();
        return "ORD" + (random.nextInt(900000) + 100000);  // Generates a random 6-digit order number
    }
}
