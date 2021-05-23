package uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor;

/**
 * Defines a controller as the receiver of a selection choice.
 *
 * @author Damion Wilson
 * @version 1.0
 */
public interface SelectionController {

    /**
     * Add the selection result to the controller
     * @param displayItem The display item that was selected
     */
    public void addSelection(DisplayItem displayItem);
}
