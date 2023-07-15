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

}
