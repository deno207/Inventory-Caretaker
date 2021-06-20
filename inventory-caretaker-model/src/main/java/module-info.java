module inventory.caretaker.model {
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires sqlite.jdbc;
    requires sqlite.dialect;

    opens com.github.deno207.inventory.caretaker.model.entity;

    exports com.github.deno207.inventory.caretaker.model.entity;
    exports com.github.deno207.inventory.caretaker.model.ui.adaptor;
}