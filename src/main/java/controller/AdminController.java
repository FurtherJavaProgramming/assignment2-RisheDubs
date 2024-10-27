package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import model.Model;

import java.sql.SQLException;
import java.util.List;

public class AdminController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorsColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    @FXML
    private TableColumn<Book, Integer> soldCopiesColumn;

    @FXML
    private TableColumn<Book, Integer> stockColumn;

    @FXML
    private TextField stockField;

    private Model model;

    public void setModel(Model model) {
        this.model = model;
        handleViewBooks();  // Automatically load books when the model is set
    }

    @FXML
    private void handleViewBooks() {
        try {
            List<Book> books = model.getBookDao().getAllBooks();  // Fetch all books from the database
            ObservableList<Book> bookList = FXCollections.observableArrayList(books);

            // Set up the columns
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorsColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            soldCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("soldCopies"));
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));

            // Populate the table
            booksTable.setItems(bookList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateStock() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            try {
                int newStock = Integer.parseInt(stockField.getText());
                if (newStock >= 0) {
                    selectedBook.setPhysicalCopies(newStock);  // Update stock in the book object
                    model.getBookDao().updateBookStock(selectedBook);  // Update stock in the database
                    handleViewBooks();  // Refresh the view
                } else {
                    System.out.println("Stock cannot be negative.");
                }
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Invalid stock value or database error.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a book to update.");
        }
    }

	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		
	}
}
