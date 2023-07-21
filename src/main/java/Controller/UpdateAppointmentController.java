package Controller;

import DAO.DAO_Appointments;
import DAO.DAO_Contacts;
import DAO.DAO_Customers;
import DAO.DAO_Users;
import Helper.AlertDisplay;
import Helper.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * class UpdateAppointmentController. Generates form that autopopulates data from selected row in AppointmentsCustomersController and allows user to update selected row to SQL database
 * */
public class UpdateAppointmentController implements Initializable {
    @FXML private TextField updateNewApptAutoGenApptIDTextField;   // apptID
    @FXML private TextField updateNewApptTitleTextField;           // title
    @FXML private TextField updateNewApptTypeTextField;            // type
    @FXML private TextField updateNewApptDescriptionTextField;     // description
    @FXML private TextField updateNewApptLocationTextField;        // location

    @FXML private DatePicker updateNewApptDayDatePicker;                   // start date picker
    @FXML private ComboBox<LocalTime> updateNewApptStartTimeComboBox;      // start time combo box
    @FXML private ComboBox<LocalTime> updateNewApptEndTimeComboBox;        // end time combo box

    @FXML private ComboBox<Integer> updateNewApptCustomerIDComboBox;     // customerID combo box
    @FXML private ComboBox<Integer> updateNewApptUserIDComboBox;         // userID combo box
    @FXML private ComboBox<String> updateNewApptContactComboBox;         // contact combo box
    private Map<Integer, String> contactMap;        // Declare the contactMap as a field of the class

    @FXML private Label currentSystemTimeZone;                           // local computer time dynamic
    @FXML private Label eastCoastTimeZone;

// ------------ buttons ----------//
    @FXML private Button updateNewApptSaveButton;
    @FXML private Button updateNewApptCancelButton;
    ZoneId localSystemTimeZone = ZoneId.systemDefault();       // Defines ZoneID of local System time zone

    // ------------ generate AppointmentID -------------//
    private static int appointmentID;      // appointmentID for addAppointment form to autopopulate form with appointmentID

// --------------------- Methods ------------------------//

    /**
     * Method initializable, Initializes values needed for UpdateAppointment form, has LAMBDA EXPRESSION. Sets data for use in upcoming methods. Data includes: systemListener, ApptID, contact combo box, start/end time combo box + logic
     * <p>
     *     lambda expression: updateNewApptStartTimeComboBox.  observable, oldValue, and newValue. These parameters represent the previous selected value, the current selected value, and the new selected value.
     *        It Sets the selection change listener for the start time combo box
     *        It dynamically populates the updateNewApptEndTimeComboBox combo box based on the selected start time in the updateNewApptStartTimeComboBox.
     *        It ensures that only end times that are later than the selected start time are available for selection.
     * </p>
     * @param url
     * @param resourceBundle
     * */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

//        systemLister.setText("Current local time zone: " + localSystemTimeZone + " time.");      // set systemListener to gather local computer time zone

// ---------- set current time zone for user to reference -------- //
        ZoneId systemTimeZone = ZoneId.systemDefault();        // Get the default time zone of the system

        ZonedDateTime currentTimeZone = ZonedDateTime.now(systemTimeZone);        // Get the current time in the system time zone

        String formattedTimeCurrentZone = currentTimeZone.format(DateTimeFormatter.ofPattern("HH:mm"));        // Format the time as Hour:Minute

        String timeZoneID = systemTimeZone.getId();        // Get the time zone ID of the system

        currentSystemTimeZone.setText("Current Time: " + formattedTimeCurrentZone + ", Time Zone: " + timeZoneID);        // Set the formatted time and time zone ID in the systemLister label

// ---------- set east coast time for user to reference -------- //
        LocalTime eastCoastTime = LocalTime.now(ZoneId.of("America/New_York"));        // Get the current time in the East Coast time zone

