package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


import model.Beverage;
import model.Order;
import model.Burger;
import model.Sandwich;
import model.Side;
import model.Combo;
import model.Size;
import model.Flavor;

/**
 * Controller for the Combo view.
 * Allows users to convert a burger or sandwich into a combo by selecting a drink and side.
 * Handles UI logic, image updates, and pricing.
 * @author Eric Lin, Anish Mande
 */
public class ComboController {

    @FXML
    private Button addOrder;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> drinkOptions;

    @FXML
    private ImageView drinksImage;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Integer> quantityOption;

    @FXML
    private TextField sandwichText;

    @FXML
    private ComboBox<Side> sideOptions;

    @FXML
    private ImageView sidesImage;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private Sandwich sandwich;
    private Burger burger;
    private Combo combo;
    private double price = 0;

    /**
     * Initializes and stores references for navigation and data sharing.
     * Called when this controller is loaded from the main menu with a sandwich.
     *
     * @param controller     Reference to MainController
     * @param stage          Secondary stage (if used)
     * @param primaryStage   Main application window
     * @param primaryScene   Main scene to return to
     * @param sandwich       The selected sandwich or burger to combo
     */
    public void setMainController(MainController controller,
                                  Stage stage,
                                  Stage primaryStage,
                                  Scene primaryScene,
                                  Sandwich sandwich) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.currentOrder = Order.getInstance();
        this.sandwich = sandwich;
    }

    /**
     * Initializes the combo options and UI listeners.
     * Called automatically after FXML is loaded.
     */
    @FXML
    private void initialize() {
        sideOptions.getItems().addAll(Side.CHIPS, Side.APPLE_SLICES);
        drinkOptions.getItems().addAll("Coca Cola", "Tea", "Juice");
        sideOptions.setValue(Side.CHIPS);
        drinkOptions.setValue("Coca Cola");

        sideOptions.setOnAction(e -> updateSideImage());
        drinkOptions.setOnAction(e -> updateDrinkImage());

        updateSideImage();
        updateDrinkImage();

        quantityOption.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    /**
     * Updates the side image based on the selected side.
     */
    private void updateSideImage() {
        Side selected = sideOptions.getValue();

        String name = "";
        if (selected.equals(Side.CHIPS)) {
            name = "chips.png";
        }
        else if (selected.equals(Side.APPLE_SLICES)) {
            name = "apples.png";
        }
        else {
            return;
        }

        sidesImage.setImage(new Image(getClass().getResourceAsStream("/image/" + name)));
    }

    /**
     * Updates the drink image based on the selected drink option.
     */
    private void updateDrinkImage() {
        String selected = drinkOptions.getValue();
        String name = "";
        if (selected.equals("Coca Cola")) {
            name = "cola.png";
        }
        else if (selected.equals("Tea")) {
            name = "tea.png";
        }
        else if (selected.equals("Juice")) {
            name = "juice.png";
        }
        else {
            return;
        }
        drinksImage.setImage(new Image(getClass().getResourceAsStream("/image/" + name)));
    }

    /**
     * Loads the UI state with the quantity and subtotal from the original sandwich or burger.
     */
    public void loadUI() {
        if (sandwich instanceof Burger) {
            burger = (Burger) sandwich;
            quantityOption.setValue(burger.getQuantity());
        } else {
            quantityOption.setValue(sandwich.getQuantity());
        }

        quantityOption.setOnAction(e -> updateSubtotal());
        updateSubtotal();
    }

    /**
     * Recalculates and displays the subtotal based on the selected sandwich and quantity.
     * Adds $2.00 per item to represent the combo upcharge.
     */
    private void updateSubtotal() {
        Integer qty = quantityOption.getValue();
        if (sandwich instanceof Burger) {
            if (qty != null) {
                burger.setQuantity(qty);
                priceField.setText(String.format("$%.2f", burger.price() + 2.00 * qty));
            }
        }
        else if (sandwich instanceof Sandwich) {
            if (qty != null) {
                sandwich.setQuantity(qty);
                priceField.setText(String.format("$%.2f", sandwich.price() + 2.00 * qty));
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Sandwich or Burger selected");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please select a sandwich or burger for combo next time.");
            alert.showAndWait();
            backToMenu();
        }
        sandwichText.setText(sandwich.toString());
    }

    /**
     * Creates a Combo object and adds it to the current order.
     * Shows confirmation and returns to the main menu.
     */
    @FXML
    void addToOrder(ActionEvent event) {
        Beverage drink = new Beverage(Size.MEDIUM, Flavor.fromString(drinkOptions.getValue()));
        if (sandwich instanceof Burger) {
            combo = new Combo(burger, drink, sideOptions.getValue());
        }
        else {
            combo = new Combo(sandwich, drink, sideOptions.getValue());
        }

        currentOrder.addItem(combo);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Success!");
        alert.setContentText(combo.toString() + " has been added to the order.");
        alert.showAndWait();
        backToMenu();
    }

    /**
     * Navigates back to the primary scene (main menu).
     */
    @FXML
    public void backToMenu() {
        //stage.close(); //close the window.
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
