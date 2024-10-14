package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Book;
import model.Model;
import model.Order;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class OrdersController {

    private Model model;

    @FXML
    private Pane ordersPane;  // Pane as the root layout

    @FXML
    private TableView<Order> ordersTable;  // TableView to display orders

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, String> dateTimeColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableView<Book> booksTable;  // TableView to display books in each order

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableColumn<Book, Integer> bookQuantityColumn;

    public void setModel(Model model) {
        this.model = model;
        loadOrders();  // Load orders when the model is set
    }

    // Load the orders in reverse chronological order
    private void loadOrders() {
        List<Order> orders = model.getOrders();
        if (orders != null) {
            Collections.reverse(orders);  // Reverse the order list to display most recent orders first

            // Set the cell value factories for the orders table
            orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
            dateTimeColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

            // Populate the orders table
            ordersTable.getItems().setAll(orders);

            // Add a listener to load books when an order is selected
            ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    loadBooksForOrder(newSelection);
                }
            });
        }
    }

    // Load books for the selected order
    private void loadBooksForOrder(Order order) {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Use a lambda to retrieve the correct quantity for each book in the order
        bookQuantityColumn.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();  // Get the current Book
            Integer quantity = order.getBooksPurchased().get(book);  // Get the quantity of the book in this order
            return new SimpleIntegerProperty(quantity).asObject();  // Wrap it in SimpleIntegerProperty for the TableView
        });

        // Clear the books table and add books from the selected order
        booksTable.getItems().clear();
        booksTable.getItems().addAll(order.getBooksPurchased().keySet());  // Add the books from the order
    }
}
