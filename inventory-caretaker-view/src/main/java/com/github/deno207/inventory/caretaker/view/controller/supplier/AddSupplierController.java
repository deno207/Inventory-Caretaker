package com.github.deno207.inventory.caretaker.view.controller.supplier;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.model.entity.Address;
import com.github.deno207.inventory.caretaker.model.entity.Supplier;
import com.github.deno207.inventory.caretaker.view.image.ImageProcessor
import com.github.deno207.inventory.caretaker.view.adaptor.UpdateController;
import com.github.deno207.inventory.caretaker.view.controller.BaseController;

import java.io.File;
import java.io.IOException;

/**
 * AddSupplierController controls the screen that allows user to add new suppliers to the system
 * @author Damion Wilson
 * @version 1.1
 */
public class AddSupplierController extends BaseController {
    @FXML protected Label titleLabel;
    @FXML protected Label imageLocationLabel;
    @FXML private Label addressLineOneLabel;
    @FXML private Label addressLineTwoLabel;
    @FXML private Label cityLabel;
    @FXML private Label countyLabel;
    @FXML private Label countryLabel;
    @FXML private Label postcodeLabel;
    @FXML protected TextField nameInput;
    @FXML protected TextField websiteInput;
    @FXML protected TextField phoneNumberInput;
    @FXML protected TextField addressLineOneInput;
    @FXML protected TextField addressLineTwoInput;
    @FXML protected TextField cityInput;
    @FXML protected TextField countyInput;
    @FXML protected TextField countryInput;
    @FXML protected TextField postcodeInput;
    @FXML protected TextArea descriptionInput;
    @FXML protected ImageView imagePreview;
    @FXML protected CheckBox addressCheckBox;
    @FXML protected Button addButton;
    @FXML private Button imageBrowserButton;
    @FXML private Button cancelButton;

    protected UpdateController parentController;
    protected File selectedImageFile;
    protected ImageProcessor imageProcessor;

    /**
     * basic constructor that initialise variables
     * @param parentController The controller that will receive an update message when the supplier is added to the
     *                         database
     */
    public AddSupplierController(UpdateController parentController) {
        this.parentController = parentController;
        imageProcessor = new ImageProcessor();
        selectedImageFile = null;
    }

    /**
     * initialises Ui elements and adds on-click methods to buttons
     */
    @FXML
    public void initialize() {
        titleLabel.setText("Add Supplier");

        addButton.setOnMouseClicked(mouseEvent -> onAddItem());
        imageBrowserButton.setOnMouseClicked(mouseEvent -> onSelectImage());
        cancelButton.setOnMouseClicked(mouseEvent -> onCancel());

        addressCheckBox.selectedProperty().addListener(changeListener -> {
            //if checkbox is set, disabled is false
            //if checkbox is not set, disabled is true
            boolean disabled = !addressCheckBox.isSelected();

            addressLineOneLabel.setDisable(disabled);
            addressLineOneInput.setDisable(disabled);
            addressLineTwoLabel.setDisable(disabled);
            addressLineTwoInput.setDisable(disabled);
            cityLabel.setDisable(disabled);
            cityInput.setDisable(disabled);
            countyLabel.setDisable(disabled);
            countyInput.setDisable(disabled);
            countryLabel.setDisable(disabled);
            countryInput.setDisable(disabled);
            postcodeLabel.setDisable(disabled);
            postcodeInput.setDisable(disabled);
        });
    }

    /**
     * Opens a file chooser so that the user can select an image file to associate with this supplier
     *
     * If an image is chosen, then it is loaded in to the ImageView and the file is cached for later
     */
    private void onSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        fileChooser.setTitle("Choose Supplier Image");
        File file = fileChooser.showOpenDialog(imageBrowserButton.getScene().getWindow());

        if (file != null) {
            Image image = imageProcessor.loadExternalImage(file);
            if (image != null) {
                selectedImageFile = file;
                imagePreview.setImage(image);
                imageLocationLabel.setText(file.getPath());
            }
        }
    }

    protected void onAddItem() {
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
            e.printStackTrace();
            return;
        }

        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setDescription(description);
        supplier.setPhoneNumber(phoneNumber);
        supplier.setWebsite(website);

        try {
            databaseManager.addSupplier(supplier, address, selectedImageFile);
        } catch (IOException e) {
            displayErrorDialog("Error saving message", e);
        }

        parentController.update();

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * closes the screen without saving the supplier
     */
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * gets the name from the name input field and validates that it is not blank
     * @return The supplier's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    protected String getName() throws IllegalArgumentException{
        String name = nameInput.getText();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The Name field can not be left blank");
        }
        return name;
    }

    /**
     * if the address check box is checked, then it will get the address data that was entered by the user and validate
     * that they have entered valid data
     * @return The entered address if the checkbox is checked, null otherwise
     * @throws IllegalArgumentException if any of the address data is not valid
     */
    protected Address getAddress() throws IllegalArgumentException{
        if (!addressCheckBox.isSelected()) {
            return null;
        }
        String addressLineOne = addressLineOneInput.getText();
        if (addressLineOne == null || addressLineOne.isBlank()) {
            throw new IllegalArgumentException("Line one of the address must be specified");
        }
        String addressLineTwq = addressLineTwoInput.getText();

        String city = cityInput.getText();
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("The city must be specified");
        }

        String county = countyInput.getText();
        if (county == null|| county.isBlank()) {
            throw new IllegalArgumentException("The county must be specified");
        }

        String country = countryInput.getText();
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("The country must be specified");
        }

        String postcode = postcodeInput.getText();
        if (postcode == null || postcode.isBlank()) {
            throw new IllegalArgumentException("The postcode must be specified");
        }
        return new Address(addressLineOne, addressLineTwq, city, county, country, postcode);
    }
}
