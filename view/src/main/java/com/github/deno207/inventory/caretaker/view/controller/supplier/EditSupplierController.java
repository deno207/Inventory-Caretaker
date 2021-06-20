package com.github.deno207.inventory.caretaker.view.controller.supplier;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Address;
import uk.ac.aber.cs39440.inventory.caretaker.data.entity.Supplier;
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;

import java.io.File;
import java.io.IOException;

/**
 * EditSupplierController controls the screen that lets the user edit the details of a supplier
 * @author Damion Wilson
 * @version 1.1
 */
public class EditSupplierController extends AddSupplierController {

    private final Supplier supplier;

    /**
     * basic constructor that initialise variables
     *
     * @param supplier         The supplier that is being edited
     * @param parentController The controller that will receive an update message when the supplier is added to the
     *                         database
     */
    public EditSupplierController(UpdateController parentController, Supplier supplier) {
        super(parentController);
        this.supplier = supplier;
        String imagePath = supplier.getImagePath();
        if (imagePath == null) {
            selectedImageFile = null;
        } else {
            selectedImageFile = new File(imagePath);
        }
    }

    /**
     * Initialise the Ui elements and populates the UI with the supplier data
     */
    @Override
    @FXML
    public void initialize() {
        super.initialize();

        addButton.setText("Save");

        bindSupplier();
    }

    /**
     * populates the UI with the supplier data
     */
    private void bindSupplier() {
        nameInput.setText(supplier.getName());
        descriptionInput.setText(supplier.getDescription());
        imageLocationLabel.setText(supplier.getImagePath());
        imagePreview.setImage(imageProcessor.getItemImage(supplier));

        websiteInput.setText(supplier.getWebsite());
        phoneNumberInput.setText(supplier.getPhoneNumber());

        Address address = supplier.getAddress();
        if (address != null) {
            addressCheckBox.setSelected(true);
            addressLineOneInput.setText(address.getLineOne());
            addressLineTwoInput.setText(address.getLineTwo());
            cityInput.setText(address.getCity());
            countyInput.setText(address.getCounty());
            countryInput.setText(address.getCountry());
            postcodeInput.setText(address.getPostCode());
        }
    }

    /**
     * retrieves and validates the entered data, saves the changes to the database, send the update notification to the
     * parent controller and then close the screen.
     *
     * If there is an error while validating the entered data, then an error dialog will be displayed telling the user
     * what the problem is.
     */
    @Override
    public void onAddItem() {
        String name;
        String description;
        String website;
        String phoneNumber;
        Address address;
        try {
            name = getName();
            description = descriptionInput.getText();
            website = websiteInput.getText();
            phoneNumber = phoneNumberInput.getText();
            address = getAddress();
        } catch (IllegalArgumentException e) {
            displayErrorDialog("Invalid Form Entry", e);
            return;
        }

        try {
            databaseManager.updateSupplier(supplier.getId(), name, description, website, phoneNumber, address, selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error Saving Image", e);
        }

        parentController.update();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}