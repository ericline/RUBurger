module com.example.ruburger {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.testng;


    opens model to javafx.fxml;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
}