package com.github.deno207.inventory.caretaker.view.controller.supplier;

import javafx.fxml.FXML;
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.SelectionController;
import com.github.deno207.inventory.caretaker.view.controller.category.SelectCategoryController;

import java.util.List;

/**
 * SelectSupplierController controls the screen that allows the user to select a supplier
 * @author Damion Wilson
 * @version 1.1
 */
public class SelectSupplierController extends SelectCategoryController implements SelectionController {

    /**
     * basic constructor that initialise variables
     * @param parentController The controller that will receive the selection choice
     */
    public SelectSupplierController(SelectionController parentController) {
        super(parentController);
    }

    /**
     * initialise UI elements and populates the UI with all of the suppliers
     */
    @FXML
    @Override
    public void initialize() {
        super.initialize();

        titleLabel.setText("Select Supplier");
    }

    /**
     * gets the list of suppliers form the database
     * @return A list of all of the Suppliers
     */
    @Override
    protected List<? extends DisplayItem> getData() {
        return databaseManager.getAllSuppliers();
    }
}
