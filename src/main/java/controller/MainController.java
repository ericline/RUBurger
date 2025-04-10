package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.application.Platform;
import model.Burger;

import java.io.IOException;

/**
 * Demo multiple views and controllers.
 * The main controller for the main view (main menu.)
 * Navigate to the second view.
 * @author Lily Chang
 */
public class MainController {

    private int value = 10; //this value will be retrieved in the SecondController
    private Stage primaryStage; //the reference of the main window.
    private Scene primaryScene; //the ref. of the scene set to the primaryStage

    @FXML
    private Button beverageButton;

    @FXML
    private Button burgerButton;

    @FXML
    private Button cartButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button sandwichButton;

    @FXML
    private Button sidesButton;

    @FXML
    private Button exitButton;

    /**
     * Set the reference of the stage and scene before show()
     * @param stage the stage used to display the scene
     * @param scene the scene set to the stage
     */
    public void setPrimaryStage(Stage stage, Scene scene) {
        primaryStage = stage;
        primaryScene = scene;
    }

    @FXML
    public void initialize() {
        applyHoverEffect(burgerButton);
        applyHoverEffect(sandwichButton);
        applyHoverEffect(beverageButton);
        applyHoverEffect(sidesButton);
        applyHoverEffect(cartButton);
        applyHoverEffect(ordersButton);
        applyHoverEffect(exitButton);
    }

    private void applyHoverEffect(Node node) {
        node.setOnMouseEntered(e -> {
            node.setStyle("-fx-background-color: #d44833"); // Or use a different style per node
            ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        node.setOnMouseExited(e -> {
            node.setStyle("-fx-background-color: #E06C5A");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }

    @FXML
    private void handleExit() {
        // Cleanly exit the application
        Platform.exit();
        System.exit(0); // Optional, ensures complete shutdown
    }

    /**
     * When the image button is clicked, a new window(stage) will be displayed.
     * The scene graph associated with the window is second-view.fxml, in which the
     * fx:controller attribute defines the controller as SecondViewController.
     * When the fxml file is loading, an instance of SecondController will be created
     * To get the reference of the controller, use the getController() method.
     */
    @FXML
    protected void displayBurgerView() {
        Stage view1 = new Stage(); //if we want to use a new window
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/burger-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            //view1.setScene(scene); //if we want to use the new window to draw the scene graph
            //view1.setTitle("view1");
            //view1.show();
            primaryStage.setScene(scene);
            BurgerController burgerController = loader.getController();
            /*
              The statement below is to pass the references of the MainController objects
              to the SecondViewController object so the SecondViewController can call the
              public methods in the MainController or to navigate back to the main view.
             */
            burgerController.setMainController(this, view1, primaryStage, primaryScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading burger-view.fxml.");
            alert.setContentText("Couldn't load burger-view.fxml.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void displaySandwichView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sandwich-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            SandwichController sandwichController = loader.getController();
            sandwichController.setMainController(this, view1, primaryStage, primaryScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Loading sandwich-view.fxml.");
            alert.setContentText("Couldn't load sandwich-view.fxml.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void displayBeverageView() {

    }

    @FXML
    protected void displaySidesView() {

    }

    @FXML
    protected void displayAllOrdersView() {

    }

    @FXML
    protected void displayOrderVIew() {

    }

    /**
     * The SecondViewController can use this getter method to read the private data encapsulated
     * in this class.
     * @return the integer value in this class.
     */
    public int getValue() {
        return value;
    }
}