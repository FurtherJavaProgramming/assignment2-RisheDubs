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
	    String insertOrderQuery = "INSERT INTO orders (order_number, order_date, total_price, username) VALUES (?, ?, ?, ?)";
	    String insertOrderItemsQuery = "INSERT INTO order_items (order_number, book_title, quantity) VALUES (?, ?, ?)";
	    String updateStockQuery = "UPDATE books SET physical_copies = physical_copies - ? WHERE title = ?";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery)) {

	        orderStmt.setString(1, order.getOrderNumber());  // Ensure this is bound as a string
	        orderStmt.setTimestamp(2, Timestamp.valueOf(order.getDateTime()));
	        orderStmt.setDouble(3, order.getTotalPrice());
	        orderStmt.setString(4, order.getUsername());  // Store the username
	        orderStmt.executeUpdate();

	        try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemsQuery)) {
	            for (Map.Entry<Book, Integer> entry : order.getBooksPurchased().entrySet()) {
	                itemStmt.setString(1, order.getOrderNumber());  // Use generated order number
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
