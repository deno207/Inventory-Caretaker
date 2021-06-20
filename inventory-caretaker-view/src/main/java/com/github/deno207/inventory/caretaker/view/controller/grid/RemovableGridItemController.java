package com.github.deno207.inventory.caretaker.view.controller.grid;

import com.github.deno207.inventory.caretaker.view.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.RemovableSelectionController;

/**
 * RemovableGridItemController controls a grid item that sends removeSelection messages to its parent controller
 * @author Damion Wilson
 * @version 1.1
 */
public class RemovableGridItemController extends SmallGridItemController{
    private final RemovableSelectionController parentController;

    /**
     * Basic constructor that initialises variables
     * @param displayItem The item to be displayed
     * @param parentController The controller to receive the removeSelection message
     */
    public RemovableGridItemController(DisplayItem displayItem, RemovableSelectionController parentController) {
        super(displayItem);
        this.parentController = parentController;
    }

    /**
     * send the removeSelection message to the parent controller
     */
    @Override
    protected void onClick() {
        parentController.removeSelection(displayItem);
    }
}
