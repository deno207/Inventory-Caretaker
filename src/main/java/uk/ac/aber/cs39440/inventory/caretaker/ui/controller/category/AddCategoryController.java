package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.category;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Category;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.RemovableSelectionController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.UpdateController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.BaseController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemFactory;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemType;

import java.io.File;
import java.io.IOException;

/**
 * The AddCategoryController controls the UI screen responsible for add new Categories to the database.
 * @author Damion Wilson
 * @version 1.1
 */
public class AddCategoryController extends BaseController implements RemovableSelectionController {
    @FXML protected Label title;
    @FXML protected TextField nameInput;
    @FXML protected TextArea descriptionInput;
    @FXML protected Label imagePathLabel;
    @FXML protected Button imageBrowserButton;
    @FXML protected Button addItemButton;
    @FXML protected ImageView imagePreview;
    @FXML private Button cancelButton;
    @FXML private Button addParentButton;
    @FXML private TilePane parentDisplay;

    protected Category parent;
    protected File selectedImageFile;
    protected ImageProcessor imageProcessor;
    protected UpdateController parentController;

    /**
     * Default Constructor which initialises variables
     * @param parentController The controller that needs to be notified of data updates when this controller finishes
     *                         its actions
     */
    public AddCategoryController(UpdateController parentController) {
        this.parentController = parentController;
        imageProcessor = new ImageProcessor();
    }

    /**
     * initializes UI items and sets Button call-back methods
     */
    @FXML
    public void initialize() {
        title.setText("Add Category");

        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());

        addItemButton.setOnMouseClicked(mouseEvent -> onAddItem());

        addParentButton.setOnMouseClicked(mouseEvent -> onAddParent());

        imageBrowserButton.setOnMouseClicked(mouseEvent -> onSelectImage());
    }

    /**
     * Adds the selected category as the parent of category being made and adds a grid item to the UI to represent that
     * it has been selected
     * @param displayItem The display item that was selected
     * @throws IllegalArgumentException if displayItem is not a Category
     */
    @Override
    public void addSelection(DisplayItem displayItem) {
        if (displayItem.getItemType() != ItemType.CATEGORY) {
            throw new IllegalArgumentException("Only categories are accepted as parents");
        }
        parent = (Category) displayItem;

        GridItemController gridItemController = GridItemFactory.getGridItemController(parent, this,
                GridItemType.REMOVABLE);
        Pane pane = loadLayout("/layout/grid_item.fxml", gridItemController);
        parentDisplay.getChildren().clear();
        parentDisplay.getChildren().add(pane);
    }

    /**
     * Removes the selected Category as the parent of the category being made, and removes it's grid item from the UI
     * @param displayItem The DisplayItem to be removed from the selection
     * @throws IllegalArgumentException if displayItem is not a Category
     */
    @Override
    public void removeSelection(DisplayItem displayItem) {
        if (displayItem.getItemType() != ItemType.CATEGORY) {
            throw new IllegalArgumentException("Only categories can be removed");
        }

        parent = null;

        parentDisplay.getChildren().clear();
    }

    /**
     * Closes the add category screen without saving the new category
     */
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Launches the select category screen so that the User can select a parent category
     */
    private void onAddParent() {
        Pane pane = loadLayout("/layout/selection.fxml", new SelectCategoryController(this));

        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates the data entered by the User, saves the category if the data is valid, and then closes the add category
     * screen. The selected image is also saved to the image cache at this point
     *
     * If the entered data is not valid, then a dialog box is shown telling the user what the problem was
     */
    protected void onAddItem() {
        String name;
        String description;

        try {
            name = getName();
            description = descriptionInput.getText();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setDescription(description);
        if (parent != null) {
            newCategory.setParent(parent);
        }

        try {
            databaseManager.addCategory(newCategory, selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error saving message", e);
        }

        parentController.update();

        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Launches a FileChooser so that the user can select an image file to represent this category in the system, then if
     * the user selected an image, the image is put in the ImageView and the file cached
     */
    protected void onSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Category Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(imageBrowserButton.getScene().getWindow());

        if (file != null) {
            Image image = imageProcessor.loadExternalImage(file);
            if (image != null) {
                selectedImageFile = file;
                imagePreview.setImage(image);
                imagePathLabel.setText(file.getPath());
            }
        }
    }

    /**
     * gets the name from the name input field and validates that it is a non-blank string
     * @return The categories name
     * @throws IllegalArgumentException if the entered name is null or blank
     */
    protected String getName() {
        String name = nameInput.getText();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must be specified");
        }

        return name;
    }
}
