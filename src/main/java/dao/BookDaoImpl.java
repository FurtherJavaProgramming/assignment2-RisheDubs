package dao;

import model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final String TABLE_NAME = "books";

    @Override
    public void setup() throws SQLException {
        // Create the books table if it doesn't exist
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "title TEXT, "
                + "author TEXT, "
                + "physical_copies INTEGER, "
                + "price REAL, "
                + "sold_copies INTEGER)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Book> getTop5Books() throws SQLException {
        String sql = "SELECT * FROM books ORDER BY sold_copies DESC LIMIT 5";  // Make sure the query is correct
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("physical_copies"),
                    rs.getDouble("price"),
                    rs.getInt("sold_copies")
                );
                books.add(book);
            }
            return books;
        }
    }
    
    @Override
    public boolean isStockAvailable(Book book, int quantity) throws SQLException {
        String sql = "SELECT physical_copies FROM books WHERE title = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int availableCopies = rs.getInt("physical_copies");
                    return availableCopies >= quantity;
                }
            }
        }
        return false;  // Return false if no copies are available
    }
    
    public void updateStock(Book book, int quantitySold) throws SQLException {
        String sql = "UPDATE books SET physical_copies = physical_copies - ? WHERE title = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantitySold);
            stmt.setString(2, book.getTitle());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public Book getBookByTitle(String title) throws SQLException {
        String query = "SELECT * FROM books WHERE title = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Assuming your Book constructor has parameters like this:
                return new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("physical_copies"),
                    rs.getDouble("price"),
                    rs.getInt("sold_copies")
                );
            }
        }
        return null;  // Return null if no book found
    }
    
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPhysicalCopies(rs.getInt("physical_copies"));
                book.setPrice(rs.getDouble("price"));
                book.setSoldCopies(rs.getInt("sold_copies"));
                books.add(book);
            }
        }
        
        return books;
    }


}
