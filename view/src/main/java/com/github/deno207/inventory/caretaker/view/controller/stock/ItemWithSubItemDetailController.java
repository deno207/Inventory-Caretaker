package com.github.deno207.inventory.caretaker.view.controller.stock;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.StockItem;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.SubItem;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;

/**
 * ItemWithSubItemDetailController controls the stock item details screen when the stock item has sub-items to be
 * displayed
 * @author Damion Wilson
 * @version 1.0
 */
public class ItemWithSubItemDetailController extends ItemDisplayController {

    @FXML private Label subItemName;
    @FXML private Button addSubItemButton;
    @FXML private TableView<SubItem> subItemTable;

    /**
     * Basic constructor which initialises variables
     * @param stockItem The StockItem to be displayed
     * @param updateController The controller that will receive update notifications if the StockItem is changed
     */
    public ItemWithSubItemDetailController(StockItem stockItem, UpdateController updateController) {
        super(stockItem, updateController);
    }

    /**
     * initialises UI elements, add on-click methods to buttons, populate the UI with the stock item data, and enable
     * and configure the sub-item table used to display all of the sub-items
     */
    @FXML
    @Override
    public void initialize() {
        super.initialize();

        //remove buttons as stock level is decided by sub-items, not by an amount set by the user
        addStockButton.setVisible(false);
        removeStockButton.setVisible(false);

        subItemName.setVisible(true);

        addSubItemButton.setOnMouseClicked(mouseEvent -> onAddSubItem());
        addSubItemButton.setVisible(true);

        subItemTable.setVisible(true);
        TableColumn<SubItem, Button> editColumn = new TableColumn<>();
        editColumn.setCellValueFactory(new ButtonCellValueFactory(this, stockItem.getId()));
        editColumn.setText("Edit");

        subItemTable.getColumns().add(editColumn);
    }

    /**
     * populates the UI with all of the stock-items data, including all of the sub-items that the stock item has
     */
    @Override
    protected void bindStockItem() {
        super.bindStockItem();

        subItemName.setText(stockItem.getSubItemPlural());

        subItemTable.getItems().clear();
        subItemTable.getItems().addAll(stockItem.getSubItems());
    }

    /**
     * Opens the add sub-item screen so that a sub-item can be added to this stock item
     */
    private void onAddSubItem() {
        Pane pane = loadLayout("/layout/add_sub_item.fxml", new AddSubItemController(stockItem.getId(), this));
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * First, reloads the stock item data so that it has the most recent version. Then it checks if hasSubItems has changed.
     * If it has, then this screen is switched to the item display screen, otherwise it repopulates the UI
     * with the stock item's changes
     * Lastly it passes the update notification to the parent controller
     */
    @Override
    public void update() {
        stockItem = databaseManager.reloadStockItem(stockItem);

        if (stockItem.hasSubItem()) {
            bindStockItem();
        } else {
            Stage stage = (Stage) subItemName.getScene().getWindow();
            stage.setScene(loadDetailScene(new ItemDisplayController(stockItem, updateController)));
        }

        updateController.update();
    }

    /**
     * A cell factory that populates each cell with an edit sub-items button
     * @author Damion Wilson
     * @version 1.0
     */
    private class ButtonCellValueFactory implements Callback<TableColumn.CellDataFeatures<SubItem, Button>, ObservableValue<Button>> {

        private final UpdateController parentController;
        private final int stockItemId;

        /**
         * Basic constructor that initialises variables
         * @param parentController The controller to notify when sub-item has finished being edited
         * @param stockItemId The database if of stock item the sub-item belongs to
         */
        public ButtonCellValueFactory(UpdateController parentController, int stockItemId) {
            this.parentController = parentController;
            this.stockItemId = stockItemId;
        }

        /**
         * Creates the edit sub-item button and packs it into a property so that it can be displayed in the table view
         * cell
         * @param cellDataFeatures The data for the current row of the table
         * @return A Property containing the edit sub-item button
         */
        @Override
        public ObservableValue<Button> call(TableColumn.CellDataFeatures<SubItem, Button> cellDataFeatures) {
            Button button = new Button();
            Image image = new Image(getClass().getResourceAsStream("/images/system/edit-button.png"));
            ImageView buttonImage = new ImageView();
            buttonImage.setImage(image);
            buttonImage.setFitHeight(20.0);
            buttonImage.setFitWidth(20.0);
            SubItem subItem = cellDataFeatures.getValue();

            button.setGraphic(buttonImage);
            button.setOnMouseClicked(mouseEvent -> onClick(subItem));
            SimpleObjectProperty<Button> property = new SimpleObjectProperty<>();
            property.set(button);
            return  property;
        }

        /**
         * opens the edit sub-item screen so that the sub-item can be edited
         * @param subItem The sub-item to be edited
         */
        private void onClick(SubItem subItem) {
            BaseController controller = new EditSubItemController(subItem, stockItemId, parentController);
            Pane pane = loadLayout("/layout/add_sub_item.fxml", controller);

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
}
