package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Category;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Supplier;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemController;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemFactory;
import com.github.deno207.inventory.caretaker.view.controller.grid.GridItemType;

/**
 * ItemDisplayController controls the screen that allows users to see the details of a stock item
 * @author Damion Wilson
 * @version 1.1
 */
public class ItemDisplayController extends BaseController implements UpdateController {
    @FXML private Label itemName;
    @FXML private Label description;
    @FXML private Label category;
    @FXML private Label currentStockLevel;
    @FXML private Label lowStockLevel;
    @FXML private Button editButton;
    @FXML private Button closeButton;
    @FXML protected Button addStockButton;
    @FXML protected Button removeStockButton;
    @FXML private ImageView itemImage;
    @FXML private TilePane supplierList;

    protected StockItem stockItem;
    protected final UpdateController updateController;

    /**
     * Basic constructor that initialises variables
     * @param stockItem The stock to be displayed
     * @param updateController The controller that will receive update notifications if the stock details are changed
     */
    public ItemDisplayController(StockItem stockItem, UpdateController updateController) {
        this.stockItem = stockItem;
        this.updateController = updateController;
    }

    /**
     * Adds on-click methods to buttons and populates the UI with data
     */
    @FXML
    public void initialize() {
        editButton.setOnMouseClicked(mouseEvent -> onEditItem());
        closeButton.setOnMouseClicked(mouseEvent -> onCloseScreen());
        addStockButton.setOnMouseClicked(mouseEvent -> onAddStock());
        removeStockButton.setOnMouseClicked(mouseEvent -> onRemoveStock());

        bindStockItem();
    }

    /**
     * populates the UI with the StockItem's data
     */
    protected void bindStockItem() {
        itemName.setText(stockItem.getName());

        description.setText(stockItem.getDescription());

        Category itemCategory = stockItem.getCategory();
        if (itemCategory != null) {
            category.setText(itemCategory.getName());
        }

        currentStockLevel.setText(getStockString(stockItem.getCurrentStock()));
        lowStockLevel.setText(getStockString(stockItem.getLowStock()));

        itemImage.setImage(new ImageProcessor().getItemImage(stockItem));

        supplierList.getChildren().clear();
        for (Supplier supplier: stockItem.getSuppliers()) {
            GridItemController controller = GridItemFactory.getGridItemController(supplier, null,
                    GridItemType.SMALL_DISPLAY);
            Pane pane = loadLayout("/layout/grid_item.fxml", controller);
            supplierList.getChildren().add(pane);
        }
    }

    /**
     * returns the provided stock float as a string with the correct measurement type unit appended to it
     * @param amount the stock amount to be converted to a stock string
     * @return The stock amount as a string with the correct unit type added to the end
     */
    private String getStockString(float amount) {
        String amountString = Float.toString(amount);
        String measurementUnit = stockItem.getMeasurementType().getUnitString();
        return amountString + measurementUnit;
    }

    /**
     * opens the edit item screen so that the stock item can be edited
     */
    private void onEditItem() {
        Pane pane = loadLayout("/layout/add_item.fxml", new EditItemController(this, stockItem));

        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens the add stock screen so that the user can add to the item's stock level
     */
    private void onAddStock() {
        Pane pane = loadLayout("/layout/edit_stock_level.fxml", new AddStockController(stockItem, this));

        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * opens the remove stock screen so that the user can subtract stock from the item
     */
    private void onRemoveStock() {
        Pane pane = loadLayout("/layout/edit_stock_level.fxml", new RemoveStockController(stockItem, this));

        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * closes the details screen
     */
    private void onCloseScreen() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * First, reloads the stock item data so that it has the most recent version. Then it checks if hasSubItems has changed.
     * If it has, then this screen is switched to the item with sub-items detail screen, otherwise it repopulates the UI
     * with the stock item's changes
     * Lastly it passes the update notification to the parent controller
     */
    @Override
    public void update() {
        stockItem = databaseManager.reloadStockItem(stockItem);

        if (stockItem.hasSubItem()) {
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(loadDetailScene(new ItemWithSubItemDetailController(stockItem, updateController)));
        } else {
            bindStockItem();
        }

        updateController.update();
    }

    /**
     * Helper method to load the other stock item detail screen
     * @param controller Either a ItemDisplayController or a ItemWithSubItemDetailController
     * @return A Scene containing the new screen
     */
    protected Scene loadDetailScene(ItemDisplayController controller) {
        Pane pane = loadLayout("/layout/item_detail.fxml", controller);
        return new Scene(pane);
    }
}
