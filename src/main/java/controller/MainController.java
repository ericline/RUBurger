package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.application.Platform;
import java.util.ArrayList;

import model.Order;

import java.io.IOException;

/**
 * The main controller for the main view (main menu)
 * Navigates to the other views.
 * @author Eric Lin, Anish Mande
 */
public class MainController {

    private Stage primaryStage; //the reference of the main window.
    private Scene primaryScene; //the ref. of the scene set to the primaryStage
    private final ArrayList<Order> orderArchive = new ArrayList<>();

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

    /**
     * Called automatically after the FXML file has been loaded.
     * Applies hover effects to the main menu buttons.
     */
    @FXML
    public void initialize() {
        applyColorHoverEffect(burgerButton);
        applyColorHoverEffect(sandwichButton);
        applyColorHoverEffect(beverageButton);
        applyColorHoverEffect(sidesButton);
        applyColorHoverEffect(cartButton);
        applyColorHoverEffect(ordersButton);
        applyColorHoverEffect(exitButton);
    }

    /**
     * Adds a hover color and scale animation to a node.
     * Used specifically on main menu buttons only.
     * @param node the node to apply hover effect on
     */
    private void applyColorHoverEffect(Node node) {
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

    /**
     * Applies only scale-based hover effects (without changing background color).
     * Used throughout other views.
     * @param node the node to apply hover effect on
     */
    public void applyHoverEffect(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }

    /**
     * Handles the exit button click by terminating the application cleanly.
     */
    @FXML
    private void handleExit() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Displays the burger menu view.
     * Loads burger-view.fxml and switches the primary scene.
     */
    @FXML
    protected void displayBurgerView() {
        Stage view1 = new Stage(); //if we want to use a new window
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/burger-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            BurgerController burgerController = loader.getController();
            burgerController.setMainController(this, view1, primaryStage, primaryScene);
            burgerController.applyHoverEffects();
        } catch (IOException e) {
            showLoadError("burger-view.fxml");

        }
    }

    /**
     * Displays the sandwich menu view.
     * Loads sandwich-view.fxml and switches the primary scene.
     */
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
            sandwichController.applyHoverEffects();
        } catch (IOException e) {
            showLoadError("sandwich-view.fxml");
        }
    }

    /**
     * Displays the beverage menu view.
     * Loads beverage-view.fxml and switches the primary scene.
     */
    @FXML
    protected void displayBeverageView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/beverage-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            BeverageController beverageController = loader.getController();
            beverageController.setMainController(this, view1, primaryStage, primaryScene);
            beverageController.applyHoverEffects();
        } catch (IOException e) {
            showLoadError("beverage-view.fxml");

        }
    }

    /**
     * Displays the sides menu view.
     * Loads sides-view.fxml and switches the primary scene.
     */

    @FXML
    protected void displaySidesView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sides-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            SidesController sidesController = loader.getController();
            sidesController.setMainController(this, view1, primaryStage, primaryScene);
            sidesController.applyHoverEffects();
        } catch (IOException e) {
            showLoadError("sides-view.fxml");

        }
    }

    /**
     * Displays the current order view.
     * Loads current-view.fxml, sets controller data, and switches the primary scene.
     */
    @FXML
    protected void displayCurrentOrderView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/current-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            CurrentOrderController currentOrderController = loader.getController();
            currentOrderController.setMainController(this, view1, primaryStage, primaryScene, orderArchive);
            currentOrderController.loadUI();
        } catch (IOException e) {
            showLoadError("current-view.fxml");

        }
    }

    /**
     * Displays the all orders view.
     * Loads orders-view.fxml, sets controller data, and switches the primary scene.
     */
    @FXML
    protected void displayAllOrdersView() {
        Stage view1 = new Stage();
        BorderPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/orders-view.fxml"));
            root = (BorderPane) loader.load();
            Scene scene = new Scene(root, 800, 800);
            primaryStage.setScene(scene);
            OrderController orderController = loader.getController();
            orderController.setMainController(this, view1, primaryStage, primaryScene, orderArchive);
            orderController.loadUI();
        } catch (IOException e) {
            showLoadError("orders-view.fxml");
        }
    }
    /**
     * Displays an alert with an error message when a FXML file cannot be loaded.
     *
     * @param fxmlName the name of the FXML file that failed to load
     */
    private void showLoadError(String fxmlName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Loading " + fxmlName);
        alert.setContentText("Couldn't load " + fxmlName + ".");
        alert.showAndWait();
    }
}