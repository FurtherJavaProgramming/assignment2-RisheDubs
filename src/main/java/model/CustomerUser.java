package model;

import service.user.CustomerActions;
import service.cart.CartActions;
import service.order.OrderViewing;

public class CustomerUser extends User implements CustomerActions, CartActions, OrderViewing {

    public CustomerUser(String username, String password) {
        super(username, password);
    }

    @Override
    public void viewBooks() {
        System.out.println("Customer is viewing books.");
    }

    @Override
    public void addToCart() {
        System.out.println("Customer is adding items to cart.");
    }

    @Override
    public void viewOrders() {
        System.out.println("Customer is viewing their orders.");
    }

    @Override
    public void checkout() {
        System.out.println("Customer is checking out.");
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
