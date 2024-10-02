import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookListScreen {

    private Stage primaryStage;
    private User user;

    // Constructor to accept the current user and primary stage
    public BookListScreen(Stage stage, User user) {
        this.primaryStage = stage;
        this.user = user;
    }

    public void display() {
        // Create a TableView for displaying books
        TableView<Book> table = new TableView<>();

        // Create columns for Book Title, Author, Physical Copies, Price, Sold Copies
        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Authors");
        authorColumn.setMinWidth(150);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, Integer> physicalCopiesColumn = new TableColumn<>("# Physical Copies");
        physicalCopiesColumn.setMinWidth(150);
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price (AUD)");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, Integer> soldCopiesColumn = new TableColumn<>("# Sold Copies");
        soldCopiesColumn.setMinWidth(150);
        soldCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("soldCopies"));

        // Add columns to the table
        table.getColumns().addAll(titleColumn, authorColumn, physicalCopiesColumn, priceColumn, soldCopiesColumn);

        // Set the table's data (using the book data from the images)
        table.setItems(getBookList());

        // Back button to return to the user dashboard
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> returnToDashboard());

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(table, backButton);

        // Scene setup
        Scene bookListScene = new Scene(layout, 800, 400);  // Adjusted size to fit all columns
        primaryStage.setScene(bookListScene);
        primaryStage.setTitle("Book List");
        primaryStage.show();
    }

    // Book data based on the table in the images
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

    private void returnToDashboard() {
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
