package DAO;

import Helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class: DAO_Countries. Holds methods that connect to database to retrieve data. Extends Countries to use Constructors and Getters.
 * */
public class DAO_Countries extends Countries {

    /**
     * Method: DAO_Countries. Constructor for DAO_Countries to initialize the DAO_Countries object
     * @param countryID
     * @param countryName
     */
    public DAO_Countries(int countryID, String countryName) {
        super(countryID, countryName);      // super statement is used to invoke the constructor of the superclass
    }

    /**
     * Method getAllCountries. Generates country data from sql database and stores in ObservableList DAO_Countries
     * @return countriesObservableList. List of countries
     * @throws SQLException
     */
    public static ObservableList<DAO_Countries> getAllCountries() throws SQLException {
        ObservableList<DAO_Countries> countriesObservableList = FXCollections.observableArrayList();
        String sqlSelect = "SELECT Country_ID, Country FROM countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            DAO_Countries countries = new DAO_Countries(countryID, countryName);        // DAO_Countries(countryID, countryName) invokes constructor
            countriesObservableList.add(countries);
        }
        return countriesObservableList;
    }
}
