package controller;

import javafx.fxml.FXMLLoader;
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

import model.Order;
import model.Sandwich;
import model.AddOns;
import model.Bread;
import model.Protein;

/**
 * Controller class for the Sandwich view.
 * Handles sandwich customization, subtotal calculation, combo creation,
 * order placement, and view transitions.
 * @author Eric Lin, Anish Mande
 */
public class SandwichController {

    @FXML
    private Button addOrder;

    @FXML
    private Button backButton;


    @FXML
    private RadioButton chicken;

    @FXML
    private Button makeCombo;

    @FXML
    private TextField priceField;

    @FXML
    private RadioButton roastBeef;

    @FXML
    private RadioButton salmon;

    @FXML
    private CheckBox sandwichAvocado;

    @FXML
    private RadioButton sandwichBrioche;

    @FXML
    private CheckBox sandwichCheese;

    @FXML
    private CheckBox sandwichLettuce;

    @FXML
    private CheckBox sandwichOnion;

    @FXML
    private RadioButton sandwichPretzel;

    @FXML
    private ComboBox<Integer> sandwichQuantity;

    @FXML
    private CheckBox sandwichTomato;

    @FXML
    private RadioButton sandwichWheat;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private Sandwich sandwich;

    /**
     * Sets up necessary references to parent controller, stages, and order.
     *
     * @param controller     the main controller
     * @param stage          the current window stage
     * @param primaryStage   the main application stage
     * @param primaryScene   the main scene to return to
     */
    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.currentOrder = Order.getInstance();
    }

    /**
     * Initializes the view components.
     * Sets default values, populates the quantity dropdown, and
     * adds listeners to update subtotal on user interaction.
     */
    @FXML
    private void initialize() {
        sandwichQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        sandwichQuantity.setValue(1);

        roastBeef.setOnAction(e -> updateSubtotal());
        salmon.setOnAction(e -> updateSubtotal());
        chicken.setOnAction(e -> updateSubtotal());

        sandwichBrioche.setOnAction(e -> updateSubtotal());
        sandwichWheat.setOnAction(e -> updateSubtotal());
        sandwichPretzel.setOnAction(e -> updateSubtotal());

        sandwichLettuce.setOnAction(e -> updateSubtotal());
        sandwichTomato.setOnAction(e -> updateSubtotal());
        sandwichOnion.setOnAction(e -> updateSubtotal());
        sandwichAvocado.setOnAction(e -> updateSubtotal());
        sandwichCheese.setOnAction(e -> updateSubtotal());

        sandwichQuantity.setOnAction(e -> updateSubtotal());

        updateSubtotal();
    }

    /**
     * Applies hover scaling effects to the buttons in the view.
     */
    public void applyHoverEffects() {
        mainController.applyHoverEffect(addOrder);
        mainController.applyHoverEffect(makeCombo);
        mainController.applyHoverEffect(backButton);
    }

    /**
     * Updates the subtotal price shown in the UI based on
     * selected protein, bread, add-ons, and quantity.
     */
    private void updateSubtotal() {
        // Determine protein
        Protein protein = null;
        if (roastBeef.isSelected()) {
            protein = Protein.ROAST_BEEF;
        } else if (salmon.isSelected()) {
            protein = Protein.SALMON;
        } else if (chicken.isSelected()) {
            protein = Protein.CHICKEN;
        }

        // Determine bread
        Bread bread = null;
        if (sandwichBrioche.isSelected()) {
            bread = Bread.BRIOCHE;
        } else if (sandwichWheat.isSelected()) {
            bread = Bread.WHEAT_BREAD;
        } else if (sandwichPretzel.isSelected()) {
            bread = Bread.PRETZEL;
        }

        // Gather add-ons
        ArrayList<AddOns> selectedAddOns = new ArrayList<>();
        if (sandwichLettuce.isSelected()) selectedAddOns.add(AddOns.LETTUCE);
        if (sandwichTomato.isSelected()) selectedAddOns.add(AddOns.TOMATOES);
        if (sandwichOnion.isSelected()) selectedAddOns.add(AddOns.ONIONS);
        if (sandwichAvocado.isSelected()) selectedAddOns.add(AddOns.AVOCADO);
        if (sandwichCheese.isSelected()) selectedAddOns.add(AddOns.CHEESE);

        sandwich = new Sandwich(bread, protein, selectedAddOns);

        Integer qty = sandwichQuantity.getValue();
        if (qty != null) {
            sandwich.setQuantity(qty);
        }

        double price = sandwich.price();
        priceField.setText(String.format("$%.2f", price));
    }

    /**
     * Adds the configured sandwich to the current order and returns to the menu.
     * @param event the action event triggered by clicking the add button
     */
    @FXML
    void addToOrder(ActionEvent event) {
        currentOrder.addItem(sandwich);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Success!");
        alert.setContentText(sandwich.toString() + " has been added to the order.");
        alert.showAndWait();
        backToMenu(event);
    }

    /**
     * Transitions to the combo creation view, passing the current sandwich to it.
     * @param event the action event triggered by clicking the combo button
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
            comboController.setMainController(mainController, view1, primaryStage, primaryScene, sandwich);
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
     * Closes the current view and returns to the main scene.
     * @param event the action event triggered by clicking the back button
     */
    @FXML
    public void backToMenu(ActionEvent event) {
        stage.close();
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
