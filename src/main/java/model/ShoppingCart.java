package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Book, Integer> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashMap<>();
    }

    public void addBook(Book book, int quantity) {
        cartItems.put(book, cartItems.getOrDefault(book, 0) + quantity);
    }

    public void removeBook(Book book) {
        cartItems.remove(book);
    }

    public void updateQuantity(Book book, int quantity) {
        if (cartItems.containsKey(book)) {
            cartItems.put(book, quantity);
        }
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
