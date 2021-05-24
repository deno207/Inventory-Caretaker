module Inventory.Caretaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    opens uk.ac.aber.cs39440.inventory.caretaker.ui.controller to javafx.fxml;
    opens uk.ac.aber.cs39440.inventory.caretaker.data.entity to org.hibernate.orm.core;

    exports uk.ac.aber.cs39440.inventory.caretaker.application;
}