package DAO;

import Helper.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DAO_Appointments {


//    /**
//     * Method: initialize generates tables and values for part and product tables.
//     *
//     * @param url defines resource location
//     * @param resourceBundle accesses local resources
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        mainScreenPartsTable.setItems(Inventory.getAllParts());
//        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        mainScreenProductsTable.setItems(Inventory.getAllProducts());
//        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//    }

    /**
     * Method: getAllAppointments. Generates ObservableList for Appointments from database
     * @return appointmentsObservableList
     * @throws SQLException
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sqlSelect = "SELECT * FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            int apptID = resultSet.getInt("Appointment_ID");
            String apptTitle = resultSet.getString("Title");
            String apptDescription = resultSet.getString("Description");
            String apptLocation = resultSet.getString("Location");
            int apptContactID = resultSet.getInt("Contact_ID");
            String apptType = resultSet.getString("Type");
            LocalDateTime apptStartDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime apptEndDateTime = resultSet.getTimestamp("End").toLocalDateTime();
            int apptCustomerID = resultSet.getInt("Customer_ID");
            int apptUserID = resultSet.getInt("User_ID");
            Appointments scheduledAppointments = new Appointments(apptID, apptTitle, apptDescription, apptLocation, apptContactID, apptType, apptStartDateTime, apptEndDateTime, apptCustomerID, apptUserID);
            appointmentsObservableList.add(scheduledAppointments);
        }
        return appointmentsObservableList;
    }
}