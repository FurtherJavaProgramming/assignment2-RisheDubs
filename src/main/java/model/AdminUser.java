package model;

import service.user.AdminActions;
import service.order.OrderProcessing;

public class AdminUser extends User implements AdminActions, OrderProcessing {

    public AdminUser(String username, String password) {
        super(username, password);
    }

    @Override
    public void addBook() {
        System.out.println("Admin is adding a book.");
    }

    @Override
    public void processOrder() {
        System.out.println("Admin is processing an order.");
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
