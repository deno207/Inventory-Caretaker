package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.entity.Category;
import com.github.deno207.inventory.caretaker.model.entity.MeasurementType;
import com.github.deno207.inventory.caretaker.model.entity.StockItem;
import com.github.deno207.inventory.caretaker.model.entity.Supplier;
import com.github.deno207.inventory.caretaker.view.image.ImageProcessor
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.RemovableSelectionController;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;
import com.github.deno207.inventory.caretaker.view.controller.category.SelectCategoryController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemFactory;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemType;
import com.github.deno207.inventory.caretaker.view.controller.supplier.SelectSupplierController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * AddItemController controls the screen which allows users to add StockItems to the database
 * @author Damion Wilson
 * @version 1.2
 */
public class AddItemController extends BaseController implements RemovableSelectionController {

    @FXML protected Label titleLabel;
    @FXML protected TextField nameInput;
    @FXML protected TextArea descriptionInput;
    @FXML protected Label imageLocationLabel;
    @FXML protected ImageView imagePreview;
    @FXML private Button imageBrowserButton;
    @FXML protected TextField lowStockInput;
    @FXML protected ComboBox<MeasurementType> measurementTypeInput;
    @FXML protected CheckBox hasSubItemInput;
    @FXML private Label singularNameLabel;
    @FXML protected TextField singularNameInput;
    @FXML private Label pluralNameLabel;
    @FXML protected TextField pluralNameInput;
    @FXML private Button addSupplierButton;
    @FXML private TilePane supplierTilePane;
    @FXML private Button addCategoryButton;
    @FXML private TilePane categoryTilePane;
    @FXML protected Button addItemButton;
    @FXML private Button cancelButton;

    protected List<Supplier> suppliers;
    protected Category category;
    protected UpdateController parentController;
    protected ImageProcessor imageProcessor;
    protected File selectedImageFile;

    /**
     * Basic constructor which initialises variables
     * @param parentController The controller that will receive an update notification when the stock item is added to
     *                         the database
     */
    public AddItemController(UpdateController parentController) {
        this.parentController = parentController;
        suppliers = new ArrayList<>();
        imageProcessor = new ImageProcessor();
    }

    /**
     * initialises UI elements and add on-click methods to buttons
     */
    @FXML
    public void initialize() {
        titleLabel.setText("Add Item");

        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());

        addSupplierButton.setOnMouseClicked(mouseEvent -> onAddSupplier());

        addCategoryButton.setOnMouseClicked(mouseEvent -> onAddCategory());

        addItemButton.setOnMouseClicked(mouseEvent -> onAddItem());

        imageBrowserButton.setOnMouseClicked(mouseEvent -> onChooseImage());

        hasSubItemInput.selectedProperty().addListener(changeListener -> {
            if (hasSubItemInput.isSelected()) {
                singularNameInput.setDisable(false);
                singularNameLabel.setDisable(false);
                pluralNameInput.setDisable(false);
                pluralNameLabel.setDisable(false);
            } else {
                singularNameInput.setDisable(true);
                singularNameLabel.setDisable(true);
                pluralNameInput.setDisable(true);
                pluralNameLabel.setDisable(true);
            }
        });

