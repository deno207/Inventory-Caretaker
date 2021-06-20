package com.github.deno207.inventory.caretaker.model.entity;

import org.hibernate.annotations.GenericGenerator;
import com.github.deno207.inventory.caretaker.view.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.ItemType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a stock item.
 *
 * stock items represent all of the various items that the system can keep track off.
 * The stock level of the item can be measured in several different units, which are tracked by the item's measurement
 * type.
 * stock items can have sub items, which represent different containers or logical groupings of the item. An example of
 * this could be pallets of fruit or rolls of fabric. In these examples the pallets or rolls are tracked individually
 * as sub items of the stock item. Sub items are optional and are configured when the item is made or edited.
 *
 * As an implementor of DisplayItem, stock items can be displayed on the UI in grid items
 *
 * @author Damion Wilson
 * @version 1.2.1
 * @see uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor
 * @see DisplayItem
 * @see ItemType
 * @see MeasurementType
 * @see SubItem
 */
@Entity
public class StockItem implements DisplayItem {
    private static final String IMAGE_FOLDER = "images/stock/";
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String name;
    private String description;
    private String imageName;
    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "CATEGORY_ID_FK"))
    private Category category;
    private float currentStock;
    private float lowStock;
    private MeasurementType measurementType;
    private boolean hasSubItem;
    private String subItemSingular;
    private String subItemPlural;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubItem> subItems;
    @ManyToMany
    private List<Supplier> suppliers;

    /**
     * Default constructor that initialises all variables to null or 0
     */
    public StockItem(){
        this(null, null, null, 0.0f, null, false);
    }

    /**
     * Initialises variables to the provided values
     * @param name The name of the stock item
     * @param description The stock item's description
     * @param imageName The name of the image this item uses
     * @param lowStock The level at which the the stock for this item is considered low
     * @param measurementType How this item is measured
     * @param hasSubItem Whether this item has sub items or not
     */
    public StockItem(String name, String description, String imageName, float lowStock,
                     MeasurementType measurementType, boolean hasSubItem) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.category = null;
        this.currentStock = 0.0f;
        this.lowStock = lowStock;
        this.measurementType = measurementType;
        this.hasSubItem = hasSubItem;
        this.subItemSingular = null;
        this.subItemPlural = null;
        this.subItems = new ArrayList<>();
        this.suppliers = new ArrayList<>();
    }

    /**
     * returns the database Id of this stock item object.
     * This value is only set after the stock item has been stored in the database
     * @return The database Id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the database id for this stock item object
     * The Id is auto-generated when the stock item object is put into the database and errors will occur if you set this
     * value manually or change it after has been set.
     * @param id the new database Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the name of this stock item
     * @return The stock item's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * returns the file path to this item's image
     *
     * The image path references to an image in the applications image cache, which is managed by the ImageProcessor
     * @return The image path to the item's image or null if imageName is not set or is blank
     * @see uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor
     * @see DisplayItem
     */
    @Override
    public String getImagePath() {
        if (imageName == null || imageName.isBlank()) {
            return null;
        }
        return IMAGE_FOLDER + imageName;
    }

    /**
     * returns the STOCK item type
     *
     * This is mainly used to determine item specific behaviour in UI controllers,
     * which only handle DisplayItems instead of StockItems.
     * @return ItemType.STOCK
     * @see ItemType
     * @see DisplayItem
     */
    @Override
    public ItemType getItemType() {
        return ItemType.STOCK;
    }

    /**
     * sets the stock item's name to the provided value
     *
     * If the provided value is null, then the new value is ignored
     * @param name the new name of the stock item
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * returns the stock item's description
     * @return The stock item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the stock item to the provided value
     * @param description the new item description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the name of the image file which contains the item's image
     * @return the name of this items image file
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * sets the image name to the provided value
     *
     * If the new value is null, then the new value is ignored
     * @param imageName the name of the new image file
     */
    public void setImageName(String imageName) {
        if (imageName != null) {
            this.imageName = imageName;
        }
    }

    /**
     * returns the category that this stock item is a part of
     * @return This item's category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * sets the category of this stock item to the provided value
     * @param category the new category of this item
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * returns the current stock level of this item
     * @return The current stock level
     */
    public float getCurrentStock() {
        if (hasSubItem) {
            float amount = 0.0f;
            for (SubItem subItem : subItems) {
                amount += subItem.getAmount();
            }
            return amount;
        } else {
            return currentStock;
        }
    }

    /**
     * sets the current stock level of this item
     * @param currentStock the new stock level
     */
    public void setCurrentStock(float currentStock) {
        if (currentStock >= 0.0) {
            this.currentStock = currentStock;
        }
    }

    /**
     * returns the low stock alert level for this item
     * @return Low stock alert level
     */
    public float getLowStock() {
        return lowStock;
    }

    /**
     * sets the low stock alert level for this item
     *
     * if the new value is below 0.0, then the new value is ignored
     * @param lowStock the new low stock alert level
     */
    public void setLowStock(float lowStock) {
        if (lowStock >= 0.0) {
            this.lowStock = lowStock;
        }
    }

    /**
     * returns the current measurement type for this item
     * @return How this item is measured
     * @see MeasurementType
     */
    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    /**
     * sets the measurement type for this item to the provided value
     *
     * if the new value is null, then the new value is ignored
     * @param measurementType the new measurement type for this item
     * @see MeasurementType
     */
    public void setMeasurementType(MeasurementType measurementType) {
        if (measurementType != null) {
            this.measurementType = measurementType;
        }
    }

    /**
     * returns whether this item can have sub items or not
     * @return true if this item can have sub items, false otherwise
     */
    public boolean hasSubItem() {
        return hasSubItem;
    }

    /**
     * sets whether this item can have sub items or not
     * @param hasSubItem whether this item can have sub items
     */
    public void setHasSubItem(boolean hasSubItem) {
        this.hasSubItem = hasSubItem;
    }

    /**
     * returns the current singular name for this item's sub items
     * @return the current singular name of the sub items
     */
    public String getSubItemSingular() {
        return subItemSingular;
    }

    /**
     * sets the current singular name for this item's sub items
     * @param subItemSingular The new singular name for sub items
     */
    public void setSubItemSingular(String subItemSingular) {
        if (subItemSingular != null) {
            this.subItemSingular = subItemSingular;
        }
    }

    /**
     * returns the plural name for this item's sub items
     * @return The plural name of the sub items
     */
    public String getSubItemPlural() {
        return subItemPlural;
    }

    /**
     * sets the plural name for this item's sub items
     * @param subItemPlural The new plural name of the sub items
     */
    public void setSubItemPlural(String subItemPlural) {
        if (subItemPlural != null) {
            this.subItemPlural = subItemPlural;
        }
    }

    /**
     * returns a list of all the sub items this item has
     * @return The list of sub items
     */
    public List<SubItem> getSubItems() {
        return subItems;
    }

    /**
     * sets the list of sub items to the provided list
     * @param subItems The new list of sub items
     */
    public void setSubItems(List<SubItem> subItems) {
        if (subItems != null) {
            this.subItems = subItems;
        }
    }

    /**
     * returns the list of suppliers that this item has
     * @return The list of suppliers
     */
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    /**
     * sets the list of suppliers to the specified list
     * @param suppliers the new list of suppliers
     */
    public void setSuppliers(List<Supplier> suppliers) {
        if (suppliers != null) {
            this.suppliers = suppliers;
        }
    }
}
