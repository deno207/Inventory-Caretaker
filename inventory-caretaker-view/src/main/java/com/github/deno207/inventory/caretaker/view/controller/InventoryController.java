package com.github.deno207.inventory.caretaker.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemFactory;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemType;
import com.github.deno207.inventory.caretaker.view.controller.stock.AddItemController;

import java.util.List;

/**
 * InventoryController controls the screen that displays all of the stock items that are in the database
 * @author Damion Wilson
 * @version 1.2
 */
public class InventoryController extends BaseController implements UpdateController {
    private static final int MAX_COLUMN_NUMBER = 5;
    @FXML
    private GridPane itemGrid;
    @FXML
    protected Button addNewItemButton;
    @FXML
    protected Label inventoryButton;
    @FXML
    protected Label supplierButton;
    @FXML
    protected Label categoryButton;

    /**
     * sets all of the on-click methods and then populates the UI with all of the stock items
     */
    @FXML
    public void initialize() {
        addNewItemButton.setOnMouseClicked(mouseEvent -> onAddNewItem());

        inventoryButton.setOnMouseClicked(mouseEvent -> onSwitchToInventory());
        supplierButton.setOnMouseClicked(mouseEvent -> onSwitchToSupplier());
        categoryButton.setOnMouseClicked(mouseEvent -> onSwitchToCategory());

        populateStockItemGrid();
    }

    /**
     * populates the main grid view with a series of grid items representing all of the display items that are to be
     * displayed
     *
     * If no display items can be displayed, then a label explaining there are no items to be displayed is added instead
     */
    private void populateStockItemGrid() {
        List<? extends DisplayItem> displayItemList = getData();
        //if they are no items in the list, display empty list message
        if (displayItemList.isEmpty()) {
            Label emptyListLabel = new Label();
            emptyListLabel.setText("There are currently no items to display");
            emptyListLabel.getStyleClass().add("large-text");
            itemGrid.add(emptyListLabel, 0, 0);
            GridPane.setColumnSpan(emptyListLabel, MAX_COLUMN_NUMBER);
        } else {
            //otherwise populate the GridPane
            Pane gridItem;
            GridItemController gridItemController;
            int columnCounter = 0;
            int rowCounter = 0;
            for (DisplayItem displayItem : displayItemList) {
                //create grid item from fxml and populate it with data
                gridItemController= GridItemFactory.getGridItemController(displayItem, this,
                        GridItemType.DISPLAY);
                gridItem = loadLayout("/layout/grid_item.fxml", gridItemController);
                itemGrid.add(gridItem, columnCounter++, rowCounter);

                //go to next row after filling up the current one
                if (columnCounter >= MAX_COLUMN_NUMBER) {
                    columnCounter = 0;
                    rowCounter += 1;
                }
            }
        }
    }

    /**
     * Returns a list of all of the stock items in the database
     * @return A list of all of the stock items in the database
     */
    protected List<? extends DisplayItem> getData() {
        return databaseManager.getAllStockItems();
    }

    /**
     * repopulates the UI with the updated data
     */
    @Override
    public void update() {
        populateStockItemGrid();
    }

    /**
     * opens the add new stock item screen so a new stock item can be added to the database
     */
    protected void onAddNewItem() {
        Pane root = loadLayout("/layout/add_item.fxml", new AddItemController(this));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * switches to the supplier screen so all of the suppliers can be viewed
     */
    protected void onSwitchToSupplier() {
        Pane root = loadLayout("/layout/inventory.fxml", new SupplierController());

        Stage stage = (Stage) supplierButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * switches to the category screen so that all of the categories can be viewed
     */
    protected void onSwitchToCategory() {
        Pane root = loadLayout("/layout/inventory.fxml", new CategoryController());

        Stage stage = (Stage) inventoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * An empty method so that nothing happens when the inventory button is clicked
     */
    protected void onSwitchToInventory() {}
}
