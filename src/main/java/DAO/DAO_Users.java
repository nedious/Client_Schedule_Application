package DAO;

import Helper.JDBC;
import Model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Users extends Users {

//    public DAO_Users(String userName, String userPass) {
//
//    }

    /**
     * Method: validateLogin. From login screen, take username and user password, make into string, create prepared statement, generate resultSet if prepared Statement is successful and test if true
     * @throws SQLException
     * @param username from login user text field
     * @param password from login user password field
     * @return boolean
     */
    public static Boolean validateLogin(String username, String password) throws SQLException {
        String sqlSelect = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";     // sql string for query
//        System.out.println("sqlSelect: " + sqlSelect);
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);     // make database connection, pass prepareStatement()
//        System.out.println("preparedStatement: " + preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();         // result set of query from database *if prepared statement returns any results

        try {
            if(resultSet.next()){           // the next() method moves the ResultSet cursor down
                System.out.println("successful login attempt");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

//    /**
//     * Gets all user data
//     * @return
//     * @throws SQLException
//     */
//    public static ObservableList<UsersDAO> getAllUsers() throws SQLException {
//        ObservableList<UsersDAO> userObservableList = FXCollections.observableArrayList();
//        String sqlSelect = "SELECT * FROM users";
//        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//            int userID = resultSet.getInt("User_ID");
//            String userName = resultSet.getString("User_Name");
//            String userPass = resultSet.getString("Password");
//            UsersDAO user = new UsersDAO(userID, userName, userPass);
//            userObservableList.add(user);
//        }
//        return  userObservableList;
//    }
}
