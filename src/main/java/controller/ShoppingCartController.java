package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.User;
import view.UserDashboard;  // Correct import

public class ShoppingCartController {

    @FXML
    private TableView<Book> cartTable;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, Integer> quantityColumn;

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
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Populate the table with cart items
        cartTable.setItems(getCartItems());
    }

    private ObservableList<Book> getCartItems() {
        // Add some dummy data for testing the cart
        ObservableList<Book> cartItems = FXCollections.observableArrayList();
        cartItems.add(new Book("Clean Code", "Robert Martin", 100, 45.0, 1));  // Quantity 1
        cartItems.add(new Book("Effective Java", "Joshua Bloch", 100, 45.0, 2));  // Quantity 2
        return cartItems;
    }

    @FXML
    private void returnToDashboard() {
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
