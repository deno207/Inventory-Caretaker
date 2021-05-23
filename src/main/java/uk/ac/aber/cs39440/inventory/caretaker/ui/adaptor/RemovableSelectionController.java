package uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor;

/**
 * Defines controller as the receiver of selection choice and being receive an un-select command on the selected items
 *
 * @author Damion Wilson
 * @version 1.0
 */
public interface RemovableSelectionController extends SelectionController {

    /**
     * removes the provided DisplayItem from the selection
     * @param displayItem The DisplayItem to be removed from the selection
     */
    public void removeSelection(DisplayItem displayItem);
}
