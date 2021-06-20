package com.github.deno207.inventory.caretaker.view.controller.supplier;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Address;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Supplier;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;

/**
 * SupplierDetailController controls the screen that shows the supplier's details
 * @author Damion Wilson
 * @version 1.1
 */
public class SupplierDetailController extends BaseController implements UpdateController {
    @FXML private Label supplierName;
    @FXML private Button editButton;
    @FXML private Button closeButton;
    @FXML private ImageView supplierImage;
    @FXML private Label description;
    @FXML private Label website;
    @FXML private Label phoneNumber;
    @FXML private Label addressLineOne;
    @FXML private Label addressLineTwo;
    @FXML private Label city;
    @FXML private Label county;
    @FXML private Label country;
    @FXML private Label postcode;

    private Supplier supplier;
    private final ImageProcessor imageProcessor;
    private final UpdateController parentController;

    /**
     * Basic Constructor that initialise variables
     * @param supplier The supplier whose details will be displayed
     */
    public SupplierDetailController(Supplier supplier, UpdateController parentController) {
        this.supplier = supplier;
        imageProcessor = new ImageProcessor();
        this.parentController = parentController;
    }

    /**
     * initialise UI elements, add on-click methods to buttons, and populate UI with supplier data
     */
    @FXML
    public void initialize() {
        editButton.setOnMouseClicked(mouseEvent -> onEdit());
        closeButton.setOnMouseClicked(mouseEvent -> onClose());

        bindSupplier();
    }

    private void bindSupplier() {
        supplierName.setText(supplier.getName());
        supplierImage.setImage(imageProcessor.getItemImage(supplier));
        description.setText(supplier.getDescription());

        website.setText(supplier.getWebsite());
        phoneNumber.setText(supplier.getPhoneNumber());
        Address address = supplier.getAddress();
        if (address != null) {
            addressLineOne.setText(address.getLineOne());
            addressLineTwo.setText(address.getLineTwo());
            city.setText(address.getCity());
            county.setText(address.getCounty());
            country.setText(address.getCountry());
            postcode.setText(address.getPostCode());
        }
    }

    /**
     * opens the edit supplier screen so that the supplier details can be edited
     */
    private void onEdit() {
        Pane pane = loadLayout("/layout/add_supplier.fxml", new EditSupplierController(this, supplier));
        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * closes the screen
     */
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * updates the data on the UI and then informs the parent controller of the update
     */
    @Override
    public void update() {
        supplier = databaseManager.reloadSupplier(supplier);
        bindSupplier();
        parentController.update();
    }
}
