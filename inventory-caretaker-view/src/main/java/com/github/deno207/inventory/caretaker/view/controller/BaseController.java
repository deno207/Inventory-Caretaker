package com.github.deno207.inventory.caretaker.view.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.DatabaseManager;

import java.io.IOException;

/**
 * Basic super class to all of the controller classes.
 *
 * Provides a single data type that all controllers belong to and helper methods to reduce code reuse
 * @author Damion Wilson
 * @version 1.0
 */
public abstract class BaseController {

    protected DatabaseManager databaseManager;

    /**
     * Creates the database manager instance for this controller
     */
    protected BaseController() {
        databaseManager = new DatabaseManager();
    }

    /**
     * loads a fxml layout file and returns the loaded pane
     *
     * If the layout file fails to load, then an error is logged the application will exit
     * @param layoutFile The resource path to the required layout file
     * @param controller The controller that will manage the loaded screen
     * @return A Pane containing the loaded layout file
     */
    protected Pane loadLayout(String layoutFile, BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(layoutFile));
        fxmlLoader.setController(controller);
        Pane pane = null;

        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Error while loading layout file: " + layoutFile);
            e.printStackTrace();
            Platform.exit();
        }

        return pane;
    }

    /**
     * displays an error dialog with the provided title and the provided exception's message as the contents
     * @param title The title of the error dialog
     * @param error The exception containing the error message to be displayed
     */
    protected void displayErrorDialog(String title, IllegalArgumentException error) {
        displayErrorDialog(title, error.getMessage());
    }

    /**
     * displays an error dialog with the provided title and the provided exception's message as the contents.
     *
     * Also prints the stack trace for the exception
     * @param title The title of the error dialog
     * @param error The exception containing the error message to be displayed
     */
    protected void displayErrorDialog(String title, IOException error) {
        displayErrorDialog(title, error.getMessage());
        error.printStackTrace();
    }

    /**
     * displays an error dialog with the provided title and the provided message as the contents
     * @param title The title of the error dialog
     * @param message error message to be displayed
     */
    protected void displayErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
