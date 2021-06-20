module inventory.caretaker.view {
    requires javafx.fxml;
    requires javafx.controls;
    requires inventory.caretaker.model;

    exports com.github.deno207.inventory.caretaker.view.adaptor to inventory.caretaker.model;

    opens com.github.deno207.inventory.caretaker.view.controller to javafx.fxml;
    opens com.github.deno207.inventory.caretaker.view.controller.grid to javafx.fxml;
    opens com.github.deno207.inventory.caretaker.view.controller.supplier to javafx.fxml;
    opens com.github.deno207.inventory.caretaker.view.controller.stock to javafx.fxml;
    opens com.github.deno207.inventory.caretaker.view.controller.category to javafx.fxml;

    opens css;
    opens images.system;
    opens images.supplier;
    opens images.category;
    opens images.stock;
}