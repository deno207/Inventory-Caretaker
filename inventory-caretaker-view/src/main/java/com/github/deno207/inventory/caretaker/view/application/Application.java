package com.github.deno207.inventory.caretaker.view.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.github.deno207.inventory.caretaker.view.controller.InventoryController;

public class Application extends javafx.application.Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/layout/inventory.fxml"));
        fxmlLoader.setController(new InventoryController());
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 960);
        stage.setScene(scene);
        stage.show();
    }
}
