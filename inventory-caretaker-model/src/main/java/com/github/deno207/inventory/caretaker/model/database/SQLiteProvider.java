package com.github.deno207.inventory.caretaker.model.database;

import org.hibernate.jpa.HibernatePersistenceProvider;
import com.github.deno207.inventory.caretaker.model.entity.Address;
import com.github.deno207.inventory.caretaker.model.entity.Category;
import com.github.deno207.inventory.caretaker.model.entity.StockItem;
import com.github.deno207.inventory.caretaker.model.entity.SubItem;
import com.github.deno207.inventory.caretaker.model.entity.Supplier;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLiteProvider implements PersistenceUnitInfo {

    private final Properties properties;
    private final List<String> managedClasses;

    public SQLiteProvider() {
        properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.driver", "org.sqlite.JDBC");
        properties.setProperty("javax.persistence.jdbc.url", "jdbc:sqlite:stock.db");
        properties.setProperty("javax.persistence.jdbc.user", "sa");
        properties.setProperty("javax.persistence.jdbc.password", "");
        properties.setProperty("hibernate.dialect", "org.sqlite.hibernate.dialect.SQLiteDialect");
        properties.setProperty("javax.persistence.schema-generation.database.action", "create");
        properties.setProperty("javax.persistence.schema-generation.create-source", "script");
        properties.setProperty("javax.persistence.schema-generation.create-script-source", "sql/create.sql");
        properties.setProperty("hibernate.show_sql", "true");

        managedClasses = new ArrayList<>();
        managedClasses.add(StockItem.class.getName());
        managedClasses.add(Supplier.class.getName());
        managedClasses.add(SubItem.class.getName());
        managedClasses.add(Address.class.getName());
        managedClasses.add(Category.class.getName());
    }
    @Override
    public String getPersistenceUnitName() {
        return "uk.ac.aber.cs39440.inventory.caretaker.sqlite.local";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return managedClasses;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
