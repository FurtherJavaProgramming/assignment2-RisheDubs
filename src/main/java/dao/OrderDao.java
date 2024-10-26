package dao;

import model.Book;
import model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OrderDao {

	public List<Order> getUserOrders(String username) throws SQLException {
	    String orderQuery = "SELECT o.order_number, o.order_date, o.total_price, oi.book_title, oi.quantity " +
	                        "FROM orders o " +
	                        "JOIN order_items oi ON o.order_number = oi.order_number " +
	                        "WHERE o.username = ?";

	    List<Order> orders = new ArrayList<>();

	    try (Connection conn = Database.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(orderQuery)) {

	        stmt.setString(1, username);  // Set the username parameter
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String orderNumber = rs.getString("order_number");
	                LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
	                double totalPrice = rs.getDouble("total_price");
	                String bookTitle = rs.getString("book_title");
	                int quantity = rs.getInt("quantity");

	                Order order = new Order(orderNumber, orderDate, totalPrice, bookTitle, quantity);
	                orders.add(order);
	            }
	        }
	    }

	    return orders;
	}



	public void insertOrder(Order order) throws SQLException {
	    String insertOrderQuery = "INSERT INTO orders (order_date, total_price, username) VALUES (?, ?, ?)";
	    String insertOrderItemsQuery = "INSERT INTO order_items (order_number, book_title, quantity) VALUES (?, ?, ?)";
	    String updateStockQuery = "UPDATE books SET physical_copies = physical_copies - ? WHERE title = ?";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {

	        orderStmt.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
	        orderStmt.setDouble(2, order.getTotalPrice());
	        orderStmt.setString(3, order.getUsername());  // Store the username
	        orderStmt.executeUpdate();

	        ResultSet rs = orderStmt.getGeneratedKeys();
	        if (rs.next()) {
	            int orderNumber = rs.getInt(1);

	            try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemsQuery)) {
	                for (Map.Entry<Book, Integer> entry : order.getBooksPurchased().entrySet()) {
	                    itemStmt.setInt(1, orderNumber);
	                    itemStmt.setString(2, entry.getKey().getTitle());
	                    itemStmt.setInt(3, entry.getValue());
	                    itemStmt.addBatch();
	                }
	                itemStmt.executeBatch();
	            }

	            try (PreparedStatement stockStmt = conn.prepareStatement(updateStockQuery)) {
	                for (Map.Entry<Book, Integer> entry : order.getBooksPurchased().entrySet()) {
	                    stockStmt.setInt(1, entry.getValue());
	                    stockStmt.setString(2, entry.getKey().getTitle());
	                    stockStmt.addBatch();
	                }
	                stockStmt.executeBatch();
	            }
	        }
	    }
	}


	// Generate random order number
	private String generateOrderNumber() {
	    Random random = new Random();
	    return "ORD" + (random.nextInt(900000) + 100000);  // Generates random 6-digit order number
	}




}
