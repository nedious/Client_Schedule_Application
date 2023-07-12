package DAO;

import Helper.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Countries extends Countries {

        public DAO_Countries(int countryID, String countryName) {
            super(countryID, countryName);
        }

    /**
     * Method getAllCountries. Generates country data and stores in ObservableList DAO_Countries
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
            DAO_Countries countries = new DAO_Countries(countryID, countryName);
            countriesObservableList.add(countries);
        }
        return countriesObservableList;
    }
}
