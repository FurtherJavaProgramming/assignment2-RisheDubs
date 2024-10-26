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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import model.Model;
import model.Order;
import model.OrderBookEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private TableView<OrderBookEntry> booksTable;

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
        // Ensure the current user is not null
        if (model.getCurrentUser() != null) {
            String username = model.getCurrentUser().getUsername();  // Fetch the current user's username
            
            List<Order> orders = model.getOrderDao().getUserOrders(username);  // Fetch orders for the current user

            if (orders != null && !orders.isEmpty()) {
                // Set cell factories for order details, book titles, and quantities
                orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
                dateTimeColumn.setCellValueFactory(cellData -> 
                    new SimpleStringProperty(cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
                totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
                bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
                bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                // Populate the table with the list of orders for the current user
                ordersTable.getItems().clear();
                ordersTable.getItems().addAll(orders);
            } else {
                System.out.println("No orders found for user: " + username);
            }
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    
    @FXML
    private void loadUserOrders() throws SQLException {
        String username = model.getCurrentUser().getUsername();  // Get the current user's username

        List<Order> orders = model.getOrderDao().getUserOrders(username);  // Fetch user-specific orders

        if (orders != null && !orders.isEmpty()) {
            Collections.reverse(orders);  // Show recent orders first

            orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
            dateTimeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

            ordersTable.getItems().clear();
            ordersTable.getItems().addAll(orders);
        } else {
            System.out.println("No orders found for the user.");
        }
    }

    
    @FXML
    private void populateBooks(Order order) {
        ObservableList<OrderBookEntry> books = FXCollections.observableArrayList();

        // Populate the list with book title and quantity
        for (Map.Entry<Book, Integer> entry : order.getBooksPurchased().entrySet()) {
            books.add(new OrderBookEntry(entry.getKey().getTitle(), entry.getValue()));
        }

        // Set the cell value factories
        bookTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(order.getBookTitle()));
        bookQuantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(order.getQuantity()).asObject());

        // Populate the table with book entries
        booksTable.setItems(books);
    }
    
    @FXML
    private void handleExportSelectedOrders() {
        ObservableList<Order> selectedOrders = ordersTable.getSelectionModel().getSelectedItems();
        if (selectedOrders.isEmpty()) {
            System.out.println("No orders selected for export.");
        } else {
            exportOrdersToCSV(selectedOrders, "selected_orders.csv");
        }
    }

    @FXML
    private void handleExportAllOrders() {
        try {
            List<Order> allOrders = model.getOrderDao().getUserOrders(model.getCurrentUser().getUsername());
            exportOrdersToCSV(FXCollections.observableArrayList(allOrders), "all_orders.csv");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportOrdersToCSV(ObservableList<Order> orders, String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write the header
                writer.write("Order Number,Date & Time,Total Price,Book Title,Quantity");
                writer.newLine();

                // Write the data
                for (Order order : orders) {
                    String row = String.format("%s,%s,%.2f,%s,%d",
                            order.getOrderNumber(),
                            order.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                            order.getTotalPrice(),
                            order.getBookTitle(),
                            order.getQuantity());
                    writer.write(row);
                    writer.newLine();
                }
                System.out.println("Export successful.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Export canceled.");
        }
    }


    @FXML
    private void closeOrdersView() {
        stage.close();  // Close the orders view
    }
}
