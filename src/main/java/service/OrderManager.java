package service;

import model.Order;
import service.discount.DiscountStrategy;

public class OrderManager {
    private DiscountStrategy discountStrategy;

    // Constructor injection for discount strategy
    public OrderManager(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double processOrder(Order order) {
        double totalPrice = order.getTotalPrice();
        return discountStrategy.applyDiscount(totalPrice);
    }
}
