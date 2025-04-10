package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Order;
import model.MenuItem;

import java.util.ArrayList;

public class CurrentOrderController {

    @FXML
    private HBox bottomBar;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Button exitButton;

    @FXML
    private ListView<MenuItem> orderList;

    @FXML
    private TextField orderNumber;

    @FXML
    private TextField orderSubtotal;

    @FXML
    private TextField orderTax;

    @FXML
    private TextField orderTotal;

    @FXML
    private Button removeItemButton;

    @FXML
    private Button sendOrderButton;

    @FXML
    private HBox topBar;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private ArrayList<Order> orderArchive;

    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene, ArrayList<Order> orderArchive) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orderArchive = orderArchive;
        this.currentOrder = Order.getInstance();
    }

    public void loadUI() {
        mainController.applyHoverEffect(sendOrderButton);
        mainController.applyHoverEffect(cancelOrderButton);
        mainController.applyHoverEffect(removeItemButton);
        mainController.applyHoverEffect(exitButton);

        updatePrices();
        orderNumber.setText("Order #" + String.valueOf(currentOrder.getNumber()));
    }

    private void updatePrices() {
        orderList.getItems().setAll(currentOrder.getItems());
        double subtotal = currentOrder.calculateTotal();
        double tax = subtotal * 0.06625;
        double total = subtotal + tax;

        orderSubtotal.setText(String.format("$%.2f", subtotal));
        orderTax.setText(String.format("$%.2f", tax));
        orderTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    void sendOrder(ActionEvent event) {
        orderArchive.add(currentOrder.cloneOrder());

        currentOrder.resetOrder();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Sent");
        alert.setHeaderText("Success!");
        alert.setContentText(orderNumber.getText() + " has been sent.");
        alert.showAndWait();

        handleExit(event);
    }

    @FXML
    void cancelItem(ActionEvent event) {
        MenuItem selectedItem = orderList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Item Selected");
            alert.setContentText("Please select an item to remove.");
            alert.showAndWait();
            return;
        }
        currentOrder.removeItem(selectedItem);

        updatePrices();
    }

    @FXML
    void cancelOrder(ActionEvent event) {
        currentOrder.resetOrder();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Canceled");
        alert.setHeaderText("Success!");
        alert.setContentText(orderNumber.getText() + " has been canceled.");
        alert.showAndWait();
        handleExit(event);
    }

    @FXML
    void handleExit(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
