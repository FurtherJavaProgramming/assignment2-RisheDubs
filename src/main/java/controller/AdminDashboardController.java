package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ManageUsersScreen;
import view.ManageInventoryScreen;
import view.OrderHistoryScreen;
import view.Login;

public class AdminDashboardController {
    private Stage primaryStage;

    public AdminDashboardController(Stage stage) {
        this.primaryStage = stage;
    }

    // Handle button actions
    @FXML
    public void handleManageUsers(ActionEvent event) {
        ManageUsersScreen manageUsersScreen = new ManageUsersScreen(primaryStage);
        manageUsersScreen.display();
    }

    @FXML
    public void handleViewOrders(ActionEvent event) {
        OrderHistoryScreen orderHistoryScreen = new OrderHistoryScreen(primaryStage);
        orderHistoryScreen.display();
    }

    @FXML
    public void handleManageInventory(ActionEvent event) {
        ManageInventoryScreen manageInventoryScreen = new ManageInventoryScreen(primaryStage);
        manageInventoryScreen.display();
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        primaryStage.setScene(Login.getLoginScene(primaryStage));
    }
}
