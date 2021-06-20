package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubItemTest {
    private SubItem testSubItem;

    private static final String DISPLAY_ID = "1";
    private static final float AMOUNT = 5.7f;
    private static final String LOCATION = "upstairs";

    private static final String NEW_DISPLAY_ID = "4";
    private static final float NEW_AMOUNT = 12.3f;
    private static final String NEW_LOCATION = "down-stairs";
    private static final float ASSERT_DELTA = 0.1f;

    @BeforeEach
    public void setup() {
        testSubItem = new SubItem(DISPLAY_ID, AMOUNT, LOCATION);
    }

    @Test
    public void getDisplayIdGetsCurrentDisplayId() {
        assertEquals(DISPLAY_ID, testSubItem.getDisplayId());
    }

    @Test
    public void getAmountGetsCurrentAmount() {
        assertEquals(AMOUNT, testSubItem.getAmount(), ASSERT_DELTA);
    }

    @Test
    public void getLocationGetsCurrentLocation() {
        assertEquals(LOCATION, testSubItem.getLocation());
    }

    @Test
    public void setDisplayIdChangesCurrentDisplayId() {
        testSubItem.setDisplayId(NEW_DISPLAY_ID);

        assertEquals(NEW_DISPLAY_ID, testSubItem.getDisplayId());
    }

    @Test
    public void setAmountChangesCurrentAmount() {
        testSubItem.setAmount(NEW_AMOUNT);

        assertEquals(NEW_AMOUNT, testSubItem.getAmount(), ASSERT_DELTA);
    }

    @Test
    public void setLocationChangesCurrentLocation() {
        testSubItem.setLocation(NEW_LOCATION);

        assertEquals(NEW_LOCATION, testSubItem.getLocation());
    }

}
