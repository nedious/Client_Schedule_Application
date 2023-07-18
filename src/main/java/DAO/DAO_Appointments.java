package DAO;

import Helper.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * class: DAO_Appointments. Holds methods that connect to database to retrieve data.
 * */
public class DAO_Appointments {

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

            int apptID = resultSet.getInt("Appointment_ID");        // in SQL query, 'Appointment_ID' is the name of the column you want to assign to 'apptID'
            String apptTitle = resultSet.getString("Title");
            String apptDescription = resultSet.getString("Description");
            String apptLocation = resultSet.getString("Location");
            int apptContactID = resultSet.getInt("Contact_ID");
            String apptType = resultSet.getString("Type");

            LocalDateTime apptStartDateTime = resultSet.getTimestamp("Start").toLocalDateTime();    // displays table contense in system time
            LocalDateTime apptEndDateTime = resultSet.getTimestamp("End").toLocalDateTime();

            int apptCustomerID = resultSet.getInt("Customer_ID");
            int apptUserID = resultSet.getInt("User_ID");

            Appointments scheduledAppointments = new Appointments(apptID, apptTitle, apptDescription, apptLocation, apptContactID, apptType, apptStartDateTime, apptEndDateTime, apptCustomerID, apptUserID);
            appointmentsObservableList.add(scheduledAppointments);
        }
        return appointmentsObservableList;
    }

    /**
     * Method: maxApptID. identifies max value from Appointment_ID column, this is then used when generating a new appointment ID
     * @return maxValue. max value from column Appointment_ID in appointments table
     * */
    public static int maxApptID() throws SQLException {
        String sqlSelect = "SELECT MAX(Appointment_ID) FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet = preparedStatement.executeQuery();

        int maxValue = 0;
        if (resultSet.next()) {
            maxValue = resultSet.getInt(1);
            System.out.println("maxApptID(): Max Appointment_ID: " + maxValue);
        }
        return maxValue;
    }

    /**
     * Method: getAppointmentsForDateRange. Gathers data from database that matches logic of start and end times to sort appointments by week and month
     * @return appointmentsObservableList.
     * @throws SQLException
     * */
    public static ObservableList<Appointments> getAppointmentsForDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();

        String sqlSelect = "SELECT * FROM appointments WHERE Start >= ? AND End <= ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlSelect);
        preparedStatement.setObject(1, startDate);
        preparedStatement.setObject(2, endDate);

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