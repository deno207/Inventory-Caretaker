package com.github.deno207.inventory.caretaker.core.processor;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.QueryHints;
import com.github.deno207.inventory.caretaker.model.entity.Address;
import com.github.deno207.inventory.caretaker.model.entity.Category;
import com.github.deno207.inventory.caretaker.model.entity.MeasurementType;
import com.github.deno207.inventory.caretaker.model.entity.StockItem;
import com.github.deno207.inventory.caretaker.model.entity.SubItem;
import com.github.deno207.inventory.caretaker.model.entity.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * The DatabaseManager is the connection between the application and the database.
 *
 * It handles all of the database queries and returns the data in a form that the application can easily use and
 * manipulate. This is handled via an EntityManager which is created from a global instance of an
 * EntityManagerFactory shared by all instances of the DatabaseManager. The EntityManager and the EntityManagerFactory
 * are part of the Java Persistence API.
 *
 * @author Damion Wilson
 * @version 1.5
 * @see EntityManagerFactory
 * @see EntityManager
 */
public class DatabaseManager {

    protected static EntityManagerFactory entityManagerFactory;
    private ImageProcessor imageProcessor;

    /**
     * Default constructor which initialises the EntityManagerFactory as a singleton variable so all DatabaseManager
     * instances can access it.
     */
    public DatabaseManager() {
        if (entityManagerFactory == null) {
            synchronized (DatabaseManager.class) {
                if (entityManagerFactory == null) {
                    entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(new SQLiteProvider(), new HashMap());
                }
            }
        }
        imageProcessor = new ImageProcessor();
    }

