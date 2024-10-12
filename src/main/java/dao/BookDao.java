package dao;

import java.sql.SQLException;
import java.util.List;
import model.Book;

public interface BookDao {
    // Setup method for creating the books table, if necessary
    void setup() throws SQLException;

    // Method to get the top 5 books by sold copies
    List<Book> getTop5Books() throws SQLException;
}
