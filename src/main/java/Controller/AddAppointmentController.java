package Controller;

import DAO.*;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static DAO.DAO_Appointments.maxApptID;

/**
 * class: AddAppointmentController collects data from user input and saves data to SQL database. Checks appt time to ensure no overlapping times and 15 min between appts and appt alert reminders
 * */
public class AddAppointmentController implements Initializable {
    @FXML private TextField addNewApptAutoGenApptIDTextField;   // apptID
    @FXML private TextField addNewApptTitleTextField;           // title
    @FXML private TextField addNewApptTypeTextField;            // type
    @FXML private TextField addNewApptDescriptionTextField;     // description
    @FXML private TextField addNewApptLocationTextField;        // location

    @FXML private DatePicker addNewApptDayDatePicker;                 // start date picker
    @FXML private ComboBox<LocalTime> addNewApptStartTimeComboBox;    // start time combo box
    @FXML private ComboBox<LocalTime> addNewApptEndTimeComboBox;      // end time combo box

    @FXML private ComboBox<Integer> addNewApptCustomerIDComboBox;     // customerID combo box
    @FXML private ComboBox<Integer> addNewApptUserIDComboBox;         // userID combo box

    @FXML private ComboBox<String> addNewApptContactComboBox;         // contact combo box

    @FXML private Label currentSystemTimeZone;                           // local computer time dynamic
    @FXML private Label eastCoastTimeZone;


    // ------------ buttons ----------//
    @FXML private Button addNewApptSaveButton;      // save
    @FXML private Button addNewApptCancelButton;    // cancel

    ZoneId localSystemTimeZone = ZoneId.systemDefault();        // Defines ZoneID of local System time zone
    ZoneId utcTimeZone = ZoneId.of("UTC");              // Defines UTC time zone for combo boxes start/end time


    // ------------ generate AppointmentID -------------//
    private static int appointmentID;      // appointmentID for addAppointment form to autopopulate form with appointmentID

