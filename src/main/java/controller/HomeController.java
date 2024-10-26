package controller;

import model.Book;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController {

    private Model model;
    private Stage stage;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<String> booksListView;

    @FXML
    private MenuItem updateProfile;
    
    @FXML
    private Button shoppingCartButton;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Button viewOrdersButton;

    @FXML
    private Button orderBooksButton;

    public HomeController() {}

    @FXML
    public void initialize() {
        updateProfile.setOnAction(event -> openEditProfile());
        shoppingCartButton.setOnAction(event -> openShoppingCart());
        orderBooksButton.setOnAction(event -> openOrderBooksPage());
        viewOrdersButton.setOnAction(event -> {
			try {
				openOrdersPage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});  // Added action for view orders button
    }

    public void showWelcomeMessage() {
        if (model != null) {
            User currentUser = model.getCurrentUser();
            if (currentUser != null) {
                welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
            }
        }
    }

    private void loadTop5Books() throws SQLException {
        if (model != null) {
            List<Book> topBooks = model.getBookDao().getTop5Books();

            for (Book book : topBooks) {
                booksListView.getItems().add(book.getTitle() + " - Sold: " + book.getSoldCopies());
            }
        }
    }

    public void setModel(Model model) {
        this.model = model;
        showWelcomeMessage();

        try {
            loadTop5Books();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void openEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfileView.fxml"));
            Pane editProfileRoot = loader.load();

            EditProfileController editProfileController = loader.getController();
            editProfileController.setModel(model);

            Scene editProfileScene = new Scene(editProfileRoot);
            Stage editProfileStage = new Stage();
            editProfileController.setStage(editProfileStage);

            editProfileStage.setScene(editProfileScene);
            editProfileStage.setTitle("Edit Profile");
            editProfileStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openShoppingCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShoppingCartView.fxml"));
            Pane cartRoot = loader.load();

            ShoppingCartController cartController = loader.getController();
            cartController.setModel(model);

            Scene cartScene = new Scene(cartRoot);
            Stage cartStage = new Stage();
            cartController.setStage(cartStage);

            cartStage.setScene(cartScene);
            cartStage.setTitle("Shopping Cart");
            cartStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openOrderBooksPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderBooksView.fxml"));
            Pane orderBooksRoot = loader.load();

            OrderBooksController orderBooksController = loader.getController();
            orderBooksController.setModel(model);

            Scene orderBooksScene = new Scene(orderBooksRoot);
            Stage orderBooksStage = new Stage();
            orderBooksController.setStage(orderBooksStage);

            orderBooksStage.setScene(orderBooksScene);
            orderBooksStage.setTitle("Order Books");
            orderBooksStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openOrdersPage() throws SQLException {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrdersView.fxml"));
            VBox ordersRoot = loader.load();

            OrdersController ordersController = loader.getController();
            ordersController.setModel(model);  // Reload the model to fetch new data

            Stage ordersStage = new Stage();
            ordersController.setStage(ordersStage);

            Scene scene = new Scene(ordersRoot);
            ordersStage.setScene(scene);
            ordersStage.setTitle("View Orders");
            ordersStage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
}
