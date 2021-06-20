package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class StockItemTest {
    private StockItem testItem;

    private static final String NAME = "Jam";
    private static final String DESCRIPTION = "Strawberry Jam";
    private static final String IMAGE_NAME = "Jam.png";
    private static final float LOW_STOCK = 5.5f;
    private static final MeasurementType MEASUREMENT_TYPE = MeasurementType.VOLUME;
    private static final boolean HAS_NO_SUB_ITEM = false;
    private static final float ASSERT_DELTA = 0.1f;

    private static final String NEW_NAME = "Bread";
    private static final String NEW_DESCRIPTION = "A piece of bread";
    private static final String NEW_IMAGE_NAME = "bread.png";
    private static final float NEW_LOW_STOCK = 10.1f;
    private static final MeasurementType NEW_MEASUREMENT_TYPE = MeasurementType.AMOUNT;
    private static final boolean HAS_SUB_ITEM = true;
    private static final float NEW_CURRENT_STOCK = 6.3f;
    private static final String NEW_SUB_ITEM_PLURAL = "Loafs";
    private static final String NEW_SUB_ITEM_SINGULAR = "Loaf";

    private static final String FULL_IMAGE_PATH = "images/stock/" + IMAGE_NAME;
    public static final String UPDATED_IMAGE_PATH = "images/stock/" + NEW_IMAGE_NAME;

    @BeforeEach
    public void setup() {
        testItem = new StockItem(NAME, DESCRIPTION, IMAGE_NAME, LOW_STOCK, MEASUREMENT_TYPE, HAS_NO_SUB_ITEM);
    }

    @Test
    public void getNameGetsCurrentNameTest() {
        assertEquals(NAME, testItem.getName());
    }

    @Test
    public void getDescriptionGetsCurrentDescription() {
        assertEquals(DESCRIPTION, testItem.getDescription());
    }

    @Test
    public void getImageNameGetsCurrentImageName() {
        assertEquals(IMAGE_NAME, testItem.getImageName());
    }

    @Test
    public void getLowStockGetsCurrentLowStock() {
        assertEquals(LOW_STOCK, testItem.getLowStock(), ASSERT_DELTA);
    }

    @Test
    public void getMeasurementTypeGetsCurrentMeasurementType() {
        assertEquals(MEASUREMENT_TYPE, testItem.getMeasurementType());
    }

    @Test
    public void getHasSubItemGetsCurrentHasSubItems() {
        assertFalse(testItem.hasSubItem());
    }

    @Test
    public void currentStockStartsAsZero() {
        assertEquals(0.0f, testItem.getCurrentStock(), ASSERT_DELTA);
    }

    @Test
    public void categoryStartsAsNull() {
        assertNull(testItem.getCategory());
    }

    @Test
    public void supplierListStartsEmpty() {
        assertNotNull(testItem.getSuppliers());
        assertEquals(0, testItem.getSuppliers().size());
    }

    @Test
    public void subItemListStartsEmpty() {
        assertNotNull(testItem.getSubItems());
        assertEquals(0, testItem.getSubItems().size());
    }

    @Test
    public void subItemSingularStartsAsNull() {
        assertNull(testItem.getSubItemSingular());
    }

    @Test
    public void subItemPluralStartsAsNull() {
        assertNull(testItem.getSubItemPlural());
    }

    @Test
    public void getImagePathGetsTheFullImagePath() {
        assertEquals(FULL_IMAGE_PATH, testItem.getImagePath());
    }

    @Test
    public void getItemTypeReturnsTheStockItemType() {
        assertEquals(ItemType.STOCK, testItem.getItemType());
    }

    @Test
    public void setNameChangesCurrentName() {
        testItem.setName(NEW_NAME);

        assertEquals(NEW_NAME, testItem.getName());
    }

    @Test
    public void setDescriptionChangesCurrentDescription() {
        testItem.setDescription(NEW_DESCRIPTION);

        assertEquals(NEW_DESCRIPTION, testItem.getDescription());
    }

    @Test
    public void setImageNameChangesCurrentImageName() {
        testItem.setImageName(NEW_IMAGE_NAME);

        assertEquals(NEW_IMAGE_NAME, testItem.getImageName());
    }

    @Test
    public void setLowStockChangesCurrentLowStock() {
        testItem.setLowStock(NEW_LOW_STOCK);

        assertEquals(NEW_LOW_STOCK, testItem.getLowStock(), ASSERT_DELTA);
    }

    @Test
    public void setMeasurementTypeChangesCurrentMeasurementType() {
        testItem.setMeasurementType(NEW_MEASUREMENT_TYPE);

        assertEquals(NEW_MEASUREMENT_TYPE, testItem.getMeasurementType());
    }

    @Test
    public void setHasSubItemChangesCurrentHasSubItem() {
        testItem.setHasSubItem(HAS_SUB_ITEM);

        assertTrue(testItem.hasSubItem());
    }

    @Test
    public void setCurrentStockChangesCurrentStockLevel() {
        testItem.setCurrentStock(NEW_CURRENT_STOCK);

        assertEquals(NEW_CURRENT_STOCK, testItem.getCurrentStock(), ASSERT_DELTA);
    }

    @Test
    public void setCategoryChangesCurrentCategory() {
        Category category = new Category("Category", "new category", "category.png");

        testItem.setCategory(category);

        assertEquals(category, testItem.getCategory());
    }

    @Test
    public void setSupplierListChangesCurrentSupplierList() {
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(new Supplier("Supplier", "a supplier", "supplier.png"));

        testItem.setSuppliers(supplierList);

        assertEquals(supplierList, testItem.getSuppliers());
    }

    @Test
    public void setSubItemListChangesCurrentSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("sub item", 10, "upstairs"));

        testItem.setSubItems(subItemList);

        assertEquals(subItemList, testItem.getSubItems());
    }

    @Test
    public void setSubItemPluralChangesCurrentSubItemPlural() {
        testItem.setSubItemPlural(NEW_SUB_ITEM_PLURAL);

        assertEquals(NEW_SUB_ITEM_PLURAL, testItem.getSubItemPlural());
    }

    @Test
    public void setSubItemSingularChangesCurrentSubItemSingular() {
        testItem.setSubItemSingular(NEW_SUB_ITEM_SINGULAR);

        assertEquals(NEW_SUB_ITEM_SINGULAR, testItem.getSubItemSingular());
    }

    @Test
    public void setImageNameAlsoUpdatesFullImagePath() {
        testItem.setImageName(NEW_IMAGE_NAME);

        assertEquals(UPDATED_IMAGE_PATH, testItem.getImagePath());
    }

    @Test
    public void setNameAsNullDoesNotWork() {
        testItem.setName(null);

        assertEquals(NAME, testItem.getName());
    }

    @Test
    public void setImageNameAsNullDoesNotWork() {
        testItem.setName(null);

        assertEquals(IMAGE_NAME, testItem.getImageName());
    }

    @Test
    public void setMeasurementTypeAsNullDoesNotWork() {
        testItem.setMeasurementType(null);

        assertEquals(MEASUREMENT_TYPE, testItem.getMeasurementType());
    }

    @Test
    public void setLowStockLowerThanZeroDoesNotWork() {
        testItem.setLowStock(-1.0f);

        assertEquals(LOW_STOCK, testItem.getLowStock());
    }

    @Test
    public void setCurrentStockLowerThanZeroDoesNotWork() {
        testItem.setCurrentStock(-1.0f);

        assertEquals(0.0f, testItem.getCurrentStock());
    }

    @Test
    public void setSubItemSingularAsNullDoesNotWork() {
        testItem.setSubItemSingular(NEW_SUB_ITEM_SINGULAR);
        testItem.setSubItemSingular(null);

        assertEquals(NEW_SUB_ITEM_SINGULAR, testItem.getSubItemSingular());
    }

    @Test
    public void setSubItemPluralAsNullDoesNotWork() {
        testItem.setSubItemPlural(NEW_SUB_ITEM_PLURAL);
        testItem.setSubItemPlural(null);

        assertEquals(NEW_SUB_ITEM_PLURAL, testItem.getSubItemPlural());
    }

    @Test
    public void setSupplierListAsNullDoesNotWork() {
        testItem.setSuppliers(null);

        assertEquals(new ArrayList<Supplier>(), testItem.getSuppliers());
    }
}
