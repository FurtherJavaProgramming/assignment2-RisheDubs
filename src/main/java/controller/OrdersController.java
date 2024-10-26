package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;
import model.Model;
import model.Order;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class OrdersController {

    private Model model;
    private Stage stage;

    @FXML
    private Pane ordersPane;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, String> dateTimeColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableColumn<Book, Integer> bookQuantityColumn;

    public void setModel(Model model) throws SQLException {
        this.model = model;
        loadOrders();  // Load orders when the model is set
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void loadOrders() throws SQLException {
        List<Order> orders = model.getOrders();  // Fetch orders from the database

        if (orders != null && !orders.isEmpty()) {
            Collections.reverse(orders);  // Show recent orders first

            orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
            dateTimeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

            ordersTable.getItems().clear();
            ordersTable.getItems().addAll(orders);

            ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldOrder, newOrder) -> {
                if (newOrder != null) {
                    populateBooks(newOrder);  // Populate books for the selected order
                }
            });
        } else {
            System.out.println("No orders found.");
        }
    }

    @FXML
    private void populateBooks(Order order) {
        ObservableList<Book> books = FXCollections.observableArrayList(order.getBooksPurchased().keySet());

        bookTitleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTitle()));

        bookQuantityColumn.setCellValueFactory(cellData -> {
        	Book book = cellData.getValue();
            int quantity = order.getBooksPurchased().getOrDefault(book, 0);
            return new SimpleIntegerProperty(quantity).asObject();  // Wrap in SimpleIntegerProperty
        });

        booksTable.getItems().clear();
        booksTable.getItems().addAll(books);
    }

    @FXML
    private void closeOrdersView() {
        stage.close();  // Close the orders view
    }
}
