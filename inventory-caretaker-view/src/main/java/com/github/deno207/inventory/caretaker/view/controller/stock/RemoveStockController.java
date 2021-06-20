package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.entity.StockItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

/**
 * RemoveStockController controls the screen that allows the user to remove stock from a stock item
 * @author Damion Wilson
 * @version 1.0
 */
public class RemoveStockController extends AddStockController{
    /**
     * Basic constructor that initialises variables
     *
     * @param stockItem        The stock item that the stock is being added to
     * @param parentController The controller that will receive update notifications when the stock is added
     */
    public RemoveStockController(StockItem stockItem, UpdateController parentController) {
        super(stockItem, parentController);
    }

    /**
     * initialise UI elements and add on-click methods to buttons
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

        title.setText("Remove Stock");
        amountLabel.setText("Amount to Remove");
        saveButton.setText("Remove");
    }

    /**
     * Gets the amount entered by the user, validates that it is a positive floating point number, and then removes it
     * from the items stock level.
     *
     * If there is an error validating the amount, then an error dialog is show telling the user the problem
     */
    @Override
    protected void onAddStock() {
        float amount;
        try {
            amount = getAmount();
        } catch (IllegalArgumentException e) {
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        databaseManager.removeStockFromStockItem(stockItem, amount);

        parentController.update();

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
