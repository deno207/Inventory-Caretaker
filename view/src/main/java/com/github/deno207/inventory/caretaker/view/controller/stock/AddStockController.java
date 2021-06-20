package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;

/**
 * AddStockController controls the pop-up that allows users to add stock to their stock items
 *
 * The amount of stock entered on this screen is added to the current stock level of the item, instead of becoming the
 * new value.
 * @author Damion Wilson
 * @version 1.0
 */
public class AddStockController extends BaseController {
    @FXML protected Label title;
    @FXML protected Label amountLabel;
    @FXML protected TextField amountInput;
    @FXML protected Button saveButton;
    @FXML private Button cancelButton;

    protected final StockItem stockItem;
    protected final UpdateController parentController;

    /**
     * Basic constructor that initialises variables
     * @param stockItem The stock item that the stock is being added to
     * @param parentController The controller that will receive update notifications when the stock is added
     */
    public AddStockController(StockItem stockItem, UpdateController parentController) {
        this.stockItem = stockItem;
        this.parentController = parentController;
    }

    /**
     * initialises UI elements and adds on-click methods to buttons
     */
    @FXML
    public void initialize() {
        title.setText("Add Stock");
        amountLabel.setText("Amount to add");

        saveButton.setText("Add");
        saveButton.setOnMouseClicked(mouseEvent -> onAddStock());
        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());
    }

    /**
     * Gets the amount entered by the user, validates that it is a positive floating point number, and then adds it to
     * the items stock level.
     *
     * If there is an error validating the amount, then an error dialog is show telling the user the problem
     */
    protected void onAddStock() {
        float amount;
        try {
            amount = getAmount();
        } catch (IllegalArgumentException e) {
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        databaseManager.addStockToStockItem(stockItem, amount);

        parentController.update();

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    protected float getAmount() throws IllegalArgumentException {
        String stringAmount = amountInput.getText();
        if (stringAmount == null || stringAmount.isBlank()) {
            throw new IllegalArgumentException("You need to specify an amount to add");
        }
        float amount;
        try {
            amount = Float.parseFloat(stringAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("That is not a valid number", e);
        }
        if (amount < 0) {
            throw new IllegalArgumentException("The amount must be a positive number");
        }
        return amount;
    }
}
