package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Category;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.MeasurementType;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Supplier;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * EditItemController controls the screen that allows users to edit a stock item
 * @author Damion Wilson
 * @version 1.1
 */
public class EditItemController extends AddItemController {

    private final StockItem stockItem;

    /**
     * Basic constructor that initialises variables
     * @param parentController The controller that will receive an update notification when the changes are saved
     * @param stockItem The stock item that is being edited
     */
    public EditItemController(UpdateController parentController, StockItem stockItem) {
        super(parentController);
        this.stockItem = stockItem;
        String imagePath = stockItem.getImagePath();
        if (imagePath != null) {
            selectedImageFile = new File(stockItem.getImagePath());
        } else {
            selectedImageFile = null;
        }
    }

    /**
     * initialises the UI elements, add on-click methods to buttons and populates the UI with the stock item's current
     * data
     */
    @Override
    public void initialize(){
        super.initialize();

        titleLabel.setText("Edit Item");

        nameInput.setText(stockItem.getName());

        descriptionInput.setText(stockItem.getDescription());

        imageLocationLabel.setText(stockItem.getImageName());
        imagePreview.setImage(new ImageProcessor().getItemImage(stockItem));

        lowStockInput.setText(Float.toString(stockItem.getLowStock()));

        measurementTypeInput.getSelectionModel().select(stockItem.getMeasurementType());

        hasSubItemInput.selectedProperty().set(stockItem.hasSubItem());
        singularNameInput.setText(stockItem.getSubItemSingular());
        pluralNameInput.setText(stockItem.getSubItemPlural());

        Category category = stockItem.getCategory();
        if (category != null) {
            addCategory(category);
        }

        List<Supplier> suppliers = stockItem.getSuppliers();
        if (suppliers != null && suppliers.size() >= 1) {
            for (Supplier supplier: suppliers) {
                addSupplier(supplier);
            }
        }

        addItemButton.setText("Save");
    }

    /**
     * validates the data entered by the user, saves the changes to the data base, and then closes the screen
     *
     * If some of the data fails to validate, then an error dialog is displayed to tell the user what the problem is
     */
    @Override
    protected void onAddItem() {
        String name;
        String description;
        float lowStockAlert;
        MeasurementType measurementType;
        boolean hasSubItem;
        String singular;
        String plural;
        try {
            //validate the entered data
            name = getName();

            description = descriptionInput.getText();

            lowStockAlert = getLowStockAlert();

            measurementType = getMeasurementType();

            hasSubItem = hasSubItemInput.isSelected();

            singular = getSubItemSingular();

            plural = getSubItemPlural();
        } catch (IllegalArgumentException e) {
            //display error message
            displayErrorDialog("Invalid Form Entry", e);
            e.printStackTrace();
            return;
        }

        try {
            //save changes
            databaseManager.updateStockItem(name, description, lowStockAlert, measurementType, hasSubItem,
                    singular, plural, category, suppliers, stockItem.getId(), selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error Saving Image", e);
        }

        parentController.update();

        //close the screen
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }
}