        measurementTypeInput.getItems().addAll(MeasurementType.values());
    }

    /**
     * closes the screen without saving the stock item
     */
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * launches the select suppliers screen so that the user can add a supplier to this stock item
     */
    private void onAddSupplier() {
        launchSelectionScreen(new SelectSupplierController(this));
    }

    /**
     * launches the select category screen so that the user can add a category to this stock item
     */
    private void onAddCategory() {
        launchSelectionScreen(new SelectCategoryController(this));
    }

    /**
     * launches one of the selection screens depending on which controller is provided
     * @param selectionController Either a SelectCategoryController or a SelectSupplierController
     */
    private void launchSelectionScreen(BaseController selectionController) {
        Pane root = loadLayout("/layout/selection.fxml", selectionController);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Opens a file chooser so that the user can select an image file to associate with this stock item
     *
     * If an image is chosen, then it is loaded in to the ImageView and the file is cached for later
     */
    private void onChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        fileChooser.setTitle("Choose Stock Image");
        File file = fileChooser.showOpenDialog(imageBrowserButton.getScene().getWindow());

        if (file != null) {
            Image image = imageProcessor.loadExternalImage(file);
            if (image != null) {
                selectedImageFile = file;
                imagePreview.setImage(image);
                imageLocationLabel.setText(file.getPath());
            }
        }
    }

    /**
     * Validates the data the user entered, saves the StockItem to the database, and then closes the screen
     *
     * If the data fails to validate, then an error dialog is shown telling the user what the problem is
     */
    protected void onAddItem() {
        StockItem newStockItem;

        try {
            //validate entered data
            String name = getName();
            String description = descriptionInput.getText();
            float lowStockAlert = getLowStockAlert();
            MeasurementType measurementType = getMeasurementType();
            boolean hasSubItem = hasSubItemInput.isSelected();

            newStockItem = new StockItem();
            newStockItem.setName(name);
            newStockItem.setDescription(description);
            newStockItem.setLowStock(lowStockAlert);
            newStockItem.setMeasurementType(measurementType);
            newStockItem.setHasSubItem(hasSubItem);

            if (hasSubItem) {
                newStockItem.setSubItemSingular(getSubItemSingular());
                newStockItem.setSubItemPlural(getSubItemPlural());
            }
        } catch (IllegalArgumentException e) {
            //display error message to the user
            displayErrorDialog("Invalid Form Entry", e);
            e.printStackTrace();
            return;
        }

        if (category != null) {
            newStockItem.setCategory(category);
        }

        if (!suppliers.isEmpty()) {
            newStockItem.setSuppliers(suppliers);
        }

        //save StockItem
        try {
            databaseManager.addStockItem(newStockItem, selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error saving image", e);
        }

        parentController.update();

        //close the screen
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }

    /**
     * gets the name from the name input field and validates that it is not blank
     * @return The stock item's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    protected String getName() throws IllegalArgumentException{
        String name = nameInput.getText();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The Name field can not be left blank");
        }
        return name;
    }

    /**
     * gets the low stock level from the low stock level input field and validates that it is a valid positive floating
     * point number
     * @return The low stock alert level
     * @throws IllegalArgumentException If the entered value is blank, is not a floating point number, or is below zero
     */
    protected float getLowStockAlert() throws IllegalArgumentException {
        float lowStockAlert;
        String lowStockText = lowStockInput.getText();

        if (lowStockText == null || lowStockText.isBlank()) {
            throw new IllegalArgumentException("Low Stock Alert Level must be specified");
        }

        try {
            lowStockAlert = Float.parseFloat(lowStockText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Low Stock Alert Level is not a valid number");
        }
        if (lowStockAlert < 0) {
            throw new IllegalArgumentException("Low Stock Alert Level can not be less than 0");
        }

        return lowStockAlert;
    }

    /**
     * get the measurement type from the measurement type input field and validates that a value was selected
     * @return The selected measurement type
     * @throws IllegalArgumentException If a measurement type was not selected
     */
    protected MeasurementType getMeasurementType() throws IllegalArgumentException {
        MeasurementType measurementType = measurementTypeInput.getValue();

        if (measurementType == null) {
            throw new IllegalArgumentException("A Measurement Type must be Selected");
        }

        return measurementType;
    }

    /**
     * gets the plural sub-item name from the plural name input and validates that it is not blank
     * @return The plural sub-item name
     * @throws IllegalArgumentException If the plural name is null or blank
     */
    protected String getSubItemPlural() throws IllegalArgumentException {
        String plural = pluralNameInput.getText();

        if (plural == null || plural.isBlank()) {
            throw new IllegalArgumentException("A plural name for the sub-items must be specified");
        }

        return plural;
    }

    /**
     * gets the singular sub-item name from the singular name input and validates that it is not blank
     * @return The singular sub-item name
     * @throws IllegalArgumentException if the singular name is null or blank
     */
    protected String getSubItemSingular() throws IllegalArgumentException {
        String singular = singularNameInput.getText();

        if (singular == null || singular.isBlank()) {
            throw new IllegalArgumentException("A singular name for the sub-items must be specified");
        }

        return singular;
    }

    /**
     * Adds the selected category or supplier to the current selection
     * @param displayItem The display item that was selected
     * @throws IllegalArgumentException If the selected item is not a Supplier or Category
     */
    @Override
    public void addSelection(DisplayItem displayItem) throws IllegalArgumentException {
        switch (displayItem.getItemType()) {
            case CATEGORY -> addCategory((Category) displayItem);
            case SUPPLIER -> addSupplier((Supplier) displayItem);
            default -> throw new IllegalArgumentException("Display item to be added has not a supplier or category: "
                    + displayItem);
        }
    }

    /**
     * Adds the selected supplier to the list of selected suppliers and adds it to the UI
     * @param supplier The selected Supplier
     */
    protected void addSupplier(Supplier supplier) {
        suppliers.add(supplier);

        Pane root;
        supplierTilePane.getChildren().clear();
        for (Supplier s: suppliers) {
            root = loadGridItem(s);
            supplierTilePane.getChildren().add(root);
        }
    }

    /**
     * Adds the selected Category as the StockItem's Category and displays it on the UI
     *
     * If a Category has already been selected, then the old category is replaced by the newly selected one
     * @param category The selected Category
     */
    protected void addCategory(Category category) {
        this.category = category;

        categoryTilePane.getChildren().clear();
        Pane root = loadGridItem(category);
        categoryTilePane.getChildren().add(root);
    }

    /**
     * Removes the selected category or supplier from the current selection
     * @param displayItem The DisplayItem to be removed from the selection
     * @throws IllegalArgumentException If the selected item is not Category or Supplier
     */
    @Override
    public void removeSelection(DisplayItem displayItem) throws IllegalArgumentException {
        switch (displayItem.getItemType()) {
            case CATEGORY -> removeCategory();
            case SUPPLIER -> removeSupplier((Supplier) displayItem);
            default -> throw new IllegalArgumentException("Display item to be added has not a supplier or category: "
                    + displayItem);
        }
    }

    /**
     * Removes the supplier from the list of selected Suppliers and removes it from the UI
     * @param supplier The Supplier to be removed from the list
     */
    private void removeSupplier(Supplier supplier) {
        suppliers.remove(supplier);

        supplierTilePane.getChildren().clear();
        Pane root = null;
        for (Supplier s : suppliers) {
            root = loadGridItem(s);
            supplierTilePane.getChildren().add(root);
        }
    }

    /**
     * Removes the category as the selected category and removes it from the UI
     */
    private void removeCategory() {
        category = null;

        categoryTilePane.getChildren().clear();
    }

    /**
     * Helper method for loading grid items that represent selected Suppliers and Categories
     * @param displayItem The Supplier or Category being represented by this grid item
     * @return A pane containing the configured grid item
     */
    private Pane loadGridItem(DisplayItem displayItem) {
        GridItemController controller = GridItemFactory.getGridItemController(displayItem, this,
                GridItemType.REMOVABLE);
        return loadLayout("/layout/grid_item.fxml", controller);
    }
}
