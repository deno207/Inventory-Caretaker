package com.github.deno207.inventory.caretaker.view.controller.grid;

import javafx.fxml.FXML;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

/**
 * This Grid item represents a stock item which has a stock level lower the its low stock alert level.
 *
 * This is represented by setting the label's background to red and the text colour to white.
 *
 * As this grid item only deals with low stock items, It only accepts StockItems instead of DisplayItems.
 * @author Damion Wilson
 * @version 1.0
 */
public class LowStockGridItemController extends GridItemController{

    /**
     * Basic constructor that initialises variables
     * @param stockItem The stock item with low stock
     * @param parentController The controller that wants to receive update notifications
     */
    public LowStockGridItemController(StockItem stockItem, UpdateController parentController) {
        super(stockItem, parentController);
    }

    /**
     * initialise UI elements and sets the correct background colour and text colour
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

        itemName.getStyleClass().clear();
        itemName.getStyleClass().add("low-stock");
    }
}
