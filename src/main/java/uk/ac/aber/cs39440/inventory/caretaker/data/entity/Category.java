package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.hibernate.annotations.GenericGenerator;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.DisplayItem;
import uk.ac.aber.cs39440.inventory.caretaker.ui.adaptor.ItemType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * This class represents a Category.
 *
 * Categories are used to group together stock items.
 * They can also be have a parent category, allowing for hieratical categorisation of various stock items.
 * An example of this could be a category called Denim have a parent category called Fabric.
 * Each category can only have one parent.
 *
 * As this class implements the DisplayItem interface, objects of this class can be displayed in grid items
 *
 * The images used to represent the categories in the UI are managed by the ImageProcessor, which manages a cache
 * of the images, as well as loading them from the files
 * @version 1.1.1
 * @author Damion Wilson
 * @see StockItem
 * @see DisplayItem
 * @see uk.ac.aber.cs39440.inventory.caretaker.data.processor.ImageProcessor
 */
@Entity
public class Category implements DisplayItem {
    private static final String IMAGE_FOLDER = "images/category/";
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String name;
    private String description;
    private String imageName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "PARENT_ID_FK"))
    private Category parent;

    /**
     * Default constructor which initialises all values to null
     */
    public Category(){
        this(null, null, null);
    }

    /**
     * Standard Constructor which initialises all variables to the provided values
     * @param name The name of the category
     * @param description A description of the category
     * @param imageName The file name of the image used to represent the category
     */
    public Category(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        parent = null;
    }

    /**
     * returns the database Id of this category object.
     * This value is only set after the category has been stored in the database
     * @return The database Id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the database id for this category object
     * The Id is auto-generated when the category object is put into the database and errors will occur if you set this
     * value manually or change it after has been set.
     * @param id the new database Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the categories current name
     * @return The categories current name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * returns the file path to the image file used to represent this category
     *
     * This file path is relative to the current working directory and points to a file
     * in the applications image cache
     * @return The file path to the category image
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
     * returns the category ItemType
     *
     * This is used to determine item specific behavior when the item is being displayed
     * @return ItemType.CATEGORY
     * @see DisplayItem
     */
    @Override
    public ItemType getItemType() {
        return ItemType.CATEGORY;
    }

    /**
     * sets the name of the category
     * @param name The category's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * return the current description of the category
     * @return The category's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the category's description to the provided value
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the file name of the image file used to represent this category
     * @return the file name of the category's image
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * sets the file name of a new image file that has been added to the image cache
     * @param imageName the name of the new image file
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * return the parent category for this category
     * @return the parent category, or null if there is no parent category
     */
    public Category getParent() {
        return parent;
    }

    /**
     * sets the parent of this category to the provided category
     * @param parent the new parent of this category
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }
}
