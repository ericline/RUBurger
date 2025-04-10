package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import model.Order;
import model.MenuItem;

import java.util.ArrayList;

/**
 * Controller class for the All Orders view.
 * Handles the display, cancellation, export, and selection of orders.
 * Communicates with MainController to maintain shared data and handle scene switching.
 * @author Eric Lin, Anish Mande
 */
public class OrderController {

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Button exitButton;

    @FXML
    private ListView<MenuItem> orderList;

    @FXML
    private ComboBox<Integer> orderNumber;

    @FXML
    private TextField orderSubtotal;

    @FXML
    private TextField orderTax;

    @FXML
    private TextField orderTotal;

    @FXML
    private Button exportOrderButton;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private ArrayList<Order> orderArchive;

    /**
     * Sets references required for scene switching and data access.
     *
     * @param controller     the main controller for returning to the main menu
     * @param stage          the current stage for this view
     * @param primaryStage   the main application stage
     * @param primaryScene   the main scene to return to
     * @param orderArchive   the shared list of all orders
     */
    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene, ArrayList<Order> orderArchive) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.orderArchive = orderArchive;
    }

    /**
     * Initializes the view, populates the order number ComboBox,
     * sets the initial current order, and updates the UI display.
     */
    public void loadUI() {
        mainController.applyHoverEffect(exportOrderButton);
        mainController.applyHoverEffect(cancelOrderButton);
        mainController.applyHoverEffect(exitButton);

        if (orderArchive == null || orderArchive.isEmpty()) {
            return;
        }

        currentOrder = orderArchive.get(0);

        updatePrices();

        for (Order order : orderArchive) {
            orderNumber.getItems().add(order.getNumber());
        }

        orderNumber.setValue(orderArchive.get(0).getNumber());

        orderNumber.setOnAction(e -> {
            Integer selectedOrderNumber = orderNumber.getValue();
            for (Order order : orderArchive) {
                if (order.getNumber() == selectedOrderNumber) {
                    currentOrder = order;
                    break;
                }
            }
            updatePrices();
        });
    }

    /**
     * Updates the UI to reflect the selected order’s subtotal, tax, total, and item list.
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
     * Exports the selected order to a text file.
     * If the order is empty or export fails, shows appropriate error messages.
     */
    @FXML
    void exportOrder(ActionEvent event) {
        if (currentOrder.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Order");
            alert.setContentText("Cannot export empty order.");
            alert.showAndWait();
            return;
        }

        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Save Order As");
        fileChooser.setInitialFileName("Order#" + currentOrder.getNumber() + ".txt");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
                writer.println(currentOrder.toString());
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Export Failed");
                error.setContentText("Unable to export order.");
                error.showAndWait();
                return;
            }
        }

        orderArchive.remove(currentOrder);
        currentOrder.resetOrder();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Exported");
        alert.setHeaderText("Success!");
        alert.setContentText("Order #" + orderNumber.getValue() + " has been exported.");
        alert.showAndWait();

        handleExit(event);
    }

    /**
     * Cancels the selected order by removing it from the archive.
     * Displays a confirmation alert and returns to the main view.
     *
     * @param event the action event triggered by the cancel button
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
        orderArchive.remove(currentOrder);
        currentOrder.resetOrder();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Canceled");
        alert.setHeaderText("Success!");
        alert.setContentText("Order #" + orderNumber.getValue() + " has been canceled.");
        alert.showAndWait();
        handleExit(event);
    }

    /**
     * Closes the current view and returns to the main scene.
     *
     * @param event the action event triggered by the exit button
     */
    @FXML
    void handleExit(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
