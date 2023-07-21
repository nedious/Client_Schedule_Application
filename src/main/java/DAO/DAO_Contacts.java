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
     * Method: getAllContactNames. Gathers contactNames data for combo box when generating contact appointment report schedules
     * @return contactNames
     * */
    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        String sqlSelect = "SELECT Contact_Name FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String contactName = resultSet.getString("Contact_Name");
            contactNames.add(contactName);
        }
        return contactNames;
    }

    /**
     * Method: getContactNameByID. Gathers contact_Name with the correspoinding Contact_ID for combo box when generating contact appointment report schedules
     * @return null
     * */
    public static String getContactNameByID(int contactID) throws SQLException {
        String sqlSelect = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        preparedStatement.setInt(1, contactID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("Contact_Name");
        }
        return null;
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
