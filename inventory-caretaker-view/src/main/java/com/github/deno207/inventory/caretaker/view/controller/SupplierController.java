package com.github.deno207.inventory.caretaker.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.controller.supplier.AddSupplierController;

import java.util.List;

/**
 * SupplierController controls the screen that displays all of the suppliers in the database
 * @author Damion Wilson
 * @version 1.0
 */
public class SupplierController extends InventoryController{

    /**
     * initialise UI elements, adds on-click methods to buttons, and populates the UI with all of the suppliers
     */
    @FXML
    @Override
    public void initialize() {
        inventoryButton.getStyleClass().clear();
        inventoryButton.getStyleClass().add("main-menu");

        supplierButton.getStyleClass().clear();
        supplierButton.getStyleClass().add("main-menu-active");

        addNewItemButton.setText("Add New Supplier");
        super.initialize();
    }

    /**
     * Returns a list of all of the suppliers in the database
     * @return A list of all of the suppliers in the datanase
     */
    @Override
    protected List<? extends DisplayItem> getData() {
        return databaseManager.getAllSuppliers();
    }

    /**
     * Currently empty method as the add supplier screen doesn't exist yet
     */
    @Override
    public void onAddNewItem() {
        Pane pane = loadLayout("/layout/add_supplier.fxml", new AddSupplierController(this));
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * empty method so that nothing happens when the user clicks on the supplier button
     */
    @Override
    protected void onSwitchToSupplier() {}

    /**
     * switches to the inventory screen so the user can view all of the stock items
     */
    @Override
    protected void onSwitchToInventory() {
        Pane root = loadLayout("/layout/inventory.fxml", new InventoryController());

        Stage stage = (Stage) inventoryButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
