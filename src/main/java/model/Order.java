package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderNumber;
    private LocalDateTime orderDate;
    private double totalPrice;
    private Map<Book, Integer> booksPurchased;  // Book and Quantity mapping

    // Constructor for new orders without order number (e.g., when placing a new order)
    public Order(LocalDateTime orderDate, double totalPrice) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.booksPurchased = new HashMap<>();
    }

    // Constructor for existing orders fetched from the database
    public Order(int orderNumber, LocalDateTime orderDate, double totalPrice) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.booksPurchased = new HashMap<>();
    }

    // Getters for use with PropertyValueFactory
    public int getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getDateTime() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Map<Book, Integer> getBooksPurchased() {
        return booksPurchased;
    }

    // Add book to the order with a specified quantity
    public void addBook(Book book, int quantity) {
        booksPurchased.put(book, quantity);
    }
}
