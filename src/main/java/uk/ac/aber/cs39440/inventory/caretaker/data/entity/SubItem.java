package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Sub-items represent logical sub divisions of stock items
 *
 * Examples of such divisions include pallets of fruit or rolls of fabric.
 * Sub-items allow these divisions to tracked separately instead of as a single stock item
 *
 * Each sub-item has a display Id which can be used to identify that specific sub-item and differentiate it from the
 * other sub-items. They also track there own location, allowing you to keep track of where they are even if they are
 * stored in different places. Lastly they track there own stock level, which can be edited individually from other
 * sub-items.
 *
 * @author Damion Wilson
 * @version 1.0
 * @see StockItem
 */
@Entity
public class SubItem {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String displayId;
    private float amount;
    private String location;

    /**
     * default constructor
     */
    public SubItem(){
        this(null, 0.0f, null);
    }

    /**
     * standard constructor which initialises variables to the provided values
     * @param displayId The sub-item's display Id
     * @param amount The amount of stock contained in this sub-item
     * @param location The location of this sub-item in your storage set-up
     */
    public SubItem(String displayId, float amount, String location) {
        this.displayId = displayId;
        this.amount = amount;
        this.location = location;
    }

    /**
     * returns the database Id of this sub-item object.
     * This value is only set after the sub-item has been stored in the database
     * @return The database Id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the database id for this sub-item object
     * The Id is auto-generated when the sub-item object is put into the database and errors will occur if you set this
     * value manually or change it after has been set.
     * @param id the new database Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the display Id of this sub-item
     * @return The current display Id
     */
    public String getDisplayId() {
        return displayId;
    }

    /**
     * sets the display Id to the specified value
     * @param displayId the new display Id
     */
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    /**
     * the current stock level of this sub-item
     * @return The amount of stock this sub-item has
     */
    public float getAmount() {
        return amount;
    }

    /**
     * sets the current stock level of this sub-item
     * @param amount The new stock level of this sub-item
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * returns the sub-items current location
     * @return The sub-items current location
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the current location to the specified value
     * @param location the new location of this sub-item
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
