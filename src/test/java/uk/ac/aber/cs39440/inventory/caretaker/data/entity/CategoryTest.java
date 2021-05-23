package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CategoryTest {
    private Category testCategory;

    private static final String NAME = "Colours";
    private static final String DESCRIPTION = "all of the colours";
    private static final String IMAGE_NAME = "colours.png";

    private static final String NEW_NAME = "fruits";
    private static final String NEW_DESCRIPTION = "all of the fruits";
    private static final String NEW_IMAGE_NAME = "fruit.png";

    private static final String FULL_IMAGE_PATH = "images/category/" + IMAGE_NAME;
    private static final String UPDATED_IMAGE_PATH = "images/category/" + NEW_IMAGE_NAME;

    @BeforeEach
    public void setup() {
        testCategory = new Category(NAME, DESCRIPTION, IMAGE_NAME);
    }

    @Test
    public void getNameGetsCurrentName() {
        assertEquals(NAME, testCategory.getName());
    }

    @Test
    public void getDescriptionGetsCurrentDescription() {
        assertEquals(DESCRIPTION, testCategory.getDescription());
    }

    @Test
    public void getImageNameGetsCurrentImageName() {
        assertEquals(IMAGE_NAME, testCategory.getImageName());
    }

    @Test
    public void parentCategoryStartsNull() {
        assertNull(testCategory.getParent());
    }

    @Test
    public void getImagePathGetsFullPathToTheImage() {
        assertEquals(FULL_IMAGE_PATH, testCategory.getImagePath());
    }

    @Test
    public void getItemTypeReturnsTheCategoryType() {
        assertEquals(ItemType.CATEGORY, testCategory.getItemType());
    }

    @Test
    public void setNameChangesCurrentName() {
        testCategory.setName(NEW_NAME);

        assertEquals(NEW_NAME, testCategory.getName());
    }

    @Test
    public void setDescriptionChangesCurrentDescription() {
        testCategory.setDescription(NEW_DESCRIPTION);

        assertEquals(NEW_DESCRIPTION, testCategory.getDescription());
    }

    @Test
    public void setImageLocationChangesCurrentImageLocation() {
        testCategory.setImageName(NEW_IMAGE_NAME);

        assertEquals(NEW_IMAGE_NAME, testCategory.getImageName());
    }

    @Test
    public void setParentChangesCurrentParent() {
        Category parent = new Category("Plants", "all plants", "plant.png");

        testCategory.setParent(parent);

        assertEquals(parent, testCategory.getParent());
    }

    @Test
    public void setImageNameAlsoUpdatesTheFullImagePath() {
        testCategory.setImageName(NEW_IMAGE_NAME);

        assertEquals(UPDATED_IMAGE_PATH, testCategory.getImagePath());
    }
}
