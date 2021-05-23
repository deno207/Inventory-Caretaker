package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.category;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.SelectionController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.BaseController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemFactory;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemType;

import java.util.List;

/**
 * SelectCategoryController controls the UI screen responsible for allowing the user to select a category so
 * that it can be added to another item
 * @author Damion Wilson
 * @version 1.0
 */
public class SelectCategoryController extends BaseController implements SelectionController {
    private static final int MAX_COLUMN_NUMBER = 3;
    @FXML
    protected Label titleLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private GridPane displayItemGrid;
    private final SelectionController parentController;

    /**
     * Default constructor that initialise variables
     * @param parentController The controller that launched this screen and wants to know which Category the user
     *                         selected
     */
    public SelectCategoryController(SelectionController parentController) {
        this.parentController = parentController;
    }

    /**
     * initialises the UI elements, sets Button call-back methods, and populates the UI with data
     */
    @FXML
    public void initialize() {
        titleLabel.setText("Select Category");

        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());

        List<? extends DisplayItem> displayItemList = getData();

        int row = 0;
        int column = 0;
        GridItemController gridItemController;

        for (DisplayItem displayItem : displayItemList) {
             gridItemController= GridItemFactory.getGridItemController(displayItem, this,
                     GridItemType.SELECTABLE);
            Pane root = loadLayout("/layout/grid_item.fxml", gridItemController);

            displayItemGrid.add(root, column++, row);
            if (column >= MAX_COLUMN_NUMBER) {
                column = 0;
                row += 1;
            }
        }
    }

    /**
     * Defines the list of data that is to be displayed on the UI
     * @return A list of all of the categories
     */
    protected List<? extends DisplayItem> getData() {
        return databaseManager.getAllCategories();
    }

    /**
     * closes the select category screen which out selecting a Category
     */
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Passes the selected Category onto the parent controller and closes the screen
     * @param displayItem The display item that was selected
     */
    @Override
    public void addSelection(DisplayItem displayItem) {
        parentController.addSelection(displayItem);

        Stage stage = (Stage) displayItemGrid.getScene().getWindow();
        stage.close();
    }
}
