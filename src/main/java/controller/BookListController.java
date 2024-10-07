package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.User;
import view.UserDashboard;

public class BookListController {

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<Book, Double> priceColumn;

    @FXML
    private TableColumn<Book, Integer> soldCopiesColumn;

    private Stage primaryStage;
    private User user;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        // Set up column bindings to Book properties
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        physicalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().physicalCopiesProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        soldCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().soldCopiesProperty().asObject());

        // Populate the table with books
        bookTable.setItems(getBookList());
    }

    // Dummy data for the table
    private ObservableList<Book> getBookList() {
    ObservableList<Book> books = FXCollections.observableArrayList();
    books.add(new Book("Absolute Java", "Savitch", 10, 50.0, 142));
    books.add(new Book("JAVA: How to Program", "Deitel and Deitel", 100, 70.0, 475));
    books.add(new Book("Computing Concepts with JAVA 8 Essentials", "Horstman", 500, 89.0, 60));
    books.add(new Book("Java Software Solutions", "Lewis and Loftus", 500, 99.0, 12));
    books.add(new Book("Java Program Design", "Cohoon and Davidson", 2, 29.0, 86));
    books.add(new Book("Clean Code", "Robert Martin", 100, 45.0, 300));
    books.add(new Book("Gray Hat C#", "Brandon Perry", 300, 68.0, 178));
    books.add(new Book("Python Basics", "David Amos", 1000, 49.0, 79));
    books.add(new Book("Bayesian Statistics The Fun Way", "Will Kurt", 600, 42.0, 155));
    return books;
}

    @FXML
    private void returnToDashboard() {
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
