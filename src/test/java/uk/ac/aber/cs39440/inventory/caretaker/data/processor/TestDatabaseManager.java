package uk.ac.aber.cs39440.inventory.caretaker.data.processor;

public class TestDatabaseManager extends DatabaseManager{

    public static void resetDatabaseManager(){
        entityManagerFactory = null;
    }
}
