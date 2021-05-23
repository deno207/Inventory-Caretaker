package uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor;

import uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid.GridItemController;

/**
 * DisplayItem defines database items that can be displayed on the UI
 *
 * These items are main used in grid items which display several items on the main screens
 *
 * @author Damion Wilson
 * @version 1.0
 * @see GridItemController
 */
public interface DisplayItem {

    /**
     * returns the name of the item
     * @return The item's name
     */
    public String getName();

    /**
     * returns the path to the image this item uses
     *
     * Image paths are relative to the applications working directory
     * @return The path to the item's image
     */
    public String getImagePath();

    /**
     * returns the type of this item
     *
     * the returned type is from the ItemType Enum and is usually used to determine item specific behaviour in the UI
     * controllers
     * @return the type of the item
     * @see ItemType
     */
    public ItemType getItemType();
}