        String formattedTimeEastCoast = eastCoastTime.format(DateTimeFormatter.ofPattern("HH:mm"));        // Format the time as Hour:Minute

        eastCoastTimeZone.setText("Global Consulting in East Coast Time: " + formattedTimeEastCoast);        // Set the formatted time in the eastCoastTimeZone label

// ------//

        try {
            updateNewApptAutoGenApptIDTextField.setText(String.valueOf(appointmentID));

            // --------------- customerID combo box -------------//
            ObservableList<Integer> customerIDs = DAO_Customers.getAllCustomerIDs();         // Retrieve customerID from the database

            updateNewApptCustomerIDComboBox.setItems(customerIDs);                     // Set the retrieved customer IDs as items in customerID combo box

            // --------------- userID combo box -------------//
            ObservableList<Integer> userIDs = DAO_Users.getAllUserIDs();             // Retrieve userID from the database

            updateNewApptUserIDComboBox.setItems(userIDs);            // Set the retrieved user IDs as items in the userID combo box

            // --------------- Contact_Name combo box -------------//
            ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();            // Retrieve contacts from the database

            contactMap = contacts.stream().collect(Collectors.toMap(DAO_Contacts::getContactID, DAO_Contacts::getContactName));            // Create a map of contact IDs to their corresponding names

            updateNewApptContactComboBox.setItems(FXCollections.observableArrayList(contactMap.values()));            // Set the retrieved contact names as items in the combo box

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ------------------ appt Start / End times combo box logic ---------------//

// Establish East Coast Time Zone -----------//

        ZoneId eastCoastTimeZone = ZoneId.of("America/New_York");  // East Coast time zone

        ZonedDateTime eastCoastDateTime = ZonedDateTime.now(eastCoastTimeZone);  // Current date and time in the East Coast time zone
        LocalDateTime eastCoastLocalDateTime = eastCoastDateTime.toLocalDateTime();  // Convert to LocalDateTime
//        System.out.println("East Coast LocalDateTime: " + eastCoastLocalDateTime);

        int yearEast = eastCoastLocalDateTime.getYear();          // Get the year component
        int monthEast = eastCoastLocalDateTime.getMonthValue();     // Get the month component as int
        int dayEast = eastCoastLocalDateTime.getDayOfMonth();     // Get the day of the month component
        int hourEast = eastCoastLocalDateTime.getHour();
        int minuteEast = eastCoastLocalDateTime.getMinute();      // Get the minute component

// Establish Local System Time Zone -----------//

        LocalDateTime localDateTime = LocalDateTime.now();  // Current date and time in the local system time zone
//        System.out.println("Local System LocalDateTime: " + localDateTime);

        int yearLocal = localDateTime.getYear();
        int monthLocal = localDateTime.getMonthValue();
        int dayLocal = localDateTime.getDayOfMonth();
        int hourLocal = localDateTime.getHour();
        int minuteLocal = localDateTime.getMinute();

// Begin logic for combo box start appt times ---------------//

        LocalDateTime timeEast = LocalDateTime.of(yearEast, monthEast, dayEast, hourEast, minuteEast);  // First EastCoastDateTime
        LocalDateTime timeLocal = LocalDateTime.of(yearLocal, monthLocal, dayLocal, hourLocal, minuteLocal); // Second LocalDateTime

        Duration duration = Duration.between(timeEast, timeLocal);  // Calculate the duration between the two LocalDateTime objects

        long hoursDiffEastToLocal = duration.toHours();      // Get the number of hours in the duration
//        System.out.println("****hoursDiffEastToLocal timeEast to timeLocal: " + hoursDiffEastToLocal);

        LocalDateTime updateLocalTimeToEasternTime = timeLocal.plusHours(hoursDiffEastToLocal);
//        System.out.println("****updateLocalTimeToEasternTime: " + updateLocalTimeToEasternTime);

        int amStartLocal = 8 + (int) hoursDiffEastToLocal;
//        System.out.println("amStartLocal: " + amStartLocal);
        int pmEndLocal = 22 + (int) hoursDiffEastToLocal;
//        System.out.println("pmEndLocal: " + pmEndLocal);

        if (pmEndLocal >= 24){
            pmEndLocal = pmEndLocal % 24;
        }

        LocalTime startTime = LocalTime.of(amStartLocal, 00);        // Generate start times from 8:00 AM to 9:45 PM Eastern time in 15-minute increments
//        System.out.println("startTime: " + startTime);
        LocalTime endTime = LocalTime.of((pmEndLocal - 1) , 45);          // last possible appointment meeting is 9:45PM eastern
//        System.out.println("endTime: " + endTime);

        while (!startTime.isAfter(endTime)) {
            updateNewApptStartTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        LocalTime endTimeExcludingStart = LocalTime.of(amStartLocal, 15);   // Generate start times from 8:15 AM to 10:00 PM Eastern time in 15-minute increments
        LocalTime endEndTime = LocalTime.of(pmEndLocal, 0);              // last possible appointment meeting is 10:00PM eastern

        while (!endTimeExcludingStart.isAfter(endEndTime)) {
            updateNewApptEndTimeComboBox.getItems().add(endTimeExcludingStart);
            endTimeExcludingStart = endTimeExcludingStart.plusMinutes(15);
        }

        /**
         * lambda expression: updateNewApptStartTimeComboBox.  observable, oldValue, and newValue. These parameters represent the previous selected value, the current selected value, and the new selected value.
         *        It Sets the selection change listener for the start time combo box
         *        It dynamically populates the updateNewApptEndTimeComboBox combo box based on the selected start time in the updateNewApptStartTimeComboBox.
         *        It ensures that only end times that are later than the selected start time are available for selection.
         * @param observable previous selected value,
         * @param oldValue the current selected value,
         * @param newValue and the new selected value.
         *  */
        updateNewApptStartTimeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Update the end time combo box to only display values later than the selected start time
            if (newValue != null) {
                updateNewApptEndTimeComboBox.getItems().clear();
                LocalTime endTimeExclusive = newValue.plusMinutes(15);
                while (!endTimeExclusive.isAfter(endEndTime)) {
                    updateNewApptEndTimeComboBox.getItems().add(endTimeExclusive);
                    endTimeExclusive = endTimeExclusive.plusMinutes(15);
                }
            }
        });
    }

    /**
     * Method: populateFieldsWithAppointment. when user clicks update customer in appointmentsCustomerController, this method populates the values of the UPDATE customer form
     * @param appointment
     * */
    public void populateFieldsWithAppointment (Appointments appointment) {
        updateNewApptAutoGenApptIDTextField.setText(String.valueOf(appointment.getApptID()));   // apptID
        updateNewApptTitleTextField.setText(appointment.getApptTitle());                        // title
        updateNewApptTypeTextField.setText(appointment.getApptType());                          // type
        updateNewApptDescriptionTextField.setText(appointment.getApptDescription());            // description
        updateNewApptLocationTextField.setText(appointment.getApptLocation());                  // location

        LocalDate appointmentDate = appointment.getApptStartDateTime().toLocalDate();      // Convert LocalDateTime to LocalDate for the DatePicker
        updateNewApptDayDatePicker.setValue(appointmentDate);                              // appointment date picker

        updateNewApptStartTimeComboBox.setValue(appointment.getApptStartDateTime().toLocalTime());    // start time combo box
        updateNewApptEndTimeComboBox.setValue(appointment.getApptEndDateTime().toLocalTime());        // end time combo box

        updateNewApptCustomerIDComboBox.setValue(appointment.getApptCustomerID());                  // customerID combo box
        updateNewApptUserIDComboBox.setValue(appointment.getApptUserID());                          // userID combo box
        updateNewApptContactComboBox.setValue(contactMap.get(appointment.getApptContactID()));      // contact combo box
    }

    /**
     * Method: updateNewApptSaveButtonClick. when user clicks save appointment form is checked to ensure all fields are filled in, then the new data is updated into the sql database
     * @param event user click
     * */
    @FXML void updateNewApptSaveButtonClick(ActionEvent event) {

        if (blankValues()) {

            try {
      // -------------- Contact_ID and Contact_Name mapping -----------//
                ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();

                Map<String, Integer> contactMap = new HashMap<>();      // maps contact names to contact IDs.

                for (DAO_Contacts contact : contacts) {          // By iterating over the contacts list and mapping each contact name to its corresponding contact ID in the contactMap, you will be able to retrieve the contact ID using the selected contact name.
                    contactMap.put(contact.getContactName(), contact.getContactID());
                }

                String selectedContactName = updateNewApptContactComboBox.getValue();                // Retrieve the selected contact name from the combo box

                int contactID = contactMap.get(selectedContactName);                // Find the corresponding Contact_ID for the selected contact name

        // ---------- Retrieve user entered values -----------//
                int appointmentID = Integer.parseInt(updateNewApptAutoGenApptIDTextField.getText());
                String title = updateNewApptTitleTextField.getText();
                String description = updateNewApptDescriptionTextField.getText();
                String location = updateNewApptLocationTextField.getText();
                String type = updateNewApptTypeTextField.getText();

                    LocalDate startDate = updateNewApptDayDatePicker.getValue();

                    LocalTime startTime = LocalTime.parse(updateNewApptStartTimeComboBox.getValue().toString());
                    LocalTime endTime = LocalTime.parse(updateNewApptEndTimeComboBox.getValue().toString());

                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);

//                Timestamp createDateTime = Timestamp.valueOf(LocalDateTime.now());

                    String createdBy = "admin";
                    Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());
                    String lastUpdatedBy = "admin";

                int customerID = Integer.parseInt(updateNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString());
                int userID = Integer.parseInt(updateNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString());

        // ------------ logic for identical appointments -----------//
                ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

                Appointments existingAppointment = null;
                for (Appointments appt : allAppointments) {
                    if (appt.getApptID() == appointmentID) {
                        existingAppointment = appt;
                        break;
                    }
                }

                // Check if the data entered by the user matches the data of the existing appointment
                if (existingAppointment != null) {
                    if (title.equals(existingAppointment.getApptTitle())
                            && description.equals(existingAppointment.getApptDescription())
                            && location.equals(existingAppointment.getApptLocation())
                            && type.equals(existingAppointment.getApptType())
                            && startDateTime.equals(existingAppointment.getApptStartDateTime())
                            && endDateTime.equals(existingAppointment.getApptEndDateTime())
                            && customerID == existingAppointment.getApptCustomerID()
                            && userID == existingAppointment.getApptUserID()
                            && contactID == existingAppointment.getApptContactID()) {
                        // No changes made to the appointment
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("No Change");
                        alert.setHeaderText(null);
                        alert.setContentText("No changes were made to:" + "\n\nAppointment ID # " + appointmentID + "\n\nType:  '" + type + "'" + "\n\nYou will be returned to the Update Appointment Main Screen.");
                        alert.showAndWait();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                        Parent parent = loader.load();
                        Stage stage = (Stage) updateNewApptSaveButton.getScene().getWindow();
                        Scene scene = new Scene(parent);
                        stage.setScene(scene);
                        stage.show();

                        return; // Exit the method without saving the data
                    }
                }

        // ------------ logic for avoiding overlaping appt times -----------//
                for (Appointments appt : allAppointments) {

                    LocalDateTime ApptStartDateTime = appt.getApptStartDateTime();
                    LocalDateTime ApptEndDateTime = appt.getApptEndDateTime();

                    int ApptCustomerID = appt.getApptCustomerID();

                    if ( (startDateTime.isBefore(ApptEndDateTime)) && (endDateTime.isAfter(ApptStartDateTime))) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Time Conflict");
                        alert.setHeaderText(null);
                        alert.setContentText("The selected time overlaps with an existing appointment for another customer: Customer ID # " + ApptCustomerID
                                + "\n\nExisting Start Time: " + ApptStartDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                + "\n\nExisting End Time: " + ApptEndDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                + "\n\nPlease select a different time.");
                        alert.showAndWait();

                        return; // Exit the method without saving the data
                    }
                }

        // ------------ SQL update statement -------------//
                String sqlUpdate =
                        "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? " +
                        "WHERE Appointment_ID=?";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlUpdate);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setTimestamp(5, Timestamp.valueOf(startDateTime));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(endDateTime));
