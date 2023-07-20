package Controller;

import DAO.DAO_Appointments;
import Model.Appointments;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * class: ReportsController. Generates a GUI for the user to view reports. Pulls data from database to display and performs logic on data for user.
 *
 * Method: filterAppointmentsByType. Has a lambda expression
 *   // Lambda Expression: allAppointments.filtered(appointment -> appointment.getApptType().equals(type));
 *      'appointment ->':
 *          This takes an Appointment object (from the ObservableList<Appointments>) and enters it as the input parameter to the lambda.
 *      'appointment.getApptType()':
 *          is a method call on the Appointment object to get the appointment type.
 *      '.equals(type)':
 *          checks if the appointment type obtained from getApptType() is equal to the type parameter passed to the filterAppointmentsByType method.
 *      For each Appointment object in the ObservableList<Appointments>, the lambda expression is applied, and if the appointment type matches the type parameter, that appointment is included in the filtered list.
 *
 * Method: filterAppointmentsByMonth. Supports MonthComboBox, creates a filtered list by checking if values in the database match the value selected in the combo box. The values are then displayed in the table.
 *      Lambda Expression:
 *          An individual element of the ObservableList<Appointments> is the input
 *          startDateTime is retrived, and it is formatted for readibility.
 *          then there is a comparision checking if the formattedMonthYear value is equal to the selectedMonth value from the combo box
 *          if they are equal, the value is added to the table
 * */
public class ReportsController implements Initializable {

    // --------- Appointment by Type and Month --------//
    @FXML private Tab tabApptByTypeAndMonth;

    @FXML private TableView<Appointments> apptReportMonthTypeTable;
    @FXML private TableColumn<?, ?> apptReportApptIDColumn;
    @FXML private TableColumn<?, ?> apptReportMonthColumn;
    @FXML private TableColumn<?, ?> apptReportTypeColumn;

    @FXML private Label totalTypeTotal;
    @FXML private Label totalMonthTotal;

    @FXML private ComboBox<String> apptReportSelectTypeComboBox;
    @FXML private ComboBox<String> apptReportSelectMonthComboBox;

    // --------- Schedule by Contact --------//
    @FXML private Tab tabApptByContact;

    @FXML private TableView<?> apptReportContactsTable;

    @FXML private TableColumn<?, ?> scheduleApptIDColumn;
    @FXML private TableColumn<?, ?> scheduleApptTitleColumn;
    @FXML private TableColumn<?, ?> scheduleApptTypeColumn;
    @FXML private TableColumn<?, ?> scheduleApptReportDescriptionColumn;
    @FXML private TableColumn<?, ?> scheduleApptReportStartDateColumn;
    @FXML private TableColumn<?, ?> scheduleApptReportEndDateColumn;
    @FXML private TableColumn<?, ?> scheduleApptReportCustomerIDColumn;

    @FXML private ComboBox<?> sortByContactComboBox;

    // --------- Appointment by Duration --------//
    @FXML private Tab tabApptByDuration;

    @FXML private TableView<?> customerCountryTotal;

    @FXML private TableColumn<?, ?> durationApptIDColumn;
    @FXML private TableColumn<?, ?> durationDurationColumn;
    @FXML private TableColumn<?, ?> durationStartDateColumn;
    @FXML private TableColumn<?, ?> durationEndDateColumn;
    @FXML private TableColumn<?, ?> durationTotalColumn;

    @FXML private Button returnButton;

//    private ObservableList<Appointments> allAppointments; // Keep a reference to the original appointments list

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // --------- Appointments Table ----------- //
            ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

            apptReportMonthTypeTable.setItems(allAppointments);

            apptReportApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            apptReportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            apptReportMonthColumn.setCellValueFactory(new PropertyValueFactory<>("apptStartDateTime"));

            // ------------ set apptReportSelectTypeComboBox with unique Appt Types ---------- //
            ObservableList<String> appointmentTypes = DAO_Appointments.getUniqueAppointmentTypes(); // getUniqueAppointmentTypes method from DAO_Appointments to get unique types for combo box
            apptReportSelectTypeComboBox.setItems(appointmentTypes);
            apptReportSelectTypeComboBox.getItems().add(""); // blank value to reset filter


            // ------------ Set apptReportSelectMonthComboBox with unique Appt Months ---------- //
            ObservableList<String> appointmentMonths = DAO_Appointments.getApptMonth();
            apptReportSelectMonthComboBox.setItems(appointmentMonths);
            apptReportSelectMonthComboBox.getItems().add(""); // blank value to reset filter


