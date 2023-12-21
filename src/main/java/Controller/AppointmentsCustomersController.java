package Controller;

import DAO.DAO_Appointments;
import DAO.DAO_Contacts;
import DAO.DAO_Customers;

import Helper.AlertDisplay;
import Helper.JDBC;

import Model.Appointments;
import Model.Customers;
import Model.Searchable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * class: AppointmentsCustomersController: displays appointment and customer data in two tables. allows appointment and customer tables to add, update, and delete. generates reports
 * */
public class AppointmentsCustomersController {

// -------------- appointment table (columns) --------------- //
    @FXML private TableView<Appointments> appointmentMainTable;

//    @FXML private TableColumn<?, ?> appointmentID;
//    @FXML private TableColumn<?, ?> appointmentTitle;
//    @FXML private TableColumn<?, ?> appointmentDescription;
//    @FXML private TableColumn<?, ?> appointmentLocation;
//    @FXML private TableColumn<?, ?> appointmentContact;
//    @FXML private TableColumn<?, ?> appointmentType;
//    @FXML private TableColumn<?, ?> appointmentStartDateTime;
//    @FXML private TableColumn<?, ?> appointmentEndDateTime;
//    @FXML private TableColumn<?, ?> appointmentCustomerID;
//    @FXML private TableColumn<?, ?> appointmentUserID;

    @FXML private TableColumn<Appointments, Integer> appointmentID;
    @FXML private TableColumn<Appointments, String> appointmentTitle;
    @FXML private TableColumn<Appointments, String> appointmentDescription;
    @FXML private TableColumn<Appointments, String> appointmentLocation;
    @FXML private TableColumn<Appointments, String> appointmentContact; // Adjusted generic type
    @FXML private TableColumn<Appointments, String> appointmentType;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentStartDateTime;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentEndDateTime;
    @FXML private TableColumn<Appointments, Integer> appointmentCustomerID;
    @FXML private TableColumn<Appointments, Integer> appointmentUserID;


    // search appointment text field
    @FXML public TextField searchAppts;

    // appointment radio buttons
    @FXML private RadioButton allAppointmentsRadio;
    @FXML private RadioButton currentWeekRadio;
    @FXML private RadioButton currentMonthRadio;
    private ToggleGroup toggleGroup;

    // appointment buttons
    @FXML private Button addNewAppointmentButton;
    @FXML private Button updateSelectedAppointmentButton;
    @FXML private Button deleteSelectedAppointmentButton;

// -------------- customer table (columns) --------------- //

    @FXML private TableView<Customers> customerMainTable;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TableColumn<?, ?> customerName;
    @FXML private TableColumn<?, ?> customerAddress;
    @FXML private TableColumn<?, ?> customerPostalCode;
    @FXML private TableColumn<?, ?> customerCountry;
    @FXML private TableColumn<?, ?> stateProvinceDivisionID;
    @FXML private TableColumn<?, ?> customerPhoneNumber;

    // search customer text field
    @FXML public TextField searchCustomers;

    // customer buttons
    @FXML private Button addNewCustomerButton;
    @FXML private Button updateSelectedCustomerButton;
    @FXML private Button deleteSelectedCustomerButton;

    // reports and logout buttons
    @FXML private Button reportsButton;
    @FXML private Button logoutButton;


// --------------- Methods -------------------- //

    /**
     * Method: initialize. Generates appointment table and customer table. groups the radio buttons.
     * @throws SQLException
     */
    public void initialize() throws SQLException{
        // --------- Appointments Table ----------- //

        ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

        appointmentMainTable.setItems(allAppointments);

        appointmentID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("apptContactID"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentStartDateTime.setCellValueFactory(new PropertyValueFactory<>("apptStartDateTime"));
        appointmentEndDateTime.setCellValueFactory(new PropertyValueFactory<>("apptEndDateTime"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));
        appointmentUserID.setCellValueFactory(new PropertyValueFactory<>("apptUserID"));

        appointmentContact.setCellValueFactory(cellData -> {
            int contactID = cellData.getValue().getApptContactID();
            try {
                String contactName = DAO_Contacts.getContactNameByID(contactID);
                return new SimpleStringProperty(contactName);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your needs
                return new SimpleStringProperty("");
            }
        });


        // --------- Customers Table ----------- //

        ObservableList<Customers> allCustomers = DAO_Customers.getAllCustomers();

        customerMainTable.setItems(allCustomers);

        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        stateProvinceDivisionID.setCellValueFactory(new PropertyValueFactory<>("stateProvinceDivisionID"));
        customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));

        // --------- Radio button toggle group ----------- //

        toggleGroup = new ToggleGroup();
        allAppointmentsRadio.setToggleGroup(toggleGroup);
        currentWeekRadio.setToggleGroup(toggleGroup);
        currentMonthRadio.setToggleGroup(toggleGroup);

