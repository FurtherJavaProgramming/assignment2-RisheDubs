package model;

import dao.UserDao;
import dao.OrderDao;
import dao.UserDaoImpl;
import dao.BookDao;
import dao.BookDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {
	private OrderDao orderDao;
    private UserDao userDao;
    private BookDao bookDao;
    private User currentUser;
    private ShoppingCart shoppingCart;  // Add shopping cart field

    public Model() {
    	
        // Initialize the DAOs
    	orderDao = new OrderDao(); 
        userDao = new UserDaoImpl();
        bookDao = new BookDaoImpl();  // Initialize the BookDao here
        shoppingCart = new ShoppingCart();  // Initialize the shopping cart
        orders = new ArrayList<>();
    }

    // Setup method (if needed for database initialization)
    public void setup() throws SQLException {
        userDao.setup();
        bookDao.setup();  // Ensure the BookDao is also set up
    }

    // Getter for UserDao
    public UserDao getUserDao() {
        return userDao;
    }
    
    public OrderDao getOrderDao() {
        return orderDao;
    }

    // Getter for BookDao
    public BookDao getBookDao() {
        return bookDao;
    }

    // Getter and setter for the currently logged-in user
    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Getter for ShoppingCart
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    
    private List<Order> orders;  // Assuming you store orders in a List

    public List<Order> getOrders() throws SQLException {
        return orderDao.getAllOrders();
    }
    
    public void addOrder(Order order) {
        orders.add(order);  // Add a new order to the list
    }

}
