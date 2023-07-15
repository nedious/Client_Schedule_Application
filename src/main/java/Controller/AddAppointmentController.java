package Controller;

import DAO.*;
import Helper.AlertDisplay;
import Helper.JDBC;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.DAO_Appointments.maxApptID;

public class AddAppointmentController implements Initializable {
    @FXML private TextField addNewApptAutoGenApptIDTextField;   // apptID
    @FXML private TextField addNewApptTitleTextField;           // title
    @FXML private TextField addNewApptTypeTextField;            // type
    @FXML private TextField addNewApptDescriptionTextField;     // description
    @FXML private TextField addNewApptLocationTextField;        // location

    @FXML private DatePicker addNewApptDayDatePicker;           // start date picker
    @FXML private ComboBox<LocalTime> addNewApptStartTimeComboBox;      // start time combo box
//    @FXML private DatePicker addNewApptEndDatePicker;                  // end date picker         DELETE??
    @FXML private ComboBox<LocalTime> addNewApptEndTimeComboBox;        // end time combo box

    @FXML private ComboBox<Integer> addNewApptCustomerIDComboBox;     // customerID combo box
    @FXML private ComboBox<Integer> addNewApptUserIDComboBox;         // userID combo box
    @FXML private ComboBox<Integer> addNewApptContactComboBox;        // contact combo box

    @FXML private Label systemLister;                           // local computer time dynamic

// ------------ buttons ----------//
    @FXML private Button addNewApptSaveButton;
    @FXML private Button addNewApptCancelButton;
    ZoneId localSystemTimeZone = ZoneId.systemDefault();        // sets ZoneID to local System time zone

    // ------------ generate AppointmentID -------------//
    private static int appointmentID;      // appointmentID for addAppointment form to autopopulate form with appointmentID

    static {
        try {
            appointmentID = maxApptID();       // make appointmentID
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
            int nextAppointmentID = DAO_Appointments.maxApptID() + 1;        // when newAppt and updateAppt form generates, appointmentID will be auto populated in form

            addNewApptAutoGenApptIDTextField.setText(String.valueOf(nextAppointmentID));

        // --------------- customerID combo box -------------//
            // Retrieve customerID from the database
            ObservableList<Integer> customerIDs = DAO_Customers.getAllCustomerIDs();

            // Set the retrieved customer IDs as items in the combo box
            addNewApptCustomerIDComboBox.setItems(customerIDs);

        // --------------- userID combo box -------------//
            // Retrieve userID from the database
            ObservableList<Integer> userIDs = DAO_Users.getAllUserIDs();

            // Set the retrieved user IDs as items in the combo box
            addNewApptUserIDComboBox.setItems(userIDs);

        // --------------- contactID combo box -------------//
            // Retrieve contactID from the database
            ObservableList<Integer> contactIDs = DAO_Contacts.getAllContactIDs();

            // Set the retrieved contact IDs as items in the combo box
            addNewApptContactComboBox.setItems(contactIDs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Generate start times from 7:00 AM to 4:45 PM in 15-minute increments
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(16, 45);

        while (!startTime.isAfter(endTime)) {
            addNewApptStartTimeComboBox.getItems().add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        // Generate end times from 7:15 AM to 5:00 PM in 15-minute increments, excluding the start time
        LocalTime endTimeExcludingStart = LocalTime.of(7, 15);
        LocalTime endEndTime = LocalTime.of(17, 0);

        while (!endTimeExcludingStart.isAfter(endEndTime)) {
            addNewApptEndTimeComboBox.getItems().add(endTimeExcludingStart);
            endTimeExcludingStart = endTimeExcludingStart.plusMinutes(15);
        }

        // Set the selection change listener for the start time combo box
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
    }

    @FXML void addNewApptSaveButtonClick(ActionEvent event) {

        if (blankValues()) {

            try {
                // Retrieve the entered values
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
                Timestamp createDateTime = Timestamp.valueOf(LocalDateTime.now());
                String createdBy = "admin";
                Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdatedBy = "admin";
                int customerID = Integer.parseInt(addNewApptCustomerIDComboBox.getSelectionModel().getSelectedItem().toString());
                int userID = Integer.parseInt(addNewApptUserIDComboBox.getSelectionModel().getSelectedItem().toString());
                int contactID = Integer.parseInt(addNewApptContactComboBox.getSelectionModel().getSelectedItem().toString());

                // Create the SQL insert statement
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