//                preparedStatement.setTimestamp(7, createDateTime);
                preparedStatement.setString(7, createdBy);
                preparedStatement.setTimestamp(8, lastUpdateDateTime);
                preparedStatement.setString(9, lastUpdatedBy);
                preparedStatement.setInt(10, customerID);
                preparedStatement.setInt(11, userID);
                preparedStatement.setInt(12, contactID);
                preparedStatement.setInt(13, appointmentID);


                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row inserted successfully. (Expecting: 1 row inserted)");

                // alert for change/update to appointment.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Change");
                alert.setHeaderText(null);
                alert.setContentText("Appointment ID:  " + appointmentID + "\n\nType:  '" + type + "' \n\nHas been updated.");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) updateNewApptSaveButton.getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method: blankValues. checks user input to determine if there is a blank value in the form.
     * @return true. if value is blank alert will display per field that needs to be filled in
     * */
    public boolean blankValues(){

            String title = updateNewApptTitleTextField.getText();               // title
            String type = updateNewApptTypeTextField.getText();                 // type
            String description = updateNewApptDescriptionTextField.getText();   // description
            String location = updateNewApptLocationTextField.getText();         // location

            LocalDate startAndEndDate = updateNewApptDayDatePicker.getValue();                                       // day date picker
//            String startTime = updateNewApptStartTimeComboBox.getSelectionModel().getSelectedItem().toString();    // start time combo box
//            String endTime = updateNewApptEndTimeComboBox.getSelectionModel().getSelectedItem().toString();        // end time combo box

//            String customerID = updateNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString();  // customerID combo box
//            String userID = updateNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString();          // userID combo box
//            String contactID = updateNewApptContactComboBox.getSelectionModel().getSelectedItem().toString();      // contact combo box

        if (title.equals("")){AlertDisplay.displayAlert(10); return false;}
        else if (type.equals("")){AlertDisplay.displayAlert(11); return false;}
        else if (description.equals("")){AlertDisplay.displayAlert(12); return false;}
        else if (location.equals("")){AlertDisplay.displayAlert(13); return false;}

        else if (startAndEndDate == null){AlertDisplay.displayAlert(14); return false;}
        else if (updateNewApptStartTimeComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(15); return false;}    // start time combo box
        else if (updateNewApptEndTimeComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(16); return false;}     // end time combo box

        else if (updateNewApptCustomerIDComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(17); return false;}  // customerID combo box
        else if (updateNewApptUserIDComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(18); return false;}      // userID combo box
        else if (updateNewApptContactComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(19); return false;}     // contact combo box

        return true;
    }

    /**
     * Method: updateNewApptCancelButtonClick. when user clicks cancel, data is cleared and the main appointment/customer screen is displayed again
     *  @param event user click
     *  @throws IOException
     * */
    @FXML void updateNewApptCancelButtonClick(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertCancel.setTitle("Canceling");
        alertCancel.setHeaderText("Do you want to Cancel?");
        alertCancel.setContentText("Appointment data will not be saved");
        Optional<ButtonType> result = alertCancel.showAndWait();

        if(result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) updateNewApptCancelButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }
}
