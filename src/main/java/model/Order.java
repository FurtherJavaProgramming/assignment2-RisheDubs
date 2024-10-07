package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class Order {

    private SimpleStringProperty orderNumber;
    private SimpleDoubleProperty totalPrice;

    public Order(String orderNumber, double totalPrice) {
        this.orderNumber = new SimpleStringProperty(orderNumber);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    // Getter and property for orderNumber
    public String getOrderNumber() {
        return orderNumber.get();
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber.set(orderNumber);
    }

    public StringProperty orderNumberProperty() {
        return orderNumber;
    }

    // Getter and property for totalPrice
    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
