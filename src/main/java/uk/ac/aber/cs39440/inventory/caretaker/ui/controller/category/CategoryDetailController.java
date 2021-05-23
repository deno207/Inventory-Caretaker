package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.category;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Category;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.UpdateController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.BaseController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemFactory;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemType;

/**
 * CategoryDetailController controls the screen that displays the details of a category to the user
 * @author Damion
 * @version 1.1
 */
public class CategoryDetailController extends BaseController implements UpdateController {

    @FXML private Label categoryName;
    @FXML private Label description;
    @FXML private Button editButton;
    @FXML private Button closeButton;
    @FXML private ImageView categoryImage;
    @FXML private TilePane parentDisplay;

    private Category category;
    private final ImageProcessor imageProcessor;
    private final UpdateController parentController;

    /**
     * Basic constructor that initialises variables
     * @param category The Category whose details are to be displayed
     */
    public CategoryDetailController(Category category, UpdateController parentController) {
        this.category = category;
        imageProcessor = new ImageProcessor();
        this.parentController = parentController;
    }

    /**
     * initialise UI elements, add on-click methods to buttons, and populate UI with data
     */
    @FXML
    public void initialize() {
        editButton.setOnMouseClicked(mouseEvent -> onEdit());
        closeButton.setOnMouseClicked(mouseEvent -> onClose());

        bindCategory();
    }

    private void bindCategory() {
        categoryName.setText(category.getName());
        description.setText(category.getDescription());
        categoryImage.setImage(imageProcessor.getItemImage(category));


        parentDisplay.getChildren().clear();
        Category parentCategory = category.getParent();
        if (parentCategory != null) {
            GridItemController controller = GridItemFactory.getGridItemController(parentCategory, null,
                    GridItemType.SMALL_DISPLAY);
            Pane pane = loadLayout("/layout/grid_item.fxml", controller);
            parentDisplay.getChildren().add(pane);
        }
    }

    /**
     * Empty method as edit category screen doesn't exist yet
     */
    private void onEdit() {
        Pane pane = loadLayout("/layout/add_category.fxml", new EditCategoryController(category, this));
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * Closes the current screen
     */
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update() {
        category = databaseManager.reloadCategory(category);
        bindCategory();
        parentController.update();
    }
}
