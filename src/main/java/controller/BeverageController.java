package controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import model.Beverage;
import model.Order;
import model.Flavor;
import model.Size;

/**
 * Controller for the Beverage view.
 * Manages beverage selection, quantity, size, price calculation, and adding the beverage to the order.
 * @author Eric Lin, Anish Mande
 */
public class BeverageController {

    @FXML
    private Button addOrder;

    @FXML
    private Button backButton;

    @FXML
    private ListView<Flavor> flavorList;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Integer> quantityOption;

    @FXML
    private ComboBox<Size> sizeOption;

    // Controller references
    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    // Current order and selected beverage
    private Order currentOrder;
    private Beverage drink;

    /**
     * Initializes references and sets up shared controller navigation.
     *
     * @param controller      Reference to the main controller
     * @param stage           The secondary stage
     * @param primaryStage    The main application stage
     * @param primaryScene    The main scene to return to
     */
    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.currentOrder = Order.getInstance();
    }

    /**
     * Initializes dropdowns, sets defaults, and applies listeners for real-time price updates.
     */
    @FXML
    private void initialize() {
        // Populate flavor list
        flavorList.getItems().setAll(Flavor.values());
        if (!flavorList.getItems().isEmpty()) {
            flavorList.getSelectionModel().selectFirst();
        }

        // Populate size list
        sizeOption.getItems().setAll(Size.values());
        sizeOption.setValue(Size.MEDIUM);

        // Populate quantity
        quantityOption.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        quantityOption.setValue(1);

        sizeOption.setOnAction(e -> updateSubtotal());
        quantityOption.setOnAction(e -> updateSubtotal());

        updateSubtotal();
    }

    /**
     * Applies hover effect animations to buttons using the shared method from MainController.
     */
    public void applyHoverEffects() {
        mainController.applyHoverEffect(addOrder);
        mainController.applyHoverEffect(backButton);
    }

    /**
     * Updates the subtotal for the selected beverage based on flavor, size, and quantity.
     * Updates the price display.
     */
    private void updateSubtotal() {
        Flavor selectedFlavor = flavorList.getSelectionModel().getSelectedItem();
        Size selectedSize = sizeOption.getValue();
        Integer quantity = quantityOption.getValue();

        drink = new Beverage(selectedSize, selectedFlavor);

        if (quantity != null) {
            drink.setQuantity(quantity);
        }

        double price = drink.price();
        priceField.setText(String.format("$%.2f", price));
    }

    /**
     * Event handler for the "Add to Order" button.
     * Adds the selected beverage to the current order and returns to the main menu.
     */
    @FXML
    void addToOrder(ActionEvent event) {

        currentOrder.addItem(drink);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Success!");
        alert.setContentText(drink + " has been added to the order.");
        alert.showAndWait();
        backToMenu(event);
    }

    /**
     * Navigates back to the primary scene (main menu).
     */
    @FXML
    void backToMenu(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}