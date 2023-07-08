package Controller;

import DAO.DAO_Appointments;
import Model.Appointments;
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

    // TODO: organize appointment and customer fxid's

    @FXML private Button addNewAppointmentButton;
    @FXML private Button addNewCustomerButton;

    @FXML private RadioButton allAppointmentsRadio;
    @FXML private RadioButton currentMonthRadio;
    @FXML private RadioButton currentWeekRadio;

    // appointment table (columns)
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

    // customer table
    @FXML private TableColumn<?, ?> customerAddress;
    @FXML private TableColumn<?, ?> customerCountry;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TableColumn<?, ?> customerName;
    @FXML private TableColumn<?, ?> customerPhoneNumber;
    @FXML private TableColumn<?, ?> customerPostalCode;
    @FXML private TableColumn<?, ?> customerProvince;
    @FXML private TableView<?> customerMainTable;
    @FXML private Button deleteSelectedAppointmentButton;
    @FXML private Button deleteSelectedCustomerButton;
    @FXML private Button logoutButton;
    @FXML private Button reportsButton;
    @FXML private Button updateSelectedAppointmentButton;
    @FXML private Button updateSelectedCustomerButton;


    /**
     * Method: initialize. Generates appointment table
     * @throws SQLException
     */
    public void initialize() throws SQLException{
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
    }

    @FXML void addNewAppointmentButtonClick(ActionEvent event) {

    }
    @FXML void addNewCustomerButtonClick(ActionEvent event) {

    }
    @FXML void deleteSelectedAppointmentButtonClick(ActionEvent event) {

    }
    @FXML void deleteSelectedCustomerButtonClick(ActionEvent event) {

    }
    @FXML void logoutButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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

}

