package model;

public class OrderBookEntry {
    private final String bookTitle;
    private final int quantity;

    public OrderBookEntry(String bookTitle, int quantity) {
        this.bookTitle = bookTitle;
        this.quantity = quantity;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }
}
