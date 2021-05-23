package uk.ac.aber.cs39440.inventory.caretaker.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.category.AddCategoryController;

import java.util.List;

/**
 * CategoryController controls the screen that displays all of the categories that are in the database
 * @author Damion Wilson
 * @version 1.0
 */
public class CategoryController extends InventoryController{

    /**
     * initialises the UI elements, adds on-click methods to the buttons and populates the UI with the list of categories
     */
    @FXML
    @Override
    public void initialize() {
        inventoryButton.getStyleClass().clear();
        inventoryButton.getStyleClass().add("main-menu");

        categoryButton.getStyleClass().clear();
        categoryButton.getStyleClass().add("main-menu-active");

        addNewItemButton.setText("Add New Category");

        super.initialize();
    }

    /**
     * returns a list of all of the categories in the database
     * @return A list of all categories in the database
     */
    @Override
    protected List<? extends DisplayItem> getData() {
        return databaseManager.getAllCategories();
    }

    /**
     * opens the add new category screen so that a new category can be added to the system
     */
    @Override
    protected void onAddNewItem() {
        Pane pane = loadLayout("/layout/add_category.fxml", new AddCategoryController(this));

        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * changes the onSwitchToCategory to an empty method so nothing happens when the user clicks on the Category button
     */
    @Override
    protected void onSwitchToCategory() {}

    /**
     * switches to the inventory screen so that user can view all of the stock items
     */
    @Override
    protected void onSwitchToInventory() {
        Pane root = loadLayout("/layout/inventory.fxml", new InventoryController());

        Stage stage = (Stage) inventoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