    static {
        try {
            appointmentID = maxApptID();       // make appointmentID to be max value of column
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

// --------------------- Methods ------------------------//

    /**
     * Method initializable, initializes values needed for AddAppt form. Sets data for use in upcoming methods. Data includes: systemListener, ApptID, contact combo box, start/end time combo box + logic
     *         // lambda expression:  observable, oldValue, and newValue. These parameters represent the previous selected
     *         value, the current selected value, and the new selected value.  dynamically populates
     *         the addNewApptEndTimeComboBox combo box based on the selected start time in the addNewApptStartTimeComboBox.
     *         It ensures that only end times that are later than the selected start time are available for selection.
     * @param url
     * @param resourceBundle
     * */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

//        systemLister.setText("Current local time zone: " + localSystemTimeZone + " time.");      // set systemListener to gather local computer time zone

// ---------- set CURRENT time zone for user to reference -------- //
        ZoneId systemTimeZone = ZoneId.systemDefault();        // Get the default time zone of the system
//        System.out.println("systemTimeZone:: " + systemTimeZone);
        ZonedDateTime currentTimeZone = ZonedDateTime.now(systemTimeZone);        // Get the current time in the system time zone
//        System.out.println("currentTimeZone:: " + currentTimeZone);

        String formattedTimeCurrentZone = currentTimeZone.format(DateTimeFormatter.ofPattern("HH:mm"));        // Format the time as Hour:Minute

        String timeZoneID = systemTimeZone.getId();        // Get the time zone ID of the system

        currentSystemTimeZone.setText("Current Time: " + formattedTimeCurrentZone + ", Time Zone: " + timeZoneID);        // Set the formatted time and time zone ID in the systemLister label

// ---------- set EAST coast time for user to reference -------- //
        LocalTime eastCoastTime = LocalTime.now(ZoneId.of("America/New_York"));        // Get the current time in the East Coast time zone

        String formattedTimeEastCoast = eastCoastTime.format(DateTimeFormatter.ofPattern("HH:mm"));        // Format the time as Hour:Minute

        eastCoastTimeZone.setText("Global Consulting in East Coast Time: " + formattedTimeEastCoast);        // Set the formatted time in the eastCoastTimeZone label

// ---------- set date to current date -------- //
        LocalDate currentDate = LocalDate.now(localSystemTimeZone);        // Get the current date in the user's local time zone

        addNewApptDayDatePicker.setValue(currentDate);        // Set the current date in the DatePicker

        // ------------------ appt Start / End times combo box logic ---------------//

// Establish East Coast Time Zone -----------//

        ZoneId eastCoastTimeZone = ZoneId.of("America/New_York");  // East Coast time zone

        ZonedDateTime eastCoastDateTime = ZonedDateTime.now(eastCoastTimeZone);  // Current date and time in the East Coast time zone
        LocalDateTime eastCoastLocalDateTime = eastCoastDateTime.toLocalDateTime();  // Convert to LocalDateTime
        System.out.println("East Coast LocalDateTime: " + eastCoastLocalDateTime);

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
//             System.out.println("****hoursDiffEastToLocal timeEast to timeLocal: " + hoursDiffEastToLocal);

        LocalDateTime updateLocalTimeToEasternTime = timeLocal.plusHours(hoursDiffEastToLocal);
//            System.out.println("****updateLocalTimeToEasternTime: " + updateLocalTimeToEasternTime);

        int amStartLocal = 8 + (int) hoursDiffEastToLocal;
//             System.out.println("amStartLocal: " + amStartLocal);
        int pmEndLocal = 22 + (int) hoursDiffEastToLocal;
//             System.out.println("pmEndLocal: " + pmEndLocal);

        if (pmEndLocal >= 24){
            pmEndLocal = pmEndLocal % 24;
        }

        LocalTime startTime = LocalTime.of(amStartLocal, 00);        // Generate start times from 8:00 AM to 9:45 PM Eastern time in 15-minute increments
//            System.out.println("startTime: " + startTime);
        LocalTime endTime = LocalTime.of((pmEndLocal - 1) , 45);          // last possible appointment meeting is 9:45PM eastern
//            System.out.println("endTime: " + endTime);

        while (!startTime.isAfter(endTime)) {
            addNewApptStartTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        LocalTime endTimeExcludingStart = LocalTime.of(amStartLocal, 15);   // Generate start times from 8:15 AM to 10:00 PM Eastern time in 15-minute increments
        LocalTime endEndTime = LocalTime.of(pmEndLocal, 0);              // last possible appointment meeting is 10:00PM eastern

        while (!endTimeExcludingStart.isAfter(endEndTime)) {
            addNewApptEndTimeComboBox.getItems().add(endTimeExcludingStart);
            endTimeExcludingStart = endTimeExcludingStart.plusMinutes(15);
        }


      /**
       * lambda expression: addNewApptStartTimeComboBox.  observable, oldValue, and newValue. These parameters represent the previous selected value, the current selected value, and the new selected value.
       *        It Sets the selection change listener for the start time combo box
       *        It dynamically populates the addNewApptEndTimeComboBox combo box based on the selected start time in the addNewApptStartTimeComboBox.
       *        It ensures that only end times that are later than the selected start time are available for selection.
       * @param observable previous selected value,
       * @param oldValue the current selected value,
       * @param newValue and the new selected value.
       *  */
        addNewApptStartTimeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Update the end time combo box to only display values later than the selected start time
            if (newValue != null) {
                addNewApptEndTimeComboBox.getItems().clear();
                LocalTime endTimeExclusive = newValue.plusMinutes(15);
                while (!endTimeExclusive.isAfter(endEndTime)) {
                    addNewApptEndTimeComboBox.getItems().add(endTimeExclusive);
                    endTimeExclusive = endTimeExclusive.plusMinutes(15);
                }
            }
        });

// -------------------------//

        try {
            // --------------- apptID auto generated value -------------//
            int nextAppointmentID = DAO_Appointments.maxApptID() + 1;        // when newAppt and updateAppt form generates, appointmentID will be autopopulated in form to max value of apptID + 1

            addNewApptAutoGenApptIDTextField.setText(String.valueOf(nextAppointmentID));        // set apptID field

        // --------------- customerID combo box -------------//
            ObservableList<Integer> customerIDs = DAO_Customers.getAllCustomerIDs();         // Retrieve customerID from the database

            addNewApptCustomerIDComboBox.setItems(customerIDs);                     // Set the retrieved customer IDs as items in customerID combo box

            // --------------- userID combo box -------------//
            ObservableList<Integer> userIDs = DAO_Users.getAllUserIDs();             // Retrieve userID from the database

            addNewApptUserIDComboBox.setItems(userIDs);            // Set the retrieved user IDs as items in the userID combo box

//        // --------------- contactID combo box -------------//
//            // Retrieve contactID from the database
//            ObservableList<Integer> contactIDs = DAO_Contacts.getAllContactIDs();
//
//            // Set the retrieved contact IDs as items in the combo box
//            addNewApptContactComboBox.setItems(contactIDs);

// --------------- contactName combo box -------------//
            ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();            // Retrieve contacts from  database

            Map<String, Integer> contactMap = contacts.stream().collect(Collectors.toMap(DAO_Contacts::getContactName, DAO_Contacts::getContactID));             // Create a map of contact names to their corresponding IDs. This will allow combo box to display names of contacts, and the associated ID value will be stored/recoreded in database by SQL statement

            addNewApptContactComboBox.setItems(FXCollections.observableArrayList(contactMap.keySet()));             // Set the retrieved contact names as items in the combo box

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: addNewApptSaveButtonClick. when user clicks save, the fields are checked to make sure they are not blank, then an SQL insert statement is added to save the data to the database
     * */
    @FXML void addNewApptSaveButtonClick(ActionEvent event) {

        if (blankValues()) {

            try {
        // -------------- Contact_ID and Contact_Name mapping -----------//
                ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();

                Map<String, Integer> contactMap = new HashMap<>();          // maps contact names to contact IDs.

                for (DAO_Contacts contact : contacts) {     // By iterating over the contacts list and mapping each contact name to its corresponding contact ID in the contactMap, you will be able to retrieve the contact ID using the selected contact name.
                    contactMap.put(contact.getContactName(), contact.getContactID());
                }

                String selectedContactName = addNewApptContactComboBox.getValue();             // Retrieve the selected contact name from the combo box

                int contactID = contactMap.get(selectedContactName);                // Find the corresponding Contact_ID for the selected contact name

        // ---------- Retrieve user entered values -----------//
                int appointmentID = Integer.parseInt(addNewApptAutoGenApptIDTextField.getText());
                String title = addNewApptTitleTextField.getText();
                String description = addNewApptDescriptionTextField.getText();
                String location = addNewApptLocationTextField.getText();
                String type = addNewApptTypeTextField.getText();
                LocalDate startDate = addNewApptDayDatePicker.getValue();

                LocalTime startTime = LocalTime.parse(addNewApptStartTimeComboBox.getValue().toString());
                LocalTime endTime = LocalTime.parse(addNewApptEndTimeComboBox.getValue().toString());
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(startDate, endTime);
//                    System.out.println("startDateTime:  " + startDateTime);

                Timestamp createDateTime = Timestamp.valueOf(LocalDateTime.now());
                String createdBy = "admin";
                Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdatedBy = "admin";
                int customerID = Integer.parseInt(addNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString());
                int userID = Integer.parseInt(addNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString());
//                int contactID = Integer.parseInt(addNewApptContactComboBox.getSelectionModel().getSelectedItem().toString());


        // ------------ logic for avoiding overlaping appt times -----------//
                ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

                for (Appointments appt : allAppointments) {

                    LocalDateTime apptStartDateTime = appt.getApptStartDateTime();
                    LocalDateTime apptEndDateTime = appt.getApptEndDateTime();

                    int apptCustomerID = appt.getApptCustomerID();
//                    System.out.println("apptCustomerID: " + apptCustomerID);

                    if ((startDateTime.isBefore(apptEndDateTime)) && (endDateTime.isAfter(apptStartDateTime))) {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Time Conflict");
                        alert.setHeaderText(null);
                        alert.setContentText("The selected time overlaps with an existing appointment for another customer: Customer ID # " + apptCustomerID
                                + "\n\nExisting Start Time: " + apptStartDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                + "\n\nExisting End Time: " + apptEndDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                + "\n\nPlease select a different time.");
                        alert.showAndWait();

                        return; // Exit the method without saving the data
                    }
                }

        // ------------ SQL insert statement -------------//
                String sqlInsert = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlInsert);

                preparedStatement.setInt(1, appointmentID);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, location);
                preparedStatement.setString(5, type);
                preparedStatement.setTimestamp(6, Timestamp.valueOf(startDateTime));
                preparedStatement.setTimestamp(7, Timestamp.valueOf(endDateTime));
                preparedStatement.setTimestamp(8, createDateTime);
                preparedStatement.setString(9, createdBy);
                preparedStatement.setTimestamp(10, lastUpdateDateTime);
                preparedStatement.setString(11, lastUpdatedBy);
                preparedStatement.setInt(12, customerID);
                preparedStatement.setInt(13, userID);
                preparedStatement.setInt(14, contactID);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row inserted successfully. (Expecting 1 row inserted)");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) addNewApptSaveButton.getScene().getWindow();
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
     * Method: blankValues. checks user input to determine if there is a blank value.
     * @return true. if value is blank alert will display per field that needs to be filled in
     * */
    public boolean blankValues(){

            String title = addNewApptTitleTextField.getText();               // title
            String type = addNewApptTypeTextField.getText();                 // type
            String description = addNewApptDescriptionTextField.getText();   // description
            String location = addNewApptLocationTextField.getText();         // location

            LocalDate startAndEndDate = addNewApptDayDatePicker.getValue();                                           // day date picker
//            String startTime = addNewApptStartTimeComboBox.getSelectionModel().getSelectedItem().toString();    // start time combo box
//            String endTime = addNewApptEndTimeComboBox.getSelectionModel().getSelectedItem().toString();        // end time combo box

//            String customerID = addNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString();  // customerID combo box
//            String userID = addNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString();          // userID combo box
//            String contactID = addNewApptContactComboBox.getSelectionModel().getSelectedItem().toString();      // contact combo box

        if (title.equals("")){AlertDisplay.displayAlert(10); return false;}
        else if (type.equals("")){AlertDisplay.displayAlert(11); return false;}
        else if (description.equals("")){AlertDisplay.displayAlert(12); return false;}
        else if (location.equals("")){AlertDisplay.displayAlert(13); return false;}

        else if (startAndEndDate == null){AlertDisplay.displayAlert(14); return false;}
        else if (addNewApptStartTimeComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(15); return false;}    // start time combo box
        else if (addNewApptEndTimeComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(16); return false;}     // end time combo box

        else if (addNewApptCustomerIDComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(17); return false;}  // customerID combo box
        else if (addNewApptUserIDComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(18); return false;}      // userID combo box
        else if (addNewApptContactComboBox.getSelectionModel().isEmpty()){AlertDisplay.displayAlert(19); return false;}     // contact combo box

        return true;
    }

    /**
     * Method: addNewApptCancelButtonClick. when user clicks cancel, the main appointment/customer screen is displayed again
     *  @param event user click
     *  @throws IOException
     * */
    @FXML void addNewApptCancelButtonClick(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertCancel.setTitle("Canceling");
        alertCancel.setHeaderText("Do you want to Cancel?");
        alertCancel.setContentText("Appointment data will not be saved");
        Optional<ButtonType> result = alertCancel.showAndWait();

        if(result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) addNewApptCancelButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }
}