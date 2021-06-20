package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;

import static org.junit.jupiter.api.Assertions.*;

public class SupplerTest {
    private Supplier testSupplier;

    private static final String NAME = "supplier";
    private static final String DESCRIPTION = "supplier description";
    private static final String IMAGE_NAME = "supplier.png";

    private static final String NEW_NAME = "new supplier";
    private static final String NEW_DESCRIPTION = "new supplier description";
    private static final String NEW_IMAGE_NAME = "new_supplier.png";
    private static final String NEW_WEBSITE = "www.new-website.co.uk";
    private static final String NEW_PHONE_NUMBER = "08824 257264";

    private static final String FULL_IMAGE_PATH = "images/supplier/" + IMAGE_NAME;
    private static final String UPDATED_IMAGE_PATH = "images/supplier/" + NEW_IMAGE_NAME;

    @BeforeEach
    public void setUp() {
        testSupplier = new Supplier(NAME, DESCRIPTION, IMAGE_NAME);
    }

    @Test
    public void getNameGetsCurrentName() {
        assertEquals(NAME, testSupplier.getName());
    }

    @Test
    public void getDescriptionGetsCurrentDescription() {
        assertEquals(DESCRIPTION, testSupplier.getDescription());
    }

    @Test
    public void getImageNameGetsCurrentImageName() {
        assertEquals(IMAGE_NAME, testSupplier.getImageName());
    }

    @Test
    public void websiteStartsAsNull() {
        assertNull(testSupplier.getWebsite());
    }

    @Test
    public void PhoneNumberStartsAsNull() {
        assertNull(testSupplier.getPhoneNumber());
    }

    @Test
    public void AddressStartsAsNull() {
        assertNull(testSupplier.getAddress());
    }

    @Test
    public void getImagePathGetsFullImagePath() {
        assertEquals(FULL_IMAGE_PATH, testSupplier.getImagePath());
    }

    @Test
    public void getItemTypeReturnsTheSupplierType() {
        assertEquals(ItemType.SUPPLIER, testSupplier.getItemType());
    }

    @Test
    public void setNameChangesCurrentName() {
        testSupplier.setName(NEW_NAME);

        assertEquals(NEW_NAME, testSupplier.getName());
    }

    @Test
    public void setDescriptionChangesCurrentDescription() {
        testSupplier.setDescription(NEW_DESCRIPTION);

        assertEquals(NEW_DESCRIPTION, testSupplier.getDescription());
    }

    @Test
    public void setImageNameChangesCurrentImageName() {
        testSupplier.setImageName(NEW_IMAGE_NAME);

        assertEquals(NEW_IMAGE_NAME, testSupplier.getImageName());
    }

    @Test
    public void setWebsiteChangesCurrentWebsite() {
        testSupplier.setWebsite(NEW_WEBSITE);

        assertEquals(NEW_WEBSITE, testSupplier.getWebsite());
    }

    @Test
    public void setPhoneNumberChangesCurrentPhoneNumber() {
        testSupplier.setPhoneNumber(NEW_PHONE_NUMBER);

        assertEquals(NEW_PHONE_NUMBER, testSupplier.getPhoneNumber());
    }

    @Test
    public void setAddressChangesCurrentAddress() {
        Address address = new Address("line one", "lone two", "city", "county", "country", "postcode");
        testSupplier.setAddress(address);

        assertEquals(address, testSupplier.getAddress());
    }

    @Test
    public void setImageNameUpdatesFullImagePath() {
        testSupplier.setImageName(NEW_IMAGE_NAME);

        assertEquals(UPDATED_IMAGE_PATH, testSupplier.getImagePath());
    }
}
