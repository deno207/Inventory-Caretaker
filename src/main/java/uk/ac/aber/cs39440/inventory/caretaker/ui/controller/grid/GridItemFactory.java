package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid;

import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.RemovableSelectionController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.SelectionController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.UpdateController;
import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.BaseController;

/**
 * A factory class for grid item controllers.
 * @author Damion Wilson
 * @version 1.0
 * @see GridItemType
 */
public class GridItemFactory {

    /**
     * returns the correct type of grid item controller based on the provided grid item type and the display item
     *
     * The returned grid item is mostly determined by the provided GridItemType, but certain grid item types will also
     * decided which controller to return based on some property of the provided displayItem
     * @param displayItem The item that will be displayed by the grid item
     * @param parentController The controller that will messages from the grid item
     * @param gridItemType The type of the required grid item
     * @throws IllegalArgumentException If the provided controller is not the correct kind of parent controller for the
     * required type of grid item controller
     * @return A grid item controller
     */
    public static GridItemController getGridItemController(DisplayItem displayItem, BaseController parentController,
                                                           GridItemType gridItemType) throws IllegalArgumentException {
        return switch (gridItemType) {
            case DISPLAY -> {
                //check parent controller is the correct type
                if (!(parentController instanceof UpdateController)) {
                    throw new IllegalArgumentException("The provided parent controller was not an Update controller");
                }
                // if the display item is a stock item and the current stock is below the low stock level
                // then return a Low stock grid item, otherwise return a normal grid item
                if (displayItem.getItemType() == ItemType.STOCK) {
                    StockItem stockItem = (StockItem) displayItem;
                    if (stockItem.getCurrentStock() < stockItem.getLowStock()) {
                        yield new LowStockGridItemController(stockItem, (UpdateController) parentController);
                    }
                }
                yield new GridItemController(displayItem, (UpdateController) parentController);
            }
            case REMOVABLE -> {
                //check parent controller is the correct type
                if (!(parentController instanceof RemovableSelectionController)) {
                    throw new IllegalArgumentException("The provided parent controller was not a removable selection" +
                            " controller");
                }
                yield new RemovableGridItemController(displayItem, (RemovableSelectionController) parentController);
            }
            case SELECTABLE -> {
                //check parent controller is the correct type
                if (!(parentController instanceof SelectionController)) {
                    throw new IllegalArgumentException("The provided parent controller was not a selection controller");
                }
                yield new SelectionGridItemController(displayItem, (SelectionController) parentController);
            }
            case SMALL_DISPLAY -> new SmallGridItemController(displayItem);
        };
    }
}
