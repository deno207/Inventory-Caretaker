package com.github.deno207.inventory.caretaker.model.entity;

/**
 * An Enum which represents the different ways you can measure a stock item.
 *
 * The values are:
 * - Mass: to indicate that an item should be measured in KiloGrams.
 * - Volume: to indicate that an item should be measured in Litres.
 * - Length: to indicate that an item should be measured in Meters.
 * - Amount: to indicate that there is not a unit of measurement for this item.
 *
 * @author Damion Wilson
 * @version 1.1
 * @see StockItem
 */
public enum MeasurementType {
    MASS("Kg"), VOLUME("L"), LENGTH("M"), AMOUNT("");

    private final String unitString;

    /**
     * Constructor that sets the unit string for this measurement type
     * @param measurementUnit The unit string for this measurement type
     */
    MeasurementType(String measurementUnit) {
        unitString = measurementUnit;
    }

    /**
     * returns the current enum as a capitalised String
     * @return the enum value's name as a capitalised String
     */
    @Override
    public String toString() {
        String newString = super.toString();
        newString = newString.toLowerCase();
        newString = newString.substring(0, 1).toUpperCase() + newString.substring(1);
        return newString;
    }

    /**
     * returns the measurement unit for this measurement type as a string
     * @return The measurement unit for this measurement type
     */
    public String getUnitString() {
        return unitString;
    }
}