            // ------------ Prepare apptReportMonthTypeTable to be filtered by Type ---------- //
            allAppointments = DAO_Appointments.getAllAppointments();
            apptReportMonthTypeTable.setItems(allAppointments);



        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException if needed
        }

    }


    /**
     * Method: apptReportSelectMonthComboBoxClick. When a user selects a value, the table filters month values to match
     * @throws SQLException
     * */
    @FXML void apptReportSelectMonthComboBoxClick() throws SQLException {
        String selectedMonth = apptReportSelectMonthComboBox.getValue();
        ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

        if (selectedMonth != null && !selectedMonth.isEmpty()) {
            ObservableList<Appointments> filteredAppointments = filterAppointmentsByMonth(allAppointments, selectedMonth);
            apptReportMonthTypeTable.setItems(filteredAppointments);
            updateTotalMonthTotal(filteredAppointments); // Update the totalMonthTotal label
        } else {
            apptReportMonthTypeTable.setItems(allAppointments); // Reset to show all appointments if no month is selected
            updateTotalMonthTotal(allAppointments); // Update the totalMonthTotal label with the count of all appointments
        }

        if (selectedMonth == null || selectedMonth.isEmpty()) {
            totalMonthTotal.setText("0");
        }
    }

    /**
     * Method: updateTotalMonthTotal. Calculates the number of filtered appointments for the Month combo box
     * */
    private void updateTotalMonthTotal(ObservableList<Appointments> filteredAppointments) {
        int totalAppointments = filteredAppointments.size();
        totalMonthTotal.setText(Integer.toString(totalAppointments));
    }

    /**
     * Method: filterAppointmentsByMonth. Supports MonthComboBox, creates a filtered list by checking if values in the database match the value selected in the combo box. The values are then displayed in the table.
     *      Lambda Expression:
     *          An individual element of the ObservableList<Appointments> is the input
     *          startDateTime is retrived, and it is formatted for readibility.
     *          then there is a comparision checking if the formattedMonthYear value is equal to the selectedMonth value from the combo box
     *          if they are equal, the value is added to the table
     * */
    private ObservableList<Appointments> filterAppointmentsByMonth(ObservableList<Appointments> allAppointments, String selectedMonth) {
        return allAppointments.filtered(appointment -> {
            LocalDateTime startDateTime = appointment.getApptStartDateTime();
            String formattedMonthYear = DAO_Appointments.formatMonthAndYear(startDateTime);
            return formattedMonthYear.equals(selectedMonth);
        });
    }


    @FXML public void apptContactScheduleSelection() {
    }

    @FXML public void apptDurationSelection() {
    }


    /**
     * Method: apptReportSelectTypeComboBoxClick. value selected from combo box is then filtered in apptReportMonthTypeTable
     * @param event
     * @throws SQLException
     * */
    @FXML void apptReportSelectTypeComboBoxClick(ActionEvent event) throws SQLException {
            String selectedType = apptReportSelectTypeComboBox.getValue();
            ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

            if (selectedType != null && !selectedType.isEmpty()) {
                ObservableList<Appointments> filteredAppointments = filterAppointmentsByType(selectedType);
                apptReportMonthTypeTable.setItems(filteredAppointments);
                updateTotalTypeTotal(filteredAppointments); // Update the totalTypeTotal label
            } else {
                apptReportMonthTypeTable.setItems(allAppointments); // Reset to show all appointments if no type is selected
                updateTotalTypeTotal(allAppointments); // Update the totalTypeTotal label with the count of all appointments
            }

            if (selectedType == null || selectedType.isEmpty()) {
                totalTypeTotal.setText("0");
            }
        }


    /**
     * Method: updateTotalTypeTotal. Calculates the number of filtered appointments for the type combo box
     * */
    private void updateTotalTypeTotal(ObservableList<Appointments> filteredAppointments) {
        int totalAppointments = filteredAppointments.size();
        totalTypeTotal.setText(Integer.toString(totalAppointments));
    }

    /**
     * Method: filterAppointmentsByType. Helper method to filter appointments by type
     *
     *      // Lambda Expression: allAppointments.filtered(appointment -> appointment.getApptType().equals(type));
     *      'appointment ->':
     *          This takes an Appointment object (from the ObservableList<Appointments>) and enters it as the input parameter to the lambda.
     *      'appointment.getApptType()':
     *          is a method call on the Appointment object to get the appointment type.
     *      '.equals(type)':
     *          checks if the appointment type obtained from getApptType() is equal to the type parameter passed to the filterAppointmentsByType method.
     *
     *      For each Appointment object in the ObservableList<Appointments>, the lambda expression is applied, and if the appointment type matches the type parameter, that appointment is included in the filtered list.
     *
     * @param type
     * @throws SQLException
     * */
    private ObservableList<Appointments> filterAppointmentsByType(String type) throws SQLException {
        ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

        return allAppointments.filtered(appointment -> appointment.getApptType().equals(type));
    }


    @FXML void sortByContactComboBoxClick(ActionEvent event) {

    }

    /**
     * Method returnButtonClick. When user clicks, they are redirected to Appt and customer table main screen
     * @param event user click
     * @throws IOException
     * */
    @FXML void returnButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) returnButton.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