        // ------------ Set up search listeners ----------- //
        setUpSearchListener(searchAppts, allAppointments, appointmentMainTable);
        setUpSearchListener(searchCustomers, allCustomers, customerMainTable);
    }

    /**
     * Sets up a search listener for a TextField that filters and updates a TableView based on search criteria.
     *
     * @param <T>         The type of items in the TableView, must extend Searchable interface.
     * @param searchField The TextField used for entering search queries.
     * @param allItems    The complete list of items in the TableView.
     * @param tableView   The TableView to be updated based on search results.
     */

    //    Both 'setUpSearchListener' and 'search' are generic methods that operate on any type 'T' that extends the Searchable interface.
    //    This enables the methods to work with different types of objects as long as they adhere to the common interface.
    //    Polymorphism
    private <T extends Searchable> void setUpSearchListener(TextField searchField, ObservableList<T> allItems, TableView<T> tableView) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Trim and convert the search text to lowercase for case-insensitive matching
            String searchText = newValue.trim().toLowerCase();

            if (searchText.isEmpty()) {
                // If search text is empty, display the entire list
                tableView.setItems(allItems);
            } else {
                // Filter the list based on the search text
                ObservableList<T> matchingItems = search(searchText, allItems);

                if (matchingItems.isEmpty()) {
                    // If no matches, display an error alert
                    AlertDisplay.displayAlert(22);
                } else {
                    // Display matching items
                    tableView.setItems(matchingItems);
                }
            }
        });
    }

    /**
     * Performs a generic search on a list of Searchable items based on a query.
     *
     * @param <T>   The type of items in the list, must extend Searchable interface.
     * @param query The search query.
     * @param items The list of items to be searched.
     * @return An ObservableList containing items that match the search query.
     */
    private <T extends Searchable> ObservableList<T> search(String query, ObservableList<T> items) {
        ObservableList<T> result = FXCollections.observableArrayList();

        for (T item : items) {
            // Check if the searchable text of the item contains the query (case-insensitive)
            if (item.getSearchableText().toLowerCase().contains(query)) {
                result.add(item);
            }
        }

        return result;
    }


    /**
     * Method: addNewAppointmentButtonClick. takes user to AddAppointment screen
     * @param event user click
     * @throws IOException
     * */
    @FXML void addNewAppointmentButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAddNewAppointment.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) addNewAppointmentButton.getScene().getWindow();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method: addNewCustomerButtonClick. takes user to AddNewCustomer screen
     * @param event user click
     * @throws IOException
     * */
    @FXML void addNewCustomerButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAddNewCustomers.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) addNewCustomerButton.getScene().getWindow();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method: deleteSelectedCustomerButtonClick. Deletes selected customer and associated appointment times. Display alert asking user if they want the delete the customer and appointments. **Due to primary key constraints in database, appointments with associated Customer_ID are deleted first, then customer is deleted.
     * @param event user click
     * @throws IOException
     * */
    @FXML void deleteSelectedCustomerButtonClick(ActionEvent event) throws SQLException {

        ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();

        Customers selectedCustomerToDelete = customerMainTable.getSelectionModel().getSelectedItem();       // selectedCustomer data to autopopulate in form

        if(selectedCustomerToDelete == null){
            AlertDisplay.displayAlert(9);
        } else if (selectedCustomerToDelete != null) {

        int customerIDToDelete = customerMainTable.getSelectionModel().getSelectedItem().getCustomerID();

        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertDelete.setTitle("DELETING");
        alertDelete.setHeaderText("Do you want to DELETE the selected CUSTOMER and ALL APPOINTMENTS associated with this customer?");
        alertDelete.setContentText("Customer ID:  " + customerIDToDelete + "\n\nAnd all associated Appointment data will be DELETED");
        Optional<ButtonType> result = alertDelete.showAndWait();


        if (result.isPresent() && result.get() == ButtonType.OK) {
            int selectedCustomerID = customerMainTable.getSelectionModel().getSelectedItem().getCustomerID();

            try {
                for (Appointments appointment: allAppointments) {
                    int associatedAppointmentsWithCustomerID = appointment.getApptCustomerID();
                    int appointmentID = appointment.getApptID();
                    String appointmentType = appointment.getApptType();

                    LocalDateTime appointmentStartTime = appointment.getApptStartDateTime();
                        DateTimeFormatter hourMinuteTime = DateTimeFormatter.ofPattern("HH:mm");
                            String apptHourMinTime = appointmentStartTime.format(hourMinuteTime);

                        if (selectedCustomerID == associatedAppointmentsWithCustomerID) {

                            Alert alertApptDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            alertApptDelete.setTitle("Attention");
                            alertApptDelete.setHeaderText("The following Appointment has been deleted:");
                            alertApptDelete.setContentText("Appointment ID:  " + appointmentID + "\n\nType:  '" + appointmentType + "'\n\nTime:  " + apptHourMinTime);
                            Optional<ButtonType> ok = alertApptDelete.showAndWait();

                            DAO_Appointments.deleteAppointmentWithCustomerID(selectedCustomerID);

                            ObservableList<Appointments> updatedAppointments = DAO_Appointments.getAllAppointments();
                            appointmentMainTable.setItems(updatedAppointments);
                        }
                }

                DAO_Customers.deleteCustomerWithCustomerID(selectedCustomerID);

                ObservableList<Customers> updatedCustomers = DAO_Customers.getAllCustomers();
                customerMainTable.setItems(updatedCustomers);

            } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method: deleteSelectedAppointmentButtonClick. Deletes selected appointment
     * @param event user click
     * @throws IOException
     * */
    @FXML void deleteSelectedAppointmentButtonClick(ActionEvent event) {

        Appointments selectedAppointmentToDelete = appointmentMainTable.getSelectionModel().getSelectedItem();       // selectedCustomer data to autopopulate in form

        if(selectedAppointmentToDelete == null){
            AlertDisplay.displayAlert(20);
        } else if (selectedAppointmentToDelete != null) {

        try {
            int appointmenttIDToDelete = appointmentMainTable.getSelectionModel().getSelectedItem().getApptID();
            String deleteApptType = appointmentMainTable.getSelectionModel().getSelectedItem().getApptType();

            Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertDelete.setTitle("DELETING");
            alertDelete.setHeaderText("Do you want to DELETE the selected APPOINTMENT?");
            alertDelete.setContentText("Appointment ID:  " + appointmenttIDToDelete + "\n\nType:  '" + deleteApptType + "' \n\nWill be DELETED.");
            Optional<ButtonType> result = alertDelete.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                DAO_Appointments.deleteAppointment(appointmenttIDToDelete);

                ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();
                appointmentMainTable.setItems(allAppointments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * Method: updateSelectedCustomerButtonClick. user can select customer and the data will populate in update customer form in order to update customer info
     * @param event
     * @throws SQLException
     */
    @FXML void updateSelectedCustomerButtonClick(ActionEvent event) throws SQLException, IOException {

        Customers selectedCustomer = customerMainTable.getSelectionModel().getSelectedItem();       // selectedCustomer data to autopopulate in form

        if(selectedCustomer == null){
            AlertDisplay.displayAlert(9);
        } else if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/imhoff/dbclientappv8/ViewUpdateCustomer.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) updateSelectedCustomerButton.getScene().getWindow();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.populateFieldsWithCustomer(selectedCustomer);      // populates form with customer data from selected row

            // Opens the AddCustomerController form
            Scene scene = new Scene(parent);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method: updateSelectedAppointmentButtonClick. user can select appointment and the data will populate in update appointment form in order to update appointment info
     * @param event user click
     * @throws IOException
     * */
    @FXML void updateSelectedAppointmentButtonClick(ActionEvent event) throws IOException {

        Appointments selectedAppointment = appointmentMainTable.getSelectionModel().getSelectedItem();

        if(appointmentMainTable.getSelectionModel().getSelectedItem() == null){
            AlertDisplay.displayAlert(20);
        } else if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/imhoff/dbclientappv8/ViewUpdateAppointment.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) updateSelectedCustomerButton.getScene().getWindow();

            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.populateFieldsWithAppointment(selectedAppointment);

            // Opens the AddCustomerController form
            Scene scene = new Scene(parent);
            stage.setTitle("Update Apppointment");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method: selectAllAppointmentsRadio. Displays all appointments by time/date
     * @param event user click
     * */
    @FXML void selectAllAppointmentsRadio(ActionEvent event) {
        try {
            ObservableList<Appointments> allAppointments = DAO_Appointments.getAllAppointments();
            appointmentMainTable.setItems(allAppointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: selectCurrentWeekRadio. Displays only appointments by current week
     * @param event user click
     * */
    @FXML void selectCurrentWeekRadio(ActionEvent event) {
        try {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(6);

            ObservableList<Appointments> appointmentsForCurrentWeek = DAO_Appointments.getAppointmentsForDateRange(startDate, endDate);
            appointmentMainTable.setItems(appointmentsForCurrentWeek);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: selectCurrentMonthRadio. Displays only appointments by current month
     * @param event user click
     * */
    @FXML void selectCurrentMonthRadio(ActionEvent event) {
        try {
            LocalDate startDate = LocalDate.now().withDayOfMonth(1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            ObservableList<Appointments> appointmentsForCurrentMonth = DAO_Appointments.getAppointmentsForDateRange(startDate, endDate);
            appointmentMainTable.setItems(appointmentsForCurrentMonth);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: reportsButtonClick. Generates reports form
     * @param event user click
     * @throws IOException
     * */
    @FXML void reportsButtonClick(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/imhoff/dbclientappv8/ViewReports.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) reportsButton.getScene().getWindow();

        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Reports");
        stage.setScene(scene);
        stage.show();
    }

/**
 * Method logoutButtonClick. When user clicks logout, application closes.
 * @param event user click
 * */
    @FXML void logoutButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

