package dao;

import model.Book;
import model.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao {

	public List<Order> getAllOrders() throws SQLException {
	    String orderQuery = "SELECT * FROM orders";
	    String orderItemsQuery = "SELECT book_title, quantity FROM order_items WHERE order_number = ?";

	    List<Order> orders = new ArrayList<>();

	    try (Connection conn = Database.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(orderQuery)) {

	        while (rs.next()) {
	            int orderNumber = rs.getInt("order_number");
	            LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
	            double totalPrice = rs.getDouble("total_price");

	            Order order = new Order(orderNumber, orderDate, totalPrice);

	            try (PreparedStatement pstmt = conn.prepareStatement(orderItemsQuery)) {
	                pstmt.setInt(1, orderNumber);
	                try (ResultSet bookRs = pstmt.executeQuery()) {
	                    while (bookRs.next()) {
	                        String bookTitle = bookRs.getString("book_title");
	                        int quantity = bookRs.getInt("quantity");

	                        Book book = new Book(bookTitle);  // Use book title constructor
	                        order.addBook(book, quantity);  // Add books to the order
	                    }
	                }
	            }

	            orders.add(order);
	        }
	    }
	    return orders;
	}

	public void insertOrder(Order order) throws SQLException {
	    String insertOrderQuery = "INSERT INTO orders (order_date, total_price) VALUES (?, ?)";
	    String insertOrderItemsQuery = "INSERT INTO order_items (order_number, book_title, quantity) VALUES (?, ?, ?)";
	    String updateStockQuery = "UPDATE books SET physical_copies = physical_copies - ? WHERE title = ?";

	    try (Connection conn = Database.getConnection();
	         PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {

	        orderStmt.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
	        orderStmt.setDouble(2, order.getTotalPrice());
	        orderStmt.executeUpdate();

	        ResultSet rs = orderStmt.getGeneratedKeys();
	        if (rs.next()) {
	            int orderNumber = rs.getInt(1);

	            try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemsQuery);
	                 PreparedStatement stockStmt = conn.prepareStatement(updateStockQuery)) {

	                for (Map.Entry<Book, Integer> entry : order.getBooksPurchased().entrySet()) {
	                    itemStmt.setInt(1, orderNumber);
	                    itemStmt.setString(2, entry.getKey().getTitle());
	                    itemStmt.setInt(3, entry.getValue());
	                    itemStmt.addBatch();

	                    stockStmt.setInt(1, entry.getValue());
	                    stockStmt.setString(2, entry.getKey().getTitle());
	                    stockStmt.addBatch();
	                }

	                itemStmt.executeBatch();
	                stockStmt.executeBatch();
	            }
	        }
	    }
	}



}
