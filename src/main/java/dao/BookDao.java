package dao;

import java.sql.SQLException;
import java.util.List;
import model.Book;

public interface BookDao {
    // Setup method for creating the books table, if necessary
    void setup() throws SQLException;

    // Method to get the top 5 books by sold copies
    List<Book> getTop5Books() throws SQLException;
    
    // Method signature for checking stock availability
    boolean isStockAvailable(Book book, int quantity) throws SQLException;
    
    // Method to update the stock of a book
    void updateStock(Book book, int quantity) throws SQLException;
    
    // Method to get a book by its title
    Book getBookByTitle(String title) throws SQLException;
    
    List<Book> getAllBooks() throws SQLException;
}


