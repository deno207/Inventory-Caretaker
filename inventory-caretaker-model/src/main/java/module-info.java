module inventory.caretaker.model {
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens com.github.deno207.inventory.caretaker.model.entity;

    exports com.github.deno207.inventory.caretaker.model.entity;
    exports com.github.deno207.inventory.caretaker.model.ui.adaptor;
}