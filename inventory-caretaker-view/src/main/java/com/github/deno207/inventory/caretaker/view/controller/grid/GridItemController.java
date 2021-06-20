package com.github.deno207.inventory.caretaker.view.controller.grid;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.entity.Category;
import com.github.deno207.inventory.caretaker.model.entity.StockItem;
import com.github.deno207.inventory.caretaker.model.entity.Supplier;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;
import com.github.deno207.inventory.caretaker.view.controller.category.CategoryDetailController;
import com.github.deno207.inventory.caretaker.view.controller.stock.ItemDisplayController;
import com.github.deno207.inventory.caretaker.view.controller.stock.ItemWithSubItemDetailController;
import com.github.deno207.inventory.caretaker.view.controller.supplier.SupplierDetailController;

/**
 * GridItemController control the grid items used to display DisplayItems in grid layouts.
 *
 * These Basic grid items are also used to launch the item detail screens for StockItems, Categories and Suppliers
 * @author Damion Wilson
 * @version 1.2
 */
public class GridItemController extends BaseController {
    @FXML
    protected StackPane stackPane;
    @FXML
    protected ImageView itemImage;
    @FXML
    protected Label itemName;
    protected DisplayItem displayItem;
    private UpdateController parentController;

    /**
     * Basic constructor which sets the display for this grid item
     * @param displayItem The item that is being displayed
     */
    public GridItemController(DisplayItem displayItem) {
        this.displayItem = displayItem;
    }

    /**
     * Basic constructor which sets the display item for this grid item and the parent controller that will want to
     * receive update
     * @param displayItem The item that is being displayed
     * @param parentController The controller that wants to receive update notifications
     */
    public GridItemController(DisplayItem displayItem, UpdateController parentController) {
        this.displayItem = displayItem;
        this.parentController = parentController;
    }

    /**
     * populates the UI element with the display item data and sets on-click method
     */
    @FXML
    public void initialize() {
        itemName.setText(displayItem.getName());

        //load item image or if item has no image, load default one
        itemImage.setImage(new ImageProcessor().getItemImage(displayItem));

        stackPane.setOnMouseClicked(mouseEvent -> onClick());
    }

    /**
     * Launches one of the item details screen.
     * The item detail screen that is launched depends on the type of the DisplayItem
     */
    protected void onClick() {
        Stage stage = new Stage();
        String layoutFile = null;
        BaseController controller = null;
        switch (displayItem.getItemType()) {
            case STOCK -> {
                StockItem item = (StockItem) displayItem;
                layoutFile = "/layout/item_detail.fxml";
                if (item.hasSubItem()) {
                    controller = new ItemWithSubItemDetailController(item, parentController);
                } else {
                    controller = new ItemDisplayController(item, parentController);
                }
            }
            case CATEGORY -> {
                Category item = (Category) displayItem;
                layoutFile = "/layout/category_detail.fxml";
                controller = new CategoryDetailController(item, parentController);
            }
            case SUPPLIER-> {
                Supplier item = (Supplier) displayItem;
                layoutFile = "/layout/supplier_detail.fxml";
                controller = new SupplierDetailController(item, parentController);
            }
        }
        Pane pane = loadLayout(layoutFile, controller);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
