package uk.ac.aber.cs39440.inventory.caretaker.util;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataSetExporter {

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:database/test.db", "sa", "");
        IDatabaseConnection databaseConnection = new DatabaseConnection(connection);

        IDataSet dataSet = databaseConnection.createDataSet();

        FlatDtdDataSet.write(dataSet, new FileOutputStream("database.dtd"));
    }
}
