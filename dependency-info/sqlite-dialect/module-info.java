module sqlite.dialect {
    requires java.sql;
    requires org.jboss.logging;

    requires transitive org.hibernate.orm.core;

    exports org.sqlite.hibernate.dialect;
    exports org.sqlite.hibernate.dialect.identity;

    provides org.hibernate.boot.spi.MetadataBuilderInitializer with
        org.sqlite.hibernate.dialect.SQLiteMetadataBuilderInitializer;

}
