package DAO;

import Helper.JDBC;
import Model.StateProvinceDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class DAO_StateProvinceDivision. Holds methods that connect to database to retrieve data. Extends StateProvinceDivision to use Constructors and Getters.
 * */
public class DAO_StateProvinceDivision extends StateProvinceDivision {

    /**
     * Method: DAO_StateProvinceDivision. Constructor for DAO_Countries to initialize the DAO_Countries object
     * @param stateProvinceDivisionID
     * @param stateProvinceDivisionName
     * @param countryID
     */
    public DAO_StateProvinceDivision(int stateProvinceDivisionID, String stateProvinceDivisionName, int countryID) {
        super(stateProvinceDivisionID, stateProvinceDivisionName, countryID);        // super statement is used to invoke the constructor of the superclass
    }

    /**
     * Method: getAllStateProvinceDivision. Gathers all data from first_level_divisions table
     * @return stateProvinceDivisionObservableList
     * @throws SQLException
     */
    public static ObservableList<DAO_StateProvinceDivision> getAllStateProvinceDivision() throws SQLException {
        ObservableList<DAO_StateProvinceDivision> stateProvinceDivisionObservableList = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM first_level_divisions";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int stateProvinceDivisionID = resultSet.getInt("Division_ID");
            String stateProvinceDivisionName = resultSet.getString("Division");
            int countryID = resultSet.getInt("Country_ID");
            DAO_StateProvinceDivision allStateProvinceDivisionNameList = new DAO_StateProvinceDivision(stateProvinceDivisionID, stateProvinceDivisionName, countryID);
            stateProvinceDivisionObservableList.add(allStateProvinceDivisionNameList);
        }
        return stateProvinceDivisionObservableList;
    }
}
