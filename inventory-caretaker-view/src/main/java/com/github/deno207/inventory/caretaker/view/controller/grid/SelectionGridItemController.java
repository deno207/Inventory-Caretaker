package com.github.deno207.inventory.caretaker.view.controller.grid;

import com.github.deno207.inventory.caretaker.model.ui.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.SelectionController;

/**
 * SelectionController controls a grid item that sends a addSelection message to a parent controller
 * @author Damion Wilson
 * @version 1.1
 */
public class SelectionGridItemController extends GridItemController{
    private final SelectionController parentController;

    /**
     * Basic constructor that initialises the variables
     * @param displayItem The item to be displayed
     * @param parentController The controller that will receive the addSelection message
     */
    public SelectionGridItemController(DisplayItem displayItem, SelectionController parentController) {
        super(displayItem);
        this.parentController = parentController;
    }

    /**
     * sends the addSelection message to the parent controller
     */
    @Override
    protected void onClick() {
        parentController.addSelection(displayItem);
    }
}
