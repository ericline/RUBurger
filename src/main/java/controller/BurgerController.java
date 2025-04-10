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

import java.io.IOException;
import java.util.ArrayList;

import javafx.util.Duration;
import model.Order;
import model.Burger;
import model.AddOns;
import model.Bread;

/**
 * Controller for the Burger view.
 * Manages burger customization, subtotal updates, and interactions for adding the item to the order
 * or making it a combo.
 * @author Eric Lin, Anish Mande
 */
public class BurgerController {

    @FXML
    private Button addOrder;

    @FXML
    private CheckBox burgerAvocado;

    @FXML
    private RadioButton burgerBrioche;

    @FXML
    private RadioButton burgerDouble;

    @FXML
    private CheckBox burgerLettuce;

    @FXML
    private CheckBox burgerOnion;

    @FXML
    private CheckBox burgerCheese;

    @FXML
    private RadioButton burgerPretzel;

    @FXML
    private ComboBox<Integer> burgerQuantity;

    @FXML
    private RadioButton burgerSingle;

    @FXML
    private CheckBox burgerTomato;

    @FXML
    private RadioButton burgerWheat;

    @FXML
    private Button makeCombo;

    @FXML
    private Button backButton;

    @FXML
    private TextField priceField;

    // Controller references
    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    // Current order and selected burger
    private Order currentOrder;
    private Burger burger;

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
     * Initializes the burger view, populates combo boxes, and sets up action listeners
     * for UI elements to trigger subtotal recalculations.
     */
    @FXML
    public void initialize() {
        burgerQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        burgerQuantity.setValue(1);

        burgerSingle.setOnAction(e -> updateSubtotal());
        burgerDouble.setOnAction(e -> updateSubtotal());

        burgerBrioche.setOnAction(e -> updateSubtotal());
        burgerWheat.setOnAction(e -> updateSubtotal());
        burgerPretzel.setOnAction(e -> updateSubtotal());

        burgerLettuce.setOnAction(e -> updateSubtotal());
        burgerTomato.setOnAction(e -> updateSubtotal());
        burgerOnion.setOnAction(e -> updateSubtotal());
        burgerAvocado.setOnAction(e -> updateSubtotal());
        burgerCheese.setOnAction(e -> updateSubtotal());

        burgerQuantity.setOnAction(e -> updateSubtotal());

        updateSubtotal();
    }

    /**
     * Applies hover effects to UI buttons using the shared method from MainController.
     */
    public void applyHoverEffects() {
        mainController.applyHoverEffect(addOrder);
        mainController.applyHoverEffect(makeCombo);
        mainController.applyHoverEffect(backButton);
    }

    /**
     * Calculates and updates the subtotal for the current burger selection
     * based on bread, size (single/double), and selected add-ons.
     */
    private void updateSubtotal() {
        boolean isDouble = burgerDouble.isSelected();

        // Determine bread
        Bread bread = null;
        if (burgerBrioche.isSelected()) {
            bread = Bread.BRIOCHE;
        } else if (burgerWheat.isSelected()) {
            bread = Bread.WHEAT_BREAD;
        } else if (burgerPretzel.isSelected()) {
            bread = Bread.PRETZEL;
        }

        // Gather add-ons
        ArrayList<AddOns> selectedAddOns = new ArrayList<>();
        if (burgerLettuce.isSelected()) selectedAddOns.add(AddOns.LETTUCE);
        if (burgerTomato.isSelected()) selectedAddOns.add(AddOns.TOMATOES);
        if (burgerOnion.isSelected()) selectedAddOns.add(AddOns.ONIONS);
        if (burgerAvocado.isSelected()) selectedAddOns.add(AddOns.AVOCADO);
        if (burgerCheese.isSelected()) selectedAddOns.add(AddOns.CHEESE);

        burger = new Burger(bread, isDouble, selectedAddOns);

        Integer qty = burgerQuantity.getValue();
        if (qty != null) {
            burger.setQuantity(qty);
        }

        double price = burger.price();
        priceField.setText(String.format("$%.2f", price));
    }

    /**
     * Adds the current burger configuration to the active order.
     * Displays confirmation and navigates back to the main menu.
     */
    @FXML
    void addToOrder(ActionEvent event) {
        currentOrder.addItem(burger);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Success!");
        alert.setContentText(burger.toString() + " has been added to the order.");
        alert.showAndWait();
        backToMenu(event);
    }

    /**
     * Loads the Combo view, passing the current burger to the next controller.
     * Handles scene transition and error reporting.
     */
    @FXML
    void makeCombo(ActionEvent event) {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/combo-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            ComboController comboController = loader.getController();
            comboController.setMainController(mainController, view1, primaryStage, primaryScene, burger);
            comboController.loadUI();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading combo-view.fxml.");
            alert.setContentText("Couldn't load combo-view.fxml.");
            alert.showAndWait();
            backToMenu(event);
        }
    }

    /**
     * Returns to the main menu by restoring the primary scene and closing the secondary stage.
     */
    @FXML
    public void backToMenu(ActionEvent event) {
        stage.close(); //close the window.
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
