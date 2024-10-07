package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Order;
import model.User;
import view.UserDashboard;  // Ensure this is correctly imported

public class OrderHistoryController {

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

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
        // Set up column bindings to Order properties
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().orderNumberProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Populate the table with orders
        orderTable.setItems(getOrderHistory());
    }

    private ObservableList<Order> getOrderHistory() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.add(new Order("Order001", 90.0));
        orders.add(new Order("Order002", 120.0));
        return orders;
    }

    @FXML
    private void returnToDashboard() {
        UserDashboard userDashboard = new UserDashboard(primaryStage, user);
        userDashboard.display();
    }
}
