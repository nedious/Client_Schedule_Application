package Controller;

import DAO.DAO_Appointments;
import DAO.DAO_Contacts;
import DAO.DAO_Customers;
import DAO.DAO_Users;
import Helper.AlertDisplay;
import Helper.JDBC;
import Model.Customers;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static DAO.DAO_Appointments.maxApptID;

public class UpdateAppointmentController implements Initializable {
    @FXML private TextField updateNewApptAutoGenApptIDTextField;   // apptID
    @FXML private TextField updateNewApptTitleTextField;           // title
    @FXML private TextField updateNewApptTypeTextField;            // type
    @FXML private TextField updateNewApptDescriptionTextField;     // description
    @FXML private TextField updateNewApptLocationTextField;        // location

    @FXML private DatePicker updateNewApptDayDatePicker;           // start date picker
    @FXML private ComboBox<LocalTime> updateNewApptStartTimeComboBox;      // start time combo box
    @FXML private ComboBox<LocalTime> updateNewApptEndTimeComboBox;        // end time combo box

    @FXML private ComboBox<Integer> updateNewApptCustomerIDComboBox;     // customerID combo box
    @FXML private ComboBox<Integer> updateNewApptUserIDComboBox;         // userID combo box
    @FXML private ComboBox<String> updateNewApptContactComboBox;        // contact combo box
    private Map<Integer, String> contactMap;        // Declare the contactMap as a field of the class

    @FXML private Label systemLister;                           // local computer time dynamic

// ------------ buttons ----------//
    @FXML private Button updateNewApptSaveButton;
    @FXML private Button updateNewApptCancelButton;
    ZoneId localSystemTimeZone = ZoneId.systemDefault();        // sets ZoneID to local System time zone

    // ------------ generate AppointmentID -------------//
    private static int appointmentID;      // appointmentID for addAppointment form to autopopulate form with appointmentID

//    static {
//        try {
//            appointmentID = maxApptID();       // make appointmentID
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

// --------------------- Methods ------------------------//

    /**
     * Method initializable.
     *      sets systemListener Label to local system time zone
     *
     * @param url
     * @param resourceBundle
     * */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        systemLister.setText("Times are listed in " + localSystemTimeZone + " time.");      // set systemListener to gather local computer time zone

        try {
//            int nextAppointmentID = DAO_Appointments.maxApptID() + 1;
//            int currentAppointmentID = DAO_Appointments.maxApptID();         // when updateAppt form generates, appointmentID will be auto populated in form

            updateNewApptAutoGenApptIDTextField.setText(String.valueOf(appointmentID));

        // --------------- customerID combo box -------------//
            // Retrieve customerID from the database
            ObservableList<Integer> customerIDs = DAO_Customers.getAllCustomerIDs();

            // Set the retrieved customer IDs as items in the combo box
            updateNewApptCustomerIDComboBox.setItems(customerIDs);

        // --------------- userID combo box -------------//
            // Retrieve userID from the database
            ObservableList<Integer> userIDs = DAO_Users.getAllUserIDs();

            // Set the retrieved user IDs as items in the combo box
            updateNewApptUserIDComboBox.setItems(userIDs);

        // --------------- contactID combo box -------------//
//            // Retrieve contactID from the database
//            ObservableList<Integer> contactIDs = DAO_Contacts.getAllContactIDs();
//
//            // Set the retrieved contact IDs as items in the combo box
//            updateNewApptContactComboBox.setItems(contactIDs);

            // Retrieve contacts from the database
            ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();

            // Create a map of contact IDs to their corresponding names
            contactMap = contacts.stream().collect(Collectors.toMap(DAO_Contacts::getContactID, DAO_Contacts::getContactName));

            // Set the retrieved contact names as items in the combo box
            updateNewApptContactComboBox.setItems(FXCollections.observableArrayList(contactMap.values()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Generate start times from 7:00 AM to 4:45 PM in 15-minute increments
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(16, 45);

        while (!startTime.isAfter(endTime)) {
            updateNewApptStartTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        // Generate end times from 7:15 AM to 5:00 PM in 15-minute increments, excluding the start time
        LocalTime endTimeExcludingStart = LocalTime.of(7, 15);
        LocalTime endEndTime = LocalTime.of(17, 0);

        while (!endTimeExcludingStart.isAfter(endEndTime)) {
            updateNewApptEndTimeComboBox.getItems().add(endTimeExcludingStart);
            endTimeExcludingStart = endTimeExcludingStart.plusMinutes(15);
        }

        // Set the selection change listener for the start time combo box
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

        updateNewApptDayDatePicker.setValue(appointment.getApptDayPicker());                          // appointment date picker
        updateNewApptStartTimeComboBox.setValue(appointment.getApptStartDateTime().toLocalTime());    // start time combo box
        updateNewApptEndTimeComboBox.setValue(appointment.getApptEndDateTime().toLocalTime());        // end time combo box

        updateNewApptCustomerIDComboBox.setValue(appointment.getApptCustomerID());  // customerID combo box
        updateNewApptUserIDComboBox.setValue(appointment.getApptUserID());          // userID combo box
        updateNewApptContactComboBox.setValue(contactMap.get(appointment.getApptContactID()));  // contact combo box
    }


    @FXML void updateNewApptSaveButtonClick(ActionEvent event) {

        if (blankValues()) {

            try {
                ObservableList<DAO_Contacts> contacts = DAO_Contacts.getAllContacts();
                Map<String, Integer> contactMap = new HashMap<>();

                for (DAO_Contacts contact : contacts) {
                    contactMap.put(contact.getContactName(), contact.getContactID());
                }

                // Retrieve the selected contact name from the combo box
                String selectedContactName = updateNewApptContactComboBox.getValue();

                // Find the corresponding Contact_ID for the selected contact name
                int contactID = contactMap.get(selectedContactName);


                // Retrieve the entered values
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
                Timestamp createDateTime = Timestamp.valueOf(LocalDateTime.now());
                String createdBy = "admin";
                Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdatedBy = "admin";
                int customerID = Integer.parseInt(updateNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString());
                int userID = Integer.parseInt(updateNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString());
//                int contactID = Integer.parseInt(updateNewApptContactComboBox.getSelectionModel().getSelectedItem().toString());

                // Create the SQL update statement
                String sqlUpdate = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlUpdate);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, type);
                preparedStatement.setTimestamp(5, Timestamp.valueOf(startDateTime));
                preparedStatement.setTimestamp(6, Timestamp.valueOf(endDateTime));
                preparedStatement.setTimestamp(7, createDateTime);
                preparedStatement.setString(8, createdBy);
                preparedStatement.setTimestamp(9, lastUpdateDateTime);
                preparedStatement.setString(10, lastUpdatedBy);
                preparedStatement.setInt(11, customerID);
                preparedStatement.setInt(12, userID);
                preparedStatement.setInt(13, contactID);
                preparedStatement.setInt(14, appointmentID);


                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row inserted successfully. (Expecting 1 row inserted)");

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
     * Method: blankValues. checks user input to determine if there is a blank value.
     * @return true. if value is blank alert will display per field that needs to be filled in
     * */
    public boolean blankValues(){

            String title = updateNewApptTitleTextField.getText();               // title
            String type = updateNewApptTypeTextField.getText();                 // type
            String description = updateNewApptDescriptionTextField.getText();   // description
            String location = updateNewApptLocationTextField.getText();         // location

            LocalDate startAndEndDate = updateNewApptDayDatePicker.getValue();                                           // day date picker
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
     * Method: updateNewApptCancelButtonClick. when user clicks cancel, the main appointment/customer screen is displayed again
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
