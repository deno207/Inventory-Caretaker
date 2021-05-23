package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.stock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.SubItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.UpdateController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.BaseController;

/**
 * AddSubItemController controls the screen that allows users to add sub-items to stock items
 * @author Damion Wilson
 * @version 1.0
 */
public class AddSubItemController extends BaseController {
    @FXML protected Label title;
    @FXML protected TextField displayIdInput;
    @FXML protected TextField amountInput;
    @FXML protected TextField locationInput;
    @FXML protected Button addButton;
    @FXML private Button cancelButton;
    protected int stockItemId;
    protected UpdateController parentController;

    /**
     * Basic constructor that initialises variables
     * @param stockItemId The database id of the stock this sub-item will be added to
     * @param parentController The controller that will receive an update notification when the sub-item is added to
     *                         the database
     */
    public AddSubItemController(int stockItemId, UpdateController parentController) {
        this.stockItemId = stockItemId;
        this.parentController = parentController;
    }

    /**
     * initialises UI elements and adds on-click methods to buttons
     */
    @FXML
    public void initialize() {
        title.setText("Add Sub-Item");

        addButton.setOnMouseClicked(mouseEvent -> onAdd());

        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());
    }

    /**
     * Validates the data that the user entered, adds the sub-item to the database and the stock item, and then closes
     * the screen
     *
     * If some of the data fails to validate, then an error dialog is displayed telling the user what the problem is
     */
    protected void onAdd() {
        String displayId;
        float amount;
        String location;
        try {
            //validate data
            displayId = getDisplayId();
            amount = getAmount();
            location = getLocation();
        } catch (IllegalArgumentException e) {
            //display error dialog
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        SubItem subItem = new SubItem(displayId, amount, location);

        //save sub-item to database and add it to the stock item
        databaseManager.addSubItem(subItem, stockItemId);

        parentController.update();

        //close the screen
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * close the screen without saving the sub-item
     */
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Gets the display id from the display id input field and validates that it is not blank
     * @return The display id for this sub-item
     * @throws IllegalArgumentException If the display id is null or blank
     */
    protected String getDisplayId() throws IllegalArgumentException {
        String displayId = displayIdInput.getText();
        if (displayId == null || displayId.isBlank()) {
            throw new IllegalArgumentException("Display Id must be specified");
        }
        return displayId;
    }

    /**
     * gets the stock level of the sub-item from the amount input field and validates that it is a valid, positive
     * floating point number
     * @return The stock level of this sub-item
     * @throws IllegalArgumentException If the entered value is blank, not a floating point number, or is below zero
     */
    protected float getAmount() throws IllegalArgumentException {
        String amountString = amountInput.getText();
        if (amountString == null || amountString.isBlank()) {
            throw new IllegalArgumentException("Amount must be specified");
        }
        float amount;
        try {
            amount = Float.parseFloat(amountString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a decimal number");
        }
        if (amount < 0.0) {
            throw new IllegalArgumentException("Amount must be greater than or equal to 0");
        }
        return amount;
    }

    /**
     * Gets the location from the location input field and validates that it is not blank
     * @return The location of the sub-item
     * @throws IllegalArgumentException If the entered value is null or blank
     */
    protected String getLocation() throws IllegalArgumentException {
        String location = locationInput.getText();

        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location must be specified");
        }
        return location;
    }
}
