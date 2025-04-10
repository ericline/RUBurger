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

/**
 * Controller for the Current Order view.
 * Displays the current order's contents, allows users to remove items, send the order,
 * or cancel it entirely. Also archives the order history.
 * @author Eric Lin, Anish Mande
 */

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

    /**
     * Initializes controller with scene and application references,
     * and sets the shared order archive and current order.
     *
     * @param controller     Main controller reference
     * @param stage          Current view stage
     * @param primaryStage   Main application window
     * @param primaryScene   Scene to return to
     * @param orderArchive   Shared list of submitted orders
     */
    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene, ArrayList<Order> orderArchive) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orderArchive = orderArchive;
        this.currentOrder = Order.getInstance();
    }

    /**
     * Loads the UI with current order details and applies hover effects to buttons.
     * Also displays the order number.
     */
    public void loadUI() {
        mainController.applyHoverEffect(sendOrderButton);
        mainController.applyHoverEffect(cancelOrderButton);
        mainController.applyHoverEffect(removeItemButton);
        mainController.applyHoverEffect(exitButton);

        updatePrices();
        orderNumber.setText("Order #" + String.valueOf(currentOrder.getNumber()));
    }

    /**
     * Updates the subtotal, tax, and total fields based on the current order contents.
     * Also populates the ListView with the order's items.
     */
    private void updatePrices() {
        orderList.getItems().setAll(currentOrder.getItems());
        double subtotal = currentOrder.calculateTotal();
        double tax = subtotal * 0.06625;
        double total = subtotal + tax;

        orderSubtotal.setText(String.format("$%.2f", subtotal));
        orderTax.setText(String.format("$%.2f", tax));
        orderTotal.setText(String.format("$%.2f", total));
    }

    /**
     * Sends the current order by archiving it and resetting the active order.
     * Shows a confirmation alert. Does not allow sending if the order is empty.
     */
    @FXML
    void sendOrder(ActionEvent event) {
        if (currentOrder.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Order");
            alert.setContentText("Cannot send empty order.");
            alert.showAndWait();
            return;
        }

        orderArchive.add(currentOrder.cloneOrder());

        currentOrder.resetOrder();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Sent");
        alert.setHeaderText("Success!");
        alert.setContentText(orderNumber.getText() + " has been sent.");
        alert.showAndWait();

        handleExit(event);
    }

    /**
     * Removes the selected item from the current order.
     * Shows an alert if no item is selected.
     */
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

    /**
     * Cancels the entire current order and resets it.
     * Shows an alert confirming cancellation. Blocks action if order is empty.
     */
    @FXML
    void cancelOrder(ActionEvent event) {
        if (currentOrder.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Order");
            alert.setContentText("There are no items in the order.");
            alert.showAndWait();
            return;
        }
        currentOrder.resetOrder();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Canceled");
        alert.setHeaderText("Success!");
        alert.setContentText(orderNumber.getText() + " has been canceled.");
        alert.showAndWait();
        handleExit(event);
    }

    /**
     * Navigates back to the primary scene (main menu).
     */
    @FXML
    void handleExit(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
