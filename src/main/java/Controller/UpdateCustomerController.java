package Controller;

import DAO.DAO_Countries;
import DAO.DAO_StateProvinceDivision;
import Helper.AlertDisplay;
import Helper.JDBC;
import Model.Customers;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class: UpdateCustomerController. Allows input from user to update customer data. Customer selected data is autopopulated when form opens
 * */
public class UpdateCustomerController implements Initializable {
    @FXML private TextField updateCustomerAutoGenerateIDNumTextField;
    @FXML private TextField updateCustomerNameTextField;
    @FXML private TextField updateCustomerAddressTextField;
    @FXML private TextField updateCustomerPostalCodeTextField;
    @FXML private ComboBox<String> updateCustomerCountryComboBox;
    @FXML private ComboBox<String> updateCustomerStateProvinceComboBox;
    @FXML private TextField updateCustomerPhoneNumberTextField;

// ---------- Save and Cancel buttons -------- -- //
    @FXML private Button updateCustomerSaveButton;
    @FXML private Button updateCustomerCancelButton;
    private static int customerID;      // customerID for UPDATE Customer form

// --------------- Methods -------------- //

    /**
     * Method: initialize. generates customersTable values. generates appropriate customerID value. has lambda expression to populate state and country names for combo-boxes
     *      // lambda expression that allows stateProvinceDivisionNames and countryNames to populate in observable list
     *      that is used in customer combo-box. Name data is gathered from first_level_divisions table and countries table.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        updateCustomerAutoGenerateIDNumTextField.setText(String.valueOf(customerID));       // allows customerID field to be displayed to user when adding/modifying customer

        try {
            JDBC.getConnection();

        // --------- STATE/PROVINCE table to observable List ------- //
            ObservableList<DAO_StateProvinceDivision> allDivisionsList = DAO_StateProvinceDivision.getAllStateProvinceDivision();
            ObservableList<String> allStateProvinceDivisionsNames = FXCollections.observableArrayList();

            allDivisionsList.forEach(division -> allStateProvinceDivisionsNames.add(division.getStateProvinceDivisionName()));       // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box

            updateCustomerStateProvinceComboBox.setItems(allStateProvinceDivisionsNames);

        // --------- COUNTRIES table to observable List ------- //
            ObservableList<DAO_Countries> allCountriesList = DAO_Countries.getAllCountries();
            ObservableList<String> allCountryNames = FXCollections.observableArrayList();

            allCountriesList.forEach(country -> allCountryNames.add(country.getCountryName()));       // lambda expression that allows countryNames to populate in observable list that is used in customer combo-box

            updateCustomerCountryComboBox.setItems(allCountryNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: populateFieldsWithCustomer. When user clicks update customer in appointmentsCustomerController, this method populates the values of the UPDATE customer form
     * @param customer
     * */
    public void populateFieldsWithCustomer(Customers customer) {
        updateCustomerAutoGenerateIDNumTextField.setText(String.valueOf(customer.getCustomerID()));
        updateCustomerNameTextField.setText(customer.getCustomerName());
        updateCustomerAddressTextField.setText(customer.getCustomerAddress());
        updateCustomerPostalCodeTextField.setText(customer.getCustomerPostalCode());
        updateCustomerCountryComboBox.setValue(customer.getCustomerCountry());
        updateCustomerStateProvinceComboBox.setValue(customer.getStateProvinceDivisionID());
        updateCustomerPhoneNumberTextField.setText(customer.getCustomerPhoneNumber());
    }