    /**
     * returns a list of all the StockItems currently in the database, with all of their Sub-Items, Suppliers,
     * and Categories.
     * @return List of all StockItems in the database
     */
    public List<StockItem> getAllStockItems() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<StockItem> results = entityManager.createQuery("select distinct s from StockItem s left join fetch " +
                "s.suppliers", StockItem.class).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
        results = entityManager.createQuery("select distinct s from StockItem s left join fetch " +
                "s.category", StockItem.class).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
        results = entityManager.createQuery("select distinct s from StockItem s left join fetch " +
                "s.subItems", StockItem.class).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
        return results;
    }

    /**
     * returns a list of all the Suppliers currently in the database along with their associated Address
     * @return List of all suppliers in the database
     */
    public List<Supplier> getAllSuppliers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Supplier> results = entityManager.createQuery("select s from Supplier s left join fetch s.address",
                Supplier.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return results;
    }

    /**
     * returns a list of all categories currently in the database along with their associated parent category
     * @return List of all categories in the database
     */
    public List<Category> getAllCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Category> results = entityManager.createQuery("select c from Category c left join fetch c.parent",
                Category.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return results;
    }

    /**
     * Adds the provided StockItem object to the database and saves
     * @param newItem the StockItem to be added
     * @param selectedImage The selected image file for this stock item
     * @throws IOException if an error occurred while saving the image
     */
    public void addStockItem(StockItem newItem, File selectedImage) throws IOException{
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            //save the new stock item to the database
            entityManager.persist(newItem);

            //save the image if one was selected
            String imageName = imageProcessor.saveExternalImage(selectedImage, newItem, newItem.getId());

            //set the image name to the returned image name
            newItem.setImageName(imageName);
        } catch (IOException e) {
            //error occurred, set image name to a blank string
            System.err.println("Error saving selected image");
            newItem.setImageName("");
            throw e;
        } finally {
            //commit the changes and close the manager
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * reloads the provided StockItem object, updating its variables to the values currently held in the database
     * @param stockItem the StockItem to be reloaded
     * @return The reloaded StockItem object
     */
    public StockItem reloadStockItem(StockItem stockItem) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        stockItem = entityManager.find(StockItem.class, stockItem.getId());
        //load sub items, suppliers and categories
        stockItem.getSuppliers().size();
        stockItem.getSubItems().size();
        stockItem.getCategory();

        entityManager.getTransaction().commit();
        entityManager.close();
        return stockItem;
    }

    /**
     * Updates the StockItem with the database id of id with the provided values
     * @param name The new name of the StockItem
     * @param description The new description of the StockItem
     * @param lowStockAlert The low stock alert level of the StockItem
     * @param measurementType The new MeasurementType of the StockItem
     * @param hasSubItems The new value of hasSubItems
     * @param subItemSingular The new singular sub item name for the StockItem
     * @param subItemPlural The new plural sub item name for the StockItem
     * @param category The StockItems new Category
     * @param suppliers The new list of suppliers for the StockItem
     * @param id The database id of the StockItem to be updated
     * @param selectedImage The image that has been selected
     */
    public void updateStockItem(String name, String description, float lowStockAlert,
                                MeasurementType measurementType, boolean hasSubItems, String subItemSingular, String subItemPlural,
                                Category category, List<Supplier> suppliers, int id, File selectedImage) throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            StockItem stockItem = entityManager.find(StockItem.class, id);
            stockItem.setName(name);
            stockItem.setDescription(description);
            stockItem.setLowStock(lowStockAlert);
            stockItem.setMeasurementType(measurementType);
            stockItem.setHasSubItem(hasSubItems);
            stockItem.setSubItemPlural(subItemPlural);
            stockItem.setSubItemSingular(subItemSingular);
            stockItem.setCategory(category);
            stockItem.setSuppliers(suppliers);

            String imageName = imageProcessor.replaceCurrentImage(selectedImage, stockItem, id);
            stockItem.setImageName(imageName);
        } catch (IOException e) {
            System.err.println("Error saving new image file");
            throw e;
        }finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * Adds the provided Sub-Item to the database and associate it with the StockItem with database id of id
     * @param subItem The SubItem to be added to the database
     * @param stockItemId The database id of the StockItem this sub-item belongs to
     */
    public void addSubItem(SubItem subItem, int stockItemId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        StockItem stockItem = entityManager.find(StockItem.class, stockItemId);
        stockItem.getSubItems().add(subItem);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Updates the SubItem with the database id of id to the provided values
     * @param displayId The new display id of the SubItem
     * @param location The new location of the SubItem
     * @param amount The new stock level of the SubItem
     * @param id The database id of the SubItem to be updated
     */
    public void updateSubItem(String displayId, String location, float amount, int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        SubItem subItem = entityManager.find(SubItem.class, id);
        subItem.setDisplayId(displayId);
        subItem.setAmount(amount);
        subItem.setLocation(location);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Adds amount to the current stock level of the provided stock item
     * @param stockItem The stock item whose stock level is being changed
     * @param amount The amount of stock being added to the stock level
     */
    public void addStockToStockItem(StockItem stockItem, float amount) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        stockItem = entityManager.find(StockItem.class, stockItem.getId());
        float oldStockLevel = stockItem.getCurrentStock();
        stockItem.setCurrentStock(oldStockLevel + amount);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Subtracts amount from the current stock level of the provided stock item.
     * If the new total stock level would be below zero, then the stock level is set to zero.
     * @param stockItem The stock item whose stock level is being changed
     * @param amount The amount of stock being subtracted from the stock level
     */
    public void removeStockFromStockItem(StockItem stockItem, float amount) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        stockItem = entityManager.find(StockItem.class, stockItem.getId());
        float oldAmount = stockItem.getCurrentStock();
        float newTotal = oldAmount - amount;
        if (newTotal < 0) {
            stockItem.setCurrentStock(0.0f);
        } else {
            stockItem.setCurrentStock(newTotal);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Adds the provided Category to the database
     * @param category The Category to be added to the database
     * @param selectedImage The image file that was selected for this category
     * @throws IOException if an error occurred saving the image file
     */
    public void addCategory(Category category, File selectedImage) throws IOException{
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(category);

            String imageName = imageProcessor.saveExternalImage(selectedImage, category, category.getId());
            category.setImageName(imageName);
        } catch (IOException e) {
            System.err.println("Error saving image file");
            category.setImageName("");
            throw e;
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * Updates a Category with database id of id to the provided values
     * @param id The database id of the category that should be updated
     * @param name The new name of the category
     * @param description The new description of the category
     * @param parent The new parent category
     * @param selectedImage The image that has been selected
     */
    public void updateCategory(int id, String name, String description, Category parent, File selectedImage) throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Category category = entityManager.find(Category.class, id);
            category.setName(name);
            category.setDescription(description);
            category.setParent(parent);

            String imageName = imageProcessor.replaceCurrentImage(selectedImage, category, id);
            category.setImageName(imageName);
        } catch (IOException e) {
            System.err.println("Error saving image file");
            throw e;
        }finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * Reloads the provided category with the newest values from the database
     * @param category The category to be reloaded
     * @return The provided category with the newest values from the database
     */
    public Category reloadCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        //using manual query as entityManager.find() is causing lazy loading errors
        category = entityManager.createQuery("select c from Category c left join fetch " +
                "c.parent where c.id = :id", Category.class).setParameter("id", category.getId()).getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return category;
    }

    /**
     * Adds the provided Supplier to the database and saves the selected image file
     * @param supplier The Supplier to be added to the database
     * @param address The address that for this supplier
     * @param selectedImage The image file that was selected for this supplier
     * @throws IOException if there was an error saving the image file
     */
    public void addSupplier(Supplier supplier, Address address, File selectedImage) throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            if (address != null) {
                entityManager.persist(address);
                supplier.setAddress(address);
            }

            entityManager.persist(supplier);

            String imageName = imageProcessor.saveExternalImage(selectedImage, supplier, supplier.getId());

            supplier.setImageName(imageName);
        } catch (IOException e) {
            System.err.println("Error saving image");
            supplier.setImageName("");
            throw e;
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * updates the supplier with the database id of id to the provided values
     * @param id The database id of the supplier that is being updated
     * @param name The new name of the supplier
     * @param description The new description of the supplier
     * @param website The new website of the supplier
     * @param phoneNumber The new phone number of the supplier
     * @param address The new address of the supplier
     * @param selectedImage The selected image file
     */
    public void updateSupplier(int id, String name, String description, String website, String phoneNumber,
                               Address address, File selectedImage) throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Supplier supplier = entityManager.find(Supplier.class, id);

            supplier.setName(name);
            supplier.setDescription(description);
            supplier.setWebsite(website);
            supplier.setPhoneNumber(phoneNumber);

            updateAddress(address, entityManager, supplier);

            String imageName = imageProcessor.replaceCurrentImage(selectedImage, supplier, id);
            supplier.setImageName(imageName);
        } catch (IOException e) {
            System.err.println("Error saving image");
            throw e;
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    /**
     * updates the address associated with the supplier to the new provided one.
     *
     * If the new address is null and the old one is not null, then the address is deleted from the supplier
     * If the new address is not null and the old is null, then the new address is added to the database and the supplier
     * If the new address and the old address are not null, then the old address is updated to the values in the new
     * address
     * If both the new address and the old address are null, then nothing is done
     * @param address The new address
     * @param entityManager The entity manager handling the update of the supplier
     * @param supplier The supplier that is being updated
     */
    private void updateAddress(Address address, EntityManager entityManager, Supplier supplier) {
        Address oldAddress = supplier.getAddress();
        if (oldAddress == null && address != null) {
            //if the supplier didn't have an address and is now being given an address
            //save the new address to the database, then associate it with the supplier
            entityManager.persist(address);
            supplier.setAddress(address);
        } else if (oldAddress != null && address == null) {
            //if the supplier had an address and now does not have one,
            //delete the address from the database and remove it from the supplier
            entityManager.remove(oldAddress);
            supplier.setAddress(null);
        } else if (oldAddress != null && address != null) {
            //if the supplier had an address and is being given a new one,
            //update the address in the database
            oldAddress.setLineOne(address.getLineOne());
            oldAddress.setLineTwo(address.getLineTwo());
            oldAddress.setCity(address.getCity());
            oldAddress.setCounty(address.getCounty());
            oldAddress.setCountry(address.getCountry());
            oldAddress.setPostCode(address.getPostCode());
        }
    }

    /**
     * reloads the provided supplier with the latest database values
     * @param supplier The supplier to be reloaded
     * @return The updated supplier
     */
    public Supplier reloadSupplier(Supplier supplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //using manual query as entityManager.find() is causing lazy loading errors
        supplier = entityManager.createQuery("select s from Supplier s left join fetch " +
                "s.address where s.id = :id", Supplier.class).setParameter("id", supplier.getId()).getSingleResult();

        entityManager.close();
        return supplier;
    }
}
