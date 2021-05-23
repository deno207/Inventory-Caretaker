package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    private Address testAddress;

    private static final String LINE_ONE = "first line";
    private static final String LINE_TWO = "second line";
    private static final String CITY = "city";
    private static final String COUNTY = "county";
    private static final String COUNTRY = "country";
    private static final String POSTCODE = "postcode";

    private static final String NEW_LINE_ONE = "new first line";
    private static final String NEW_LINE_TWO = "new second line";
    private static final String NEW_CITY = "new city";
    private static final String NEW_COUNTY = "new county";
    private static final String NEW_COUNTRY = "new country";
    private static final String NEW_POSTCODE = "new postcode";

    @BeforeEach
    public void setup() {
        testAddress = new Address(LINE_ONE, LINE_TWO, CITY, COUNTY, COUNTRY, POSTCODE);
    }

    @Test
    public void getLineOneGetsCurrentLineOne() {
        assertEquals(LINE_ONE, testAddress.getLineOne());
    }

    @Test
    public void getLineTwoGetsCurrentLineTwo() {
        assertEquals(LINE_TWO, testAddress.getLineTwo());
    }

    @Test
    public void getCityGetsCurrentCity() {
        assertEquals(CITY, testAddress.getCity());
    }

    @Test
    public void getCountyGetsCurrentCounty() {
        assertEquals(COUNTY, testAddress.getCounty());
    }

    @Test
    public void getCountryGetsCurrentCountry() {
        assertEquals(COUNTRY, testAddress.getCountry());
    }

    @Test
    public void getPostCodeGetsCurrentPostCode() {
        assertEquals(POSTCODE, testAddress.getPostCode());
    }

    @Test
    public void setLineOneChangesCurrentLineOne() {
        testAddress.setLineOne(NEW_LINE_ONE);

        assertEquals(NEW_LINE_ONE, testAddress.getLineOne());
    }

    @Test
    public void setLineTwoChangesCurrentLineTwo() {
        testAddress.setLineTwo(NEW_LINE_TWO);

        assertEquals(NEW_LINE_TWO, testAddress.getLineTwo());
    }

    @Test
    public void setCityChangesCurrentCity() {
        testAddress.setCity(NEW_CITY);

        assertEquals(NEW_CITY, testAddress.getCity());
    }

    @Test
    public void setCountyChangesCurrentCounty() {
        testAddress.setCounty(NEW_COUNTY);

        assertEquals(NEW_COUNTY, testAddress.getCounty());
    }

    @Test
    public void setCountryChangesCurrentCountry() {
        testAddress.setCountry(NEW_COUNTRY);

        assertEquals(NEW_COUNTRY, testAddress.getCountry());
    }

    @Test
    public void setPostcodeChangesCurrentPostCode() {
        testAddress.setPostCode(NEW_POSTCODE);

        assertEquals(NEW_POSTCODE, testAddress.getPostCode());
    }
}
