package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import model.Order;
import model.Side;
import model.Size;
import model.SideItem;

/**
 * Controller class for the Sides view.
 * Manages user interaction for selecting a side, size, and quantity,
 * updating the subtotal, and adding the side item to the current order.
 * @author Eric Lin, Anish Mande
 */
public class SidesController {

    @FXML
    private Button addOrder;

    @FXML
    private Button backButton;

    @FXML
    private ListView<Side> sidesList;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Integer> quantityOption;

    @FXML
    private ComboBox<Size> sizeOption;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private Side side;
    private SideItem sideItem;

    /**
     * Sets references to the main controller, stages, and scene for returning to the menu.
     *
     * @param controller     the main controller reference
     * @param stage          the current popup stage
     * @param primaryStage   the main application stage
     * @param primaryScene   the main menu scene to return to
     */
    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.currentOrder = Order.getInstance();
    }

    /**
     * Initializes the sides view UI.
     * Populates the side, size, and quantity controls and sets listeners
     * to update the subtotal when selections change.
     */
    @FXML
    private void initialize() {
        // Populate Sides list
        sidesList.getItems().setAll(Side.values());
        if (!sidesList.getItems().isEmpty()) {
            sidesList.getSelectionModel().selectFirst();
        }

        // Populate size list
        sizeOption.getItems().setAll(Size.values());
        sizeOption.setValue(Size.MEDIUM);

        // Populate quantity
        quantityOption.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        quantityOption.setValue(1);


        sidesList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateSubtotal();
        });
        sizeOption.setOnAction(e -> updateSubtotal());
        quantityOption.setOnAction(e -> updateSubtotal());

        updateSubtotal();
    }

    /**
     * Applies hover scaling effect to interactive buttons.
     */
    public void applyHoverEffects() {
        mainController.applyHoverEffect(addOrder);
        mainController.applyHoverEffect(backButton);
    }

    /**
     * Updates the subtotal price displayed based on the selected side,
     * size, and quantity.
     */
    private void updateSubtotal() {
        Side selectedSide = sidesList.getSelectionModel().getSelectedItem();
        Size selectedSize = sizeOption.getValue();
        Integer quantity = quantityOption.getValue();

        sideItem = new SideItem(selectedSide, selectedSize);

        if (quantity != null) {
            sideItem.setQuantity(quantity);
        }

        double price = sideItem.price();
        priceField.setText(String.format("$%.2f", price));
    }

    /**
     * Adds the selected side item to the current order and displays a confirmation.
     * @param event the action event triggered by clicking the add button
     */
    @FXML
    void addToOrder(ActionEvent event) {

        currentOrder.addItem(sideItem);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Success!");
        alert.setContentText(sideItem + " has been added to the order.");
        alert.showAndWait();
        backToMenu(event);
    }

    /**
     * Closes the sides view and returns to the main menu scene.
     * @param event the action event triggered by clicking the back button
     */
    @FXML
    void backToMenu(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}