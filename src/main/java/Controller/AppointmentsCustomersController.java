package Controller;

import DAO.DAO_Appointments;
import DAO.DAO_Countries;
import DAO.DAO_Customers;
import DAO.DAO_StateProvinceDivision;
import Helper.AlertDisplay;
import Helper.JDBC;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
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
import java.sql.SQLException;

/**
 * class: AppointmentsCustomersController: displays appointment and customer data in two tables
 * */
public class AppointmentsCustomersController {

// -------------- appointment table (columns) --------------- //
    @FXML private TableView<Appointments> appointmentMainTable;

    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private TableColumn<?, ?> appointmentTitle;
    @FXML private TableColumn<?, ?> appointmentDescription;
    @FXML private TableColumn<?, ?> appointmentLocation;
    @FXML private TableColumn<?, ?> appointmentContact;
    @FXML private TableColumn<?, ?> appointmentType;
    @FXML private TableColumn<?, ?> appointmentStartDateTime;
    @FXML private TableColumn<?, ?> appointmentEndDateTime;
    @FXML private TableColumn<?, ?> appointmentCustomerID;
    @FXML private TableColumn<?, ?> appointmentUserID;

    // appointment radio buttons
    @FXML private RadioButton allAppointmentsRadio;
    @FXML private RadioButton currentWeekRadio;
    @FXML private RadioButton currentMonthRadio;

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

    // customer buttons
    @FXML private Button addNewCustomerButton;
    @FXML private Button updateSelectedCustomerButton;
    @FXML private Button deleteSelectedCustomerButton;

    // reports and logout buttons
    @FXML private Button reportsButton;
    @FXML private Button logoutButton;


// --------------- Methods -------------------- //

    /**
     * Method: initialize. Generates appointment table and customer table
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
    }

    @FXML void addNewAppointmentButtonClick(ActionEvent event) {

    }

    /**
     * Method: addNewCustomerButtonClick. takes user to AddNewCustomer screen
     * @param event user click
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

    @FXML
    void deleteSelectedCustomerButtonClick(ActionEvent event) {

    }

    /**
     * Method: updateSelectedCustomerButtonClick. user can select customer and the data will populate in customer form in order to update customer info
     * @param event
     * @throws SQLException
     */
    @FXML void updateSelectedCustomerButtonClick(ActionEvent event) throws SQLException, IOException {

        Customers selectedCustomer = customerMainTable.getSelectionModel().getSelectedItem();

        if(customerMainTable.getSelectionModel().getSelectedItem() == null){
            AlertDisplay.displayAlert(9);
        } else if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/imhoff/dbclientappv8/ViewUpdateCustomer.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) updateSelectedCustomerButton.getScene().getWindow();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.populateFieldsWithCustomer(selectedCustomer);

            // Opens the AddCustomerController form
            Scene scene = new Scene(parent);
            stage.setTitle("Update Customer");
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML void deleteSelectedAppointmentButtonClick(ActionEvent event) {

    }

    @FXML void reportsButtonClick(ActionEvent event) {

    }
    @FXML void selectAllAppointmentsRadio(ActionEvent event) {

    }
    @FXML void selectCurrentMonthRadio(ActionEvent event) {

    }

    @FXML
    void selectCurrentWeekRadio(ActionEvent event) {

    }

    @FXML
    void updateSelectedAppointmentButtonClick(ActionEvent event) {

    }

/**
 * Method logoutButtonClick. When user clicks logout, application closes.
 * */
    @FXML void logoutButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

