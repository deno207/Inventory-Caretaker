package uk.ac.aber.cs39440.inventory.caretaker.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents the physical mailing address of a {@link Supplier}
 *
 * The Address is comprised of several components:
 * - lineOne: The first line of the address i.e. 24 SomeStreet Road
 * - lineTwo: The second line i.e. apartment 24b
 * - city: The city, town, or village that the address is in i.e. Aberystwyth
 * - County: The county or state that the address is located in i.e. Yorkshire
 * - Country: The country the address is located in i.e. England
 * - Postcode: The address's post code i.e. CA1 3ER
 *
 * As most address do not have a second line, it is reasonable for lineTwo to be null most of the time, and it should
 * be checked that it is not null before you try and use it.
 *
 * @author Damion Wilson
 * @version 1.0
 * @see Supplier
 */
@Entity
public class Address {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String county;
    private String country;
    private String postCode;

    /**
     * Default Constructor that initialises everything to null
     */
    public Address(){
        this(null, null, null, null, null, null);
    }

    /**
     * Basic Constructor which initialises variables to the provided values
     * @param lineOne The first line of the address
     * @param lineTwo The second line of the address, not every address will have one of these
     * @param city The city that the address is in
     * @param county the county that the address is in
     * @param country The country the address is in
     * @param postCode The address's postcode or zip code
     */
    public Address(String lineOne, String lineTwo, String city, String county, String country, String postCode) {
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.county = county;
        this.country = country;
        this.postCode = postCode;
    }

    /**
     * returns the database Id of this address object.
     * The Id is only available after the address has been stored in the database
     * @return the database Id for this address
     */
    public int getId() {
        return id;
    }

    /**
     * sets the database id for this address object
     * The Id is auto-generated when the address object is put into the database and errors will occur if you set this
     * value manually or change it after has been set.
     * @param id the new database Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the first line of the address
     * @return The first line of the address
     */
    public String getLineOne() {
        return lineOne;
    }

    /**
     * sets the first line of the address to the provided value
     * @param lineOne The new first line of the address
     */
    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    /**
     * returns the second line of the address
     * This value may be null as not all addresses have a second line
     * @return The second line of the address
     */
    public String getLineTwo() {
        return lineTwo;
    }

    /**
     * sets the second line of the address to the provided value
     * @param lineTwo the new second line of the address
     */
    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    /**
     * returns the current city for this address
     * @return The current City
     */
    public String getCity() {
        return city;
    }

    /**
     * sets the current city for this address
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * returns the current county for this address
     * @return The current county
     */
    public String getCounty() {
        return county;
    }

    /**
     * sets the current county of the address to the provided value
     * @param county the new county
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * returns the current country of the address
     * @return the current country
     */
    public String getCountry() {
        return country;
    }

    /**
     * set the current country of the address to the specified value
     * @param country the new country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * returns the postcode for the address
     * @return the current postcode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * sets the postcode of the the address to the specified value
     * @param postCode the new postcode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
