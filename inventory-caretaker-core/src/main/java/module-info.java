module inventory.caretaker.core {
    requires inventory.caretaker.model;
    requires inventory.caretaker.view;
    requires org.hibernate.orm.core;
    requires sqlite.jdbc;
    requires sqlite.dialect;
    requires java.persistence;
    requires java.sql;

    exports com.github.deno207.inventory.caretaker.core.processor;
}