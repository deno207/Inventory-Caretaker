package com.github.deno207.inventory.caretaker.model.database;

public class TestDatabaseManager extends DatabaseManager{

    public static void resetDatabaseManager(){
        entityManagerFactory = null;
    }
}
