package uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid;

import javafx.fxml.FXML;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;

/**
 * A smaller grid item that does nothing when clicked.
 *
 * Designed for displaying items on item detail pages where the grid item's smaller size works better.
 * @author Damion Wilson
 * @version 1.0
 */
public class SmallGridItemController extends GridItemController{

    /**
     * Basic constructor that passes variables to super constructor
     * @param displayItem The DisplayItem that this grid item is displaying
     */
    public SmallGridItemController(DisplayItem displayItem) {
        super(displayItem);
    }

    /**
     * initialise the UI elements
     */
    @FXML
    @Override
    public void initialize() {
        super.initialize();
        stackPane.setMaxSize(150.0, 150.0);
        itemImage.setFitHeight(150.0);
        itemImage.setFitWidth(150.0);
        itemName.setMaxWidth(150.0);
        itemName.setPrefWidth(150.0);
        itemName.getStyleClass().clear();
        //set css style manually as style classes from css file are not working
        itemName.setStyle("-fx-font-size: 16; -fx-background-color: #FFFFFF; -fx-text-alignment: center;");
    }

    /**
     * Empty on-click method as this grid item is not meant to do anything when clicked
     */
    @Override
    protected void onClick() {}
}
