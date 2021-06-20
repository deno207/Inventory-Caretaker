package com.github.deno207.inventory.caretaker.view.controller.category;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Category;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

import java.io.File;
import java.io.IOException;

/**
 * EditCategoryController controls the screen that allows users to edit an existing category
 * @author Damion Wilson
 * @version 1.1
 */
public class EditCategoryController extends AddCategoryController{

    private final Category category;

    /**
     * Default Constructor which initialises variables
     * @param category The category that is being edited
     * @param parentController The controller that needs to be notified of data updates when this controller finishes
     *                         its actions
     */
    public EditCategoryController(Category category, UpdateController parentController) {
        super(parentController);
        this.category = category;
        String imagePath = category.getImagePath();
        if (imagePath == null) {
            selectedImageFile = null;
        } else {
            selectedImageFile = new File(imagePath);
        }
    }

    /**
     * initialises Ui elements and populates UI with category data
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

        addItemButton.setText("Save");

        nameInput.setText(category.getName());
        descriptionInput.setText(category.getDescription());
        imagePreview.setImage(imageProcessor.getItemImage(category));
        imagePathLabel.setText(category.getImageName());

        Category parent = category.getParent();
        if (parent != null) {
            addSelection(parent);
        }
    }

    /**
     * retrieves and validates the entered data, saves the changes to the database, send the update notification to the
     * parent controller and then close the screen.
     *
     * If there is an error while validating the entered data, then an error dialog will be displayed telling the user
     * what the problem is.
     */
    @Override
    protected void onAddItem() {
        String name;
        String description;

        try {
            name = getName();
            description = descriptionInput.getText();
        } catch (IllegalArgumentException e) {
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        try {
            databaseManager.updateCategory(category.getId(), name, description, parent, selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error saving image", e);
        }

        parentController.update();

        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }
}
