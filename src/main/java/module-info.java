module inventory.caretaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires sqlite.dialect;
    requires sqlite.jdbc;
    requires java.sql;

    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller.supplier to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller.category to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller.grid to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller.stock to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.data.entity to org.hibernate.orm.core, javafx.base;
    opens css;
    opens images.system;
    opens images.supplier;
    opens images.category;
    opens images.stock;
    opens sql;

    exports uk.ac.aber.cs39440.inventory.caretaker.application;
    exports uk.ac.aber.cs39440.inventory.caretaker.data.processor to inventory.caretaker.view;
    exports uk.ac.aber.cs39440.inventory.caretaker.data.entity to inventory.caretaker.view;
}