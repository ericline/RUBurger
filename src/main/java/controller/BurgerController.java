package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.util.ArrayList;

import model.Order;
import model.Burger;
import model.AddOns;
import model.Bread;

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
    private TextField priceField;

    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;
    private Burger burger;

    public void setMainController(MainController controller, Stage stage, Stage primaryStage, Scene primaryScene) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
        this.currentOrder = Order.getInstance();
    }


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

    @FXML
    void addToOrder(ActionEvent event) {
        currentOrder.addItem(burger);

        System.out.println(currentOrder.toString());
    }

    @FXML
    void makeCombo(ActionEvent event) {

    }

    @FXML
    public void backToMenu(ActionEvent event) {
        //stage.close(); //close the window.
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

}
