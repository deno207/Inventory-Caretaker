package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.SubItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

/**
 * EditSubItemController controls the screen that allows the user to edit sub-items
 * @author Damion Wilson
 * @version 1.0
 */
public class EditSubItemController extends AddSubItemController{

    private final SubItem subItem;

    /**
     * Basic constructor that initialises variables
     * @param subItem The sub-item being edited
     * @param stockItemId The database id of the stock this sub-item belongs to
     * @param parentController The controller that will want to receive update notifications when the sub-item changes
     *                         are saved
     */
    public EditSubItemController(SubItem subItem, int stockItemId, UpdateController parentController) {
        super(stockItemId, parentController);
        this.subItem = subItem;
    }

    /**
     * initialises the UI elements, adds on-click methods to the buttons, and populates the UI with the current sub-item
     * data
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

        title.setText("Edt Sub-Item");
        addButton.setText("Save");

        displayIdInput.setText(subItem.getDisplayId());
        amountInput.setText(Float.toString(subItem.getAmount()));
        locationInput.setText(subItem.getLocation());
    }

    /**
     * validates the entered data, saves the changes to the database, and closes the screen
     *
     * If some of the data fails to validate, then an error dialog is displayed to the user to tell them what the
     * problem is
     */
    @Override
    public void onAdd() {
        String displayId;
        float amount;
        String location;
        try {
            displayId = getDisplayId();
            amount = getAmount();
            location = getLocation();
        } catch (IllegalArgumentException e) {
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        databaseManager.updateSubItem(displayId, location, amount, subItem.getId());

        parentController.update();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
