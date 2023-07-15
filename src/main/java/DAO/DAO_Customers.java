package DAO;

import Helper.JDBC;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Customers {
    /**
     * Method getAllCustomers. Generates customer data and displays in ObservableList
     * @return customersObservableList
     * @throws SQLException
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        String sqlSelect =
                "SELECT customers.*, first_level_divisions.Division\n" +
                "FROM customers\n" +
                "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID;\n";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");       // in SQL query, 'Customer_ID' is the name of the column you want to assign to 'customerID'
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostalCode = resultSet.getString("Postal_Code");

            String customerCountry = resultSet.getString("Division_ID");     // this is a string that needs to be converted to an int
                String countryNumberCode = customerCountry;                             // convert customerCountry string number to variable for converting into integer
                    int countryInteger = Integer.parseInt(countryNumberCode);           // convert the countryNumberCode string to an integer
                        if (countryInteger <= 54 ){
                            customerCountry = "USA";
                        } else if (countryInteger <= 72){
                            customerCountry = "Canada";
                        } else {
                            customerCountry = "UK";
                        }

            String stateProvinceDivisionID = resultSet.getString("Division");          // in the String sqlSelect statement, Division name is gathered by:  'first_level_divisions.Division'

            String customerPhoneNumber = resultSet.getString("Phone");

            Customers allCustomersListObject = new Customers(customerID, customerName, customerAddress, customerPostalCode, customerCountry, stateProvinceDivisionID, customerPhoneNumber);       // this creates the customer object
            customersObservableList.add(allCustomersListObject);
        }
        return customersObservableList;
    }

    /**
     * Method: maxID. identifies max value from Customer_ID column, this is then used when generating a new customer ID
     * @return maxValue. max value from column Customer_ID in customers table
     * */
    public static int maxID() throws SQLException {
        String sqlSelect = "SELECT MAX(Customer_ID) FROM customers";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();

        int maxValue = 0;
        if (resultSet.next()) {
            maxValue = resultSet.getInt(1);
            System.out.println("maxID(): Max Customer_ID: " + maxValue);
        }
        return maxValue;
    }

    /**
     * Method getAllCustomerIDs. Generates customerID data and displays in ObservableList. Used to display in combo box for appointments
     * @return getAllCustomerIDs
     * @throws SQLException
     */
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        String sqlSelect = "SELECT Customer_ID FROM customers";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");
            customerIDs.add(customerID);
        }
        return customerIDs;
    }
}












