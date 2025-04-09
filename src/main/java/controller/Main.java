package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class that contains the main() to launch the JavaFX application.
 * @author Lily Chang
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/model/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        MainController mainController = fxmlLoader.getController();
        mainController.setPrimaryStage(stage, scene);
        mainController.initialize();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}