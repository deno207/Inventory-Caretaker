package com.github.deno207.inventory.caretaker.model.entity;

import org.hibernate.annotations.GenericGenerator;
import com.github.deno207.inventory.caretaker.view.adaptor.DisplayItem;
import com.github.deno207.inventory.caretaker.view.adaptor.ItemType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Supplier represent the individual suppliers of the stock items in this system
 *
 * suppliers contain the contact information of the suppliers in three forms, a website address, a phone number, and a
 * physical postage address.
 *
 * As an implementor of DisplayItem, suppliers can be displayed on the UI in grid items
 *
 * @author Damion Wilson
 * @version 1.1.1
 * @see DisplayItem
 */
@Entity
public class Supplier implements DisplayItem {
    private static final String IMAGE_FOLDER = "images/supplier/";
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String name;
    private String description;
    private String imageName;
    private String website;
    private String phoneNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * default constructor which initialises values to null
     */
    public Supplier(){
        this(null, null, null);
    }

    /**
     * standard constructor which initialises variables to the specified values
     * @param name The name of the supplier
     * @param description A description of the supplier
     * @param imageName The file name of the image representing the supplier
     */
    public Supplier(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.website = null;
        this.phoneNumber = null;
        this.address = null;
    }

    /**
     * returns the database Id of this supplier object.
     * This value is only set after the supplier has been stored in the database
     * @return The database Id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the database id for this supplier item object
     * The Id is auto-generated when the supplier item object is put into the database and errors will occur if you set this
     * value manually or change it after has been set.
     * @param id the new database Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the name of this supplier
     * @return The supplier's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * returns the file path to this supplier's image file
     * @return The file path to the image file or null if imageName is null or blank
     */
    @Override
    public String getImagePath() {
        if (imageName == null || imageName.isBlank()) {
            return null;
        }
        return IMAGE_FOLDER + imageName;
    }

    /**
     * returns the SUPPLIER item type
     *
     * This is mainly used to determine item specific behaviour in UI controllers,
     * which only handle DisplayItems instead of Suppliers.
     * @return ItemType.SUPPLIER
     * @see ItemType
     * @see DisplayItem
     */
    @Override
    public ItemType getItemType() {
        return ItemType.SUPPLIER;
    }

    /**
     * sets the supplier's name
     * @param name The supplier's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the supplier's description
     * @return The supplier's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the supplier's description to the provided value
     * @param description the new description of this supplier
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the name of the image file containing this supplier's image
     * @return The supplier's image file name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * sets the name of the supplier's image file to the provided value
     * @param imageName The new image file name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * returns the website address for this supplier
     * @return The website address
     */
    public String getWebsite() {
        return website;
    }

    /**
     * sets the website address to the specified value
     * @param website The new website address
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * returns the supplier's phone number
     * @return The supplier's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets the supplier's phone number to the provided value
     * @param phoneNumber The new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns the supplier's postal address
     * @return The supplier's postal address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * sets the supplier's postal address to the provided value
     * @param address The new postal address of the supplier
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
