package Controller;

import DAO.DAO_Appointments;
import DAO.DAO_Customers;
import Model.Appointments;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

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
     * Method: initialize. Generates appointment table
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
    @FXML void addNewCustomerButtonClick(ActionEvent event) {

    }
    @FXML void deleteSelectedAppointmentButtonClick(ActionEvent event) {

    }
    @FXML void deleteSelectedCustomerButtonClick(ActionEvent event) {

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

    @FXML
    void updateSelectedCustomerButtonClick(ActionEvent event) {

    }

/**
 * Method logoutButtonClick. When user clicks logout, application closes.
 * */
    @FXML void logoutButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

