package uk.ac.aber.cs39440.inventory.caretaker.intergration.test;

import javafx.geometry.VerticalDirection;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import uk.ac.aber.cs39440.inventory.caretaker.application.Application;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.DatabaseManager;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.TestDatabaseManager;

import java.sql.DriverManager;

public class AddNewItemIT extends ApplicationTest {
    private static IDatabaseConnection databaseConnection;

    private Application application;
    private IDataSet dataSet;

    @BeforeAll
    public static void setUpEnvironment() throws Exception {
        //get hibernate to create the database if it does yet exist
        new DatabaseManager();

        //set up database connection so test data can be entered and validated
        databaseConnection = new DatabaseConnection(DriverManager.getConnection("jdbc:sqlite:test.db", "sa", ""));
        databaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);

        FxToolkit.registerPrimaryStage();
    }

    @BeforeEach
    public void setUp() throws Exception{
        dataSet = readDataSet("/database-data-sets/base-sets/base-dataset.xml");
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

        application = (Application) FxToolkit.setupApplication(Application.class);
        FxToolkit.showStage();
    }

    @Test
    public void addNewItem() throws Exception{
        fillInNewItemFormWithBasicInformation();

        clickAddNewItemButton();

        assertDatabaseState("/database-data-sets/base-sets/base-dataset-plus-stock-item.xml");
    }

    @Test
    public void addNewItemWithCategoryAndSupplier() throws Exception{
        fillInNewItemFormWithBasicInformation();

        addSupplierAndCategoryInformation();

        clickAddNewItemButton();

        assertDatabaseState("/database-data-sets/base-sets/base-dataset-plus-connected-stock-item.xml");
    }

    private void addSupplierAndCategoryInformation() {
        clickOn("#addSupplierButton");
        clickOn("Fruit Inc");

        clickOn("#addCategoryButton");
        clickOn("Fruit");
    }

    @Test
    public void addNewItemWithSubItems() throws Exception{
        fillInNewItemFormWithBasicInformation();

        addSubItemInformation();

        clickAddNewItemButton();

        assertDatabaseState("/database-data-sets/base-sets/base-dataset-plus-stock-item-with-subitem.xml");
    }

    private void addSubItemInformation() {
        clickOn("#hasSubItemInput");
        clickOn("#singularNameInput").write("Pallet");
        clickOn("#pluralNameInput").write("Pallets");
    }

    @Test
    public void addNewItemWithCategoryAndSupplierAndSubItem() throws Exception {
        fillInNewItemFormWithBasicInformation();

        addSubItemInformation();

        addSupplierAndCategoryInformation();

        clickAddNewItemButton();

        assertDatabaseState("/database-data-sets/base-sets/base-dataset-plus-connected-stock-item-with-subitem.xml");
    }

    @AfterEach
    public void cleanUp() throws Exception {
        DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);

        TestDatabaseManager.resetDatabaseManager();
        FxToolkit.cleanupApplication(application);
    }

    @AfterAll
    static void tearDown() throws Exception {
        databaseConnection.close();

        FxToolkit.cleanupStages();
    }

    private IDataSet readDataSet(String filePath) throws Exception{
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        return builder.build(getClass().getResource(filePath));
    }

    private void fillInNewItemFormWithBasicInformation() {
        clickOn("#addNewItemButton");

        clickOn("#nameInput").write("Apple");
        clickOn("#descriptionInput").write("A red or green round fruit that is grown on trees");
        clickOn("#lowStockInput").write("10");
        clickOn("#measurementTypeInput").clickOn("Amount");
    }

    private void assertDatabaseState(String expectedDatasetPath) throws Exception {
        IDataSet expectedDataSet = readDataSet(expectedDatasetPath);

        IDataSet actualDataSet = databaseConnection.createDataSet();

        Assertion.assertEquals(expectedDataSet, actualDataSet);
    }

    private void clickAddNewItemButton() {
        scroll(20, VerticalDirection.DOWN);
        clickOn("#addItemButton");
    }
}