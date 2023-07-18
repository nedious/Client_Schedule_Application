package DAO;

import Helper.JDBC;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class: DAO_Contacts. Holds methods that connect to database to retrieve data.
 * */
public class DAO_Contacts extends Contacts {

    /**
     * Method: getAllContactIDs. Gathers Contact_ID data for combo box when generating/updating new customers
     * @return contactIDs
     * */
    public static ObservableList<Integer> getAllContactIDs() throws SQLException {
        ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
        String sqlSelect = "SELECT Contact_ID FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            contactIDs.add(contactID);
        }
        return contactIDs;
    }

    /**
     * Method: DAO_Contacts. Constructor for DAO_Contacts to initialize the DAO_Contacts object
     * @param contactID
     * @param contactName
     */
    public DAO_Contacts(int contactID, String contactName) {
        super(contactID, contactName);      // super statement is used to invoke the constructor of the superclass
    }

    /**
     * Method: getAllContacts. Gets all data from contacts sql table. Used to map Contact_ID to Contact_Name for new/update appointments contact combo box.
     * @return contactsObservableList
     * */
    public static ObservableList<DAO_Contacts> getAllContacts() throws SQLException {
        ObservableList<DAO_Contacts> contactsObservableList = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");

            DAO_Contacts contact = new DAO_Contacts(contactID, contactName);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }
}
