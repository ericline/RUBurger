package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller of the second view when navigating from the main view.
 * @author Lily Chang
 */
public class SecondViewController {
    private MainController mainController;
    private Stage stage;
    private Scene primaryScene;
    private Stage primaryStage;
    private ObservableList<String> colorList, fruitList, peopleList;

    @FXML
    private Label output;

    @FXML
    private ComboBox<String> cmb_color, cmb_fruit;

    @FXML
    private ListView<String> lv_fruit, lv_person;

    @FXML
    private Button menuButton;

    /**
     * This method will be automatically performed when the controller object is
     * first created, after the fxml is loaded.
     * Use this method to set up the default values or initialization of GUI objects.
     */
    public void initialize() {
        colorList = FXCollections.observableArrayList("Green", "Red", "Blue", "Yellow");
        fruitList = FXCollections.observableArrayList("Apple", "Orange", "Banana", "Watermelon");
        List people = List.of(new Person("John", "Doe"),
                new Person("Jane", "Doe"),
                new Person("April", "May"),
                new Person("JJ", "Johnson"));
        peopleList = FXCollections.observableArrayList(people);
        lv_person.setItems(peopleList);
        lv_person.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cmb_color.setItems(colorList);
        lv_fruit.setItems(fruitList);
        cmb_color.getSelectionModel().select(0); //pre-select the item in index 0
        cmb_fruit.setItems(fruitList);
        cmb_fruit.getSelectionModel().select(0);
        /*
          The following statements would add the items to the GUI objects only without setting the data source.
          cmb_color.getItems().addAll("Red", "Green", "Blue", "Yellow"); //add to ComboBox object
          lv_fruit.getItems().addAll("Apple", "Orange", "Banana", "Watermelon"); //add to ListView object
        */
    }

    /**
     * Get the reference to the MainController object.
     * We can call any public method defined in the controller through the reference.
     */
    public void setMainController (MainController controller,
                                   Stage stage,
                                   Stage primaryStage,
                                   Scene primaryScene) {
        mainController = controller;
        this.stage = stage;
        this.primaryStage = primaryStage;
        this.primaryScene = primaryScene;
    }
    @FXML
    /**
     * An example to get the private data in the MainController object through the reference.
     */
    public void showValue(ActionEvent event) {
        output.setText(String.valueOf(mainController.getValue()));
    }

    @FXML
    /**
     * Event handler for the ComboBox; this is an ActionEvent handler.
     */
    public void displaySelected(ActionEvent event) {
        String selected = cmb_color.getSelectionModel().getSelectedItem();
        output.setText(selected);
    }

    /**
     * Event handler for the ListView lv_fruit; this is a MouseEvent handler or a mouse click.
     * The item selected in the ListView will be removed from the observable list.
     * Since the source data of the ListView is set to an observable list, they
     * are synchronized. That is, removing the item either from the ListView
     * or from the data source would keep the list on ListView and the source in sync.
     * However, it is a better approach to remove it from the source; after all, the ListView
     * is just a GUI object used to show the data from the source.
     */
    @FXML
    public void displayFruit() {
        int selected = lv_fruit.getSelectionModel().getSelectedIndex(); //get the index of selected item
        //String selected = listview.getSelectionModel().getSelectedItem(); //get the selected object
        if (lv_fruit.getItems().size() != 0) {
            output.setText(lv_fruit.getItems().get(selected) + " is removed.");
            fruitList.remove(selected); //remove the item from the observable list
            //listview.getItems().remove(selected); //remove the item from the ListView
        }
    }

    @FXML
    /**
     * Display the people selected from the ListView (multiple selection)
     */
    public void displayPeople() {
        ObservableList<String> people = lv_person.getSelectionModel().getSelectedItems();
        output.setText(people.toString());
    }

    @FXML
    /**
     *  Remove the first item from the observable list.
     *  Since the source of the ComboBox is set to an observable list, they
     *  are synchronized. That is, removing the item either from the ComboBox
     *  or from the data source would work. However, it is a better approach to
     *  remove it from the source; after all, the ComboBox is just a GUI object
     *  used to show the data from the source.
     *  Please note that, this method is for demo purpose. A typical case to use
     *  a ComboBox (dropdown) is to provide a static list for the user to select
     *  from; thus, doesn't allow the removal of an item.
     */
    public void removeColor(ActionEvent event) {
        if (cmb_color.getItems().size() != 0) {
            output.setText(cmb_color.getItems().get(0).toString() + " is removed.");
            cmb_color.getItems().remove(0); //remove from cmb_color and colorList
            //colorList.remove(0); //remove from colorList and cmb_color
        }
    }

    @FXML
    /**
     * Navigate back to the main view.
     */
    public void displayMain() {
        //stage.close(); //close the window.
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
