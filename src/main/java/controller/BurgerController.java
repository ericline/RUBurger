package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Order;
import model.Burger;

public class BurgerController {
    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;

    private Order currentOrder;

    public BurgerController(MainController controller, Stage stage, Scene primaryScene) {
        this.mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
    }

}