    /**
     * Method: updateCustomerCountryComboBoxSelectionChange. Creates  observable list of division names, makes sub observable lists for US,UK, and Canada, those are assigned per the country code
     *      //reusing lambda expression from initializable() method to generate observable list of division names
     *      //lambda expression assigns division names to specific observale lists per matching country ID and the list is refined to display in combo box drop down, ex: US country = states displayed.
     * @param event
     * @throws SQLException
     */
    @FXML public void updateCustomerCountryComboBoxSelectionChange(ActionEvent event) throws SQLException {
        try {
            JDBC.getConnection();

        // --------- STATE/PROVINCE table to observable List ------- //
            ObservableList<DAO_StateProvinceDivision> allDivisionsList = DAO_StateProvinceDivision.getAllStateProvinceDivision();
            ObservableList<String> allStateProvinceDivisionsNames = FXCollections.observableArrayList();

            allDivisionsList.forEach(division -> allStateProvinceDivisionsNames.add(division.getStateProvinceDivisionName()));       // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box

            updateCustomerStateProvinceComboBox.setItems(allStateProvinceDivisionsNames);

        // ------------ individual observable Lists for US, UK, and Canada  -------- //
            ObservableList<String> stateUS = FXCollections.observableArrayList();
            ObservableList<String> divisionUK = FXCollections.observableArrayList();
            ObservableList<String> provinceCanada = FXCollections.observableArrayList();

// ------------ lambda expression checks countryID and sets individual observable list to comboBox for state/province/division ------------ //
            allDivisionsList.forEach(division -> {
                if (division.getCountryID() == 1) {
                    stateUS.add(division.getStateProvinceDivisionName());
                } else if (division.getCountryID() == 2) {
                    divisionUK.add(division.getStateProvinceDivisionName());
                } else if (division.getCountryID() == 3) {
                    provinceCanada.add(division.getStateProvinceDivisionName());
                }
            });

        // ------------ when countryComboBox item is selected, the following stateProvinceComboBox is updated to match country options ------//
            String selectedItem = updateCustomerCountryComboBox.getSelectionModel().getSelectedItem();

            if (selectedItem.equals("U.S")) {
                updateCustomerStateProvinceComboBox.setItems(stateUS);
            } else if (selectedItem.equals("UK")) {
                updateCustomerStateProvinceComboBox.setItems(divisionUK);
            } else if (selectedItem.equals("Canada")) {
                updateCustomerStateProvinceComboBox.setItems(provinceCanada);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method: updateCustomerSaveButtonClick. when user clicks button the data is saved. if there are empty fields user will be notified and prompted to enter data
     *      // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box
     * @param event user click
     * */
    @FXML
    void updateCustomerSaveButtonClick(ActionEvent event) {
        try {
            int customerID = Integer.parseInt(updateCustomerAutoGenerateIDNumTextField.getText());            // Retrieve the customer ID from the form

            // Retrieve the values from the form fields
            String customerName = updateCustomerNameTextField.getText();
            String customerAddress = updateCustomerAddressTextField.getText();
            String customerPostalCode = updateCustomerPostalCodeTextField.getText();
            String customerCountry = updateCustomerCountryComboBox.getValue();
            String customerState = updateCustomerStateProvinceComboBox.getValue();
            String customerPhoneNum = updateCustomerPhoneNumberTextField.getText();

            if (blankValues(customerName, customerAddress, customerPostalCode, customerCountry, customerState, customerPhoneNum)) {

                int divisionID = 0;

                for (DAO_StateProvinceDivision division : DAO_StateProvinceDivision.getAllStateProvinceDivision()) {
                    if (customerState.equals(division.getStateProvinceDivisionName())) {
                        divisionID = division.getStateProvinceDivisionID();
                        break;
                    }
                }

                // Create the SQL update statement with column names and preparedStatement '?' placeholders
                String sqlUpdate =
                        "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, Last_Updated_By=?, Division_ID=? " +
                        "WHERE Customer_ID=?";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlUpdate);

                preparedStatement.setString(1, customerName);
                preparedStatement.setString(2, customerAddress);
                preparedStatement.setString(3, customerPostalCode);
                preparedStatement.setString(4, customerPhoneNum);
                preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setString(6, "admin");
                preparedStatement.setInt(7, divisionID);
                preparedStatement.setInt(8, customerID);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row updated successfully. (Expecting 1 row updated)");

                // Redirect back to the customer/Appointment list view
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) updateCustomerSaveButton.getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method: blankValues. Checks user input to determine if there is a blank value.
     * @return true. If value is blank alert will display per field that needs to be filled in
     * */
    public boolean blankValues(
            String addCustomerNameTextField,
            String addCustomerAddressTextField,
            String addCustomerPostalCodeTextField,
            String addCustomerCountryComboBox,
            String addCustomerStateProvinceComboBox,
            String addCustomerPhoneNumberTextField
    ) {
        if(addCustomerNameTextField.equals("")){AlertDisplay.displayAlert(3);return false;}               // name
        else if(addCustomerAddressTextField.equals("")){AlertDisplay.displayAlert(4);return false;}       // address
        else if(addCustomerPostalCodeTextField.equals("")){AlertDisplay.displayAlert(5);return false;}    // postal code
        else if(addCustomerCountryComboBox == null){AlertDisplay.displayAlert(6);return false;}           // country combo box
        else if(addCustomerStateProvinceComboBox == null){AlertDisplay.displayAlert(7);return false;}     // state combo box
        else if(addCustomerPhoneNumberTextField.equals("")){AlertDisplay.displayAlert(8);return false;}   // phone number
        return true;
    }

    /**
     * Method updateCustomerCancelButtonClick. When user clicks, they are prompted by alert if they wish to discard entered data. If yes, they are redirected to Appt and customer table screen
     * @param event user click
     * @throws IOException
     * */
    @FXML void updateCustomerCancelButtonClick(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertCancel.setTitle("Canceling");
        alertCancel.setHeaderText("Do you want to Cancel?");
        alertCancel.setContentText("Customer data will not be saved");
        Optional<ButtonType> result = alertCancel.showAndWait();

        if(result.get() == ButtonType.OK){            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) updateCustomerCancelButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }

}
