package DAO;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Contacts {
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

    public static ObservableList<DAO_Contacts> getAllContacts() throws SQLException {
        ObservableList<DAO_Contacts> contactsObservableList = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            // ... Retrieve other contact properties as needed

            DAO_Contacts contact = new DAO_Contacts(contactID, contactName);
            contactsObservableList.add(contact);
        }
        return contactsObservableList;
    }

    private int contactID;
    private String contactName;

    public DAO_Contacts(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

}
