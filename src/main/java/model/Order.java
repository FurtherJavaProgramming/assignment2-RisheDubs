package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Order {
    private String orderNumber;  // Keep order number as String for flexibility
    private String username;
    private LocalDateTime orderDate;
    private double totalPrice;
    private String bookTitle;
    private int quantity;
    private Map<Book, Integer> booksPurchased = new HashMap<>();  // Initialize the map directly

    // Constructor for new orders with username
    public Order(String username, LocalDateTime orderDate, double totalPrice) {
        this.username = username;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderNumber = generateOrderNumber();  // Generate random order number
    }

    // Constructor for existing orders fetched from the database
    public Order(String orderNumber, LocalDateTime orderDate, double totalPrice, String bookTitle, int quantity) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
    }

    // Generate a random 6-digit order number
    private String generateOrderNumber() {
        int randomOrderNumber = new Random().nextInt(900000) + 100000;  // Range: 100000-999999
        return String.valueOf(randomOrderNumber);
    }

    // Getters and Setters
    public String getOrderNumber() {
        return orderNumber;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateTime() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public Map<Book, Integer> getBooksPurchased() {
        return booksPurchased;
    }

    public void addBook(Book book, int quantity) {
        booksPurchased.put(book, quantity);
    }
}
