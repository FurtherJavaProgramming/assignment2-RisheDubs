package model;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private String orderNumber;
    private LocalDateTime dateTime;
    private double totalPrice;
    private Map<Book, Integer> booksPurchased;

    public Order(String orderNumber, LocalDateTime dateTime, double totalPrice, Map<Book, Integer> booksPurchased) {
        this.orderNumber = orderNumber;
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.booksPurchased = booksPurchased;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Map<Book, Integer> getBooksPurchased() {
        return booksPurchased;
    }
}
