package uk.ac.aber.cs39440.inventory.caretaker.intergration.test;

import javafx.geometry.VerticalDirection;
import javafx.scene.control.Label;
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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import uk.ac.aber.cs39440.inventory.caretaker.application.Application;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.DatabaseManager;
import uk.ac.aber.cs39440.inventory.caretaker.data.processor.TestDatabaseManager;

import java.sql.DriverManager;

public class EditItemIT extends ApplicationTest {
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
        dataSet = readDataSet("/database-data-sets/edit-sets/base-dataset.xml");
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

        application = (Application) FxToolkit.setupApplication(Application.class);
        FxToolkit.showStage();
    }

    @Test
    public void editItemNameDescriptionAndLowStockAlert() throws Exception{
        openEditItemScreen();

        clickOn("#nameInput").eraseText(5).write("Strawberry");
        clickOn("#descriptionInput").eraseText(35).write("A Red Berry");
        clickOn("#lowStockInput").eraseText(5).write("20");

        clickSaveItemButton();

        assertDatabaseState("/database-data-sets/edit-sets/edited-dataset.xml");

        Assertions.assertThat(lookup("#itemName").queryAs(Label.class)).hasText("Strawberry");
        Assertions.assertThat(lookup("#description").queryAs(Label.class)).hasText("A Red Berry");
        Assertions.assertThat(lookup("#lowStockLevel").queryAs(Label.class)).hasText("20.0");
    }

    @Test
    public void removeSupplierAndCategoryFromItem() throws Exception {
        openEditItemScreen();

        clickOn("Fruit Inc");
        clickOn("Fruit");

        clickSaveItemButton();

        assertDatabaseState("/database-data-sets/edit-sets/edited-dataset-no-category-or-supplier.xml");
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

    private void openEditItemScreen() {
        clickOn("Lemon");

        clickOn("#editButton");
    }

    private IDataSet readDataSet(String filePath) throws Exception{
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        return builder.build(getClass().getResource(filePath));
    }

    private void assertDatabaseState(String expectedDatasetPath) throws Exception {
        IDataSet expectedDataSet = readDataSet(expectedDatasetPath);

        IDataSet actualDataSet = databaseConnection.createDataSet();

        Assertion.assertEquals(expectedDataSet, actualDataSet);
    }

    private void clickSaveItemButton() {
        scroll(20, VerticalDirection.DOWN);
        clickOn("#addItemButton");
    }
}
