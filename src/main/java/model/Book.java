package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Book {

    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleIntegerProperty physicalCopies;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty soldCopies;
    private SimpleIntegerProperty quantity;  // For the shopping cart

    public Book(String title, String author, int physicalCopies, double price, int soldCopies) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.physicalCopies = new SimpleIntegerProperty(physicalCopies);
        this.price = new SimpleDoubleProperty(price);
        this.soldCopies = new SimpleIntegerProperty(soldCopies);
        this.quantity = new SimpleIntegerProperty(1);  // Default quantity for shopping cart
    }

    // Getters and JavaFX properties for TableView binding
    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public int getPhysicalCopies() {
        return physicalCopies.get();
    }

    public SimpleIntegerProperty physicalCopiesProperty() {
        return physicalCopies;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public int getSoldCopies() {
        return soldCopies.get();
    }

    public SimpleIntegerProperty soldCopiesProperty() {
        return soldCopies;
    }

    // Getter and property for quantity
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
}
