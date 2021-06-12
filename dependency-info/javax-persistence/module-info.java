module java.persistence {
    requires java.logging;

    requires transitive java.instrument;
    requires transitive java.sql;

    exports javax.persistence;
    exports javax.persistence.criteria;
    exports javax.persistence.metamodel;
    exports javax.persistence.spi;
    
    uses javax.persistence.spi.PersistenceProvider;
}
