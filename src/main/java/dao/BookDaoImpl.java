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
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY sold_copies DESC LIMIT 5";
        List<Book> topBooks = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("physical_copies"),
                        rs.getDouble("price"),
                        rs.getInt("sold_copies")
                );
                topBooks.add(book);
            }
        }
        return topBooks;
    }
}
