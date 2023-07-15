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
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.DAO_Customers.maxID;

/**
 * class: AddCustomerController: allows input from user to generate new customer data
 * */
public class AddCustomerController implements Initializable {
    @FXML private TextField addCustomerAutoGenerateIDNumTextField;
    @FXML private TextField addCustomerNameTextField;
    @FXML private TextField addCustomerAddressTextField;
    @FXML private TextField addCustomerPostalCodeTextField;
    @FXML private ComboBox<String> addCustomerCountryComboBox;
    @FXML private ComboBox<String> addCustomerStateProvinceComboBox;
    @FXML private TextField addCustomerPhoneNumberTextField;

    // ---------- Save and Cancel buttons ---------- //
    @FXML private Button addCustomerSaveButton;
    @FXML private Button addCustomerCancelButton;

    // ------------ generate customerID -------------//
    private static int customerID;      // customerID for addCustomer form to autopopulate form with customerID

    static {
        try {
            customerID = maxID();       // make customerID
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // --------------- Methods -------------- //

    /**
     * /**
     * Method: initialize. generates customersTable values. generates appropriate customerID value. has lambda expression to populate state and country names for combo-boxes
     * lambda expression that allows stateProvinceDivisionNames and countryNames to populate in observable list that is used in customer combo-box. Name data is gathered from first_level_divisions table and countries table.
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {

        addCustomerAutoGenerateIDNumTextField.setText(String.valueOf(customerID));

        try {
            JDBC.getConnection();

        // --------- STATE/PROVINCE table to observable List ------- //
            ObservableList<DAO_StateProvinceDivision> allDivisionsList = DAO_StateProvinceDivision.getAllStateProvinceDivision();
            ObservableList<String> allStateProvinceDivisionsNames = FXCollections.observableArrayList();

            allDivisionsList.forEach(division -> allStateProvinceDivisionsNames.add(division.getStateProvinceDivisionName()));       // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box

            addCustomerStateProvinceComboBox.setItems(allStateProvinceDivisionsNames);

        // --------- COUNTRIES table to observable List ------- //
            ObservableList<DAO_Countries> allCountriesList = DAO_Countries.getAllCountries();
            ObservableList<String> allCountryNames = FXCollections.observableArrayList();

            allCountriesList.forEach(country -> allCountryNames.add(country.getCountryName()));       // lambda expression that allows countryNames to populate in observable list that is used in customer combo-box

            addCustomerCountryComboBox.setItems(allCountryNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: addCustomerCountryComboBoxSelectionChange. Creates  observable list of division names, makes sub observable lists for US,UK, and Canada, those are assigned per the country code
     * reusing lambda expression from initializable() method to generate observable list of division names
     * lambda expression assigns division names to specific observale lists per matching country ID and the list is refined to display in combo box drop down, ex: US country = states displayed.
     * @param event
     * @throws SQLException
     */
    @FXML public void addCustomerCountryComboBoxSelectionChange(ActionEvent event) throws SQLException {
        try {
            JDBC.getConnection();

            // --------- STATE/PROVINCE table to observable List ------- //
            ObservableList<DAO_StateProvinceDivision> allDivisionsList = DAO_StateProvinceDivision.getAllStateProvinceDivision();
            ObservableList<String> allStateProvinceDivisionsNames = FXCollections.observableArrayList();

            allDivisionsList.forEach(division -> allStateProvinceDivisionsNames.add(division.getStateProvinceDivisionName()));       // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box

            addCustomerStateProvinceComboBox.setItems(allStateProvinceDivisionsNames);

            // ------------ individual observable Lists for US, UK, and Canada  -------- //
            ObservableList<String> stateUS = FXCollections.observableArrayList();
            ObservableList<String> divisionUK = FXCollections.observableArrayList();
            ObservableList<String> provinceCanada = FXCollections.observableArrayList();

            // ------------ lambda expression -
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
            String selectedItem = addCustomerCountryComboBox.getSelectionModel().getSelectedItem();

            if (selectedItem.equals("U.S")) {
                addCustomerStateProvinceComboBox.setItems(stateUS);
            } else if (selectedItem.equals("UK")) {
                addCustomerStateProvinceComboBox.setItems(divisionUK);
            } else if (selectedItem.equals("Canada")) {
                addCustomerStateProvinceComboBox.setItems(provinceCanada);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method: addCustomerSaveButtonClick. when user clicks button the data is saved. if there are empty fields user will be notified and prompted to enter data
     *  lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box
     * */
    @FXML void addCustomerSaveButtonClick(ActionEvent event) {
        try {
            // --------- STATE/PROVINCE table to observable List ------- //
            ObservableList<DAO_StateProvinceDivision> allDivisionsList = DAO_StateProvinceDivision.getAllStateProvinceDivision();
            ObservableList<String> allStateProvinceDivisionsNames = FXCollections.observableArrayList();

            allDivisionsList.forEach(division -> allStateProvinceDivisionsNames.add(division.getStateProvinceDivisionName()));       // lambda expression that allows stateProvinceDivisionNames to populate in observable list that is used in customer combo-box

            addCustomerStateProvinceComboBox.setItems(allStateProvinceDivisionsNames);

            // ------------------- //

            String customerName = addCustomerNameTextField.getText();
            String customerAddress = addCustomerAddressTextField.getText();
            String customerPostalCode = addCustomerPostalCodeTextField.getText();
            String customerCountry = addCustomerCountryComboBox.getValue();
            String customerState = addCustomerStateProvinceComboBox.getValue();
            String customerPhoneNum = addCustomerPhoneNumberTextField.getText();

            if(blankValues(customerName, customerAddress, customerPostalCode, customerCountry, customerState, customerPhoneNum)){

                int divisionID = 0;     // state/province/division ID num that corresponds to state name
//                System.out.println("divisionID:  " + divisionID);

            //  The loop iterates over each DAO_StateProvinceDivision object in the collection returned by getAllStateProvinceDivision() method.
            for (DAO_StateProvinceDivision division : DAO_StateProvinceDivision.getAllStateProvinceDivision()) {
//                System.out.println("NAME: division.getStateProvinceDivisionName() :::  " + division.getStateProvinceDivisionName());
//                System.out.println("State NAME  :::  " + division.getStateProvinceDivisionName());

//                System.out.println("COMBOBOX: addCustomerStateProvinceComboBox.getValue() :::  " + addCustomerStateProvinceComboBox.getValue());
//                System.out.println("Value COMBOBOX:  " + addCustomerStateProvinceComboBox.getValue());
                if (addCustomerStateProvinceComboBox.getValue().equals(division.getStateProvinceDivisionName())) {      // if the combobox selection is equal to the state/province name from the division ordered list, then divisionID is set to division.getStateProvinceDivisionID();
                    divisionID = division.getStateProvinceDivisionID();
//                    System.out.println("divisionID now assigned: " + divisionID);
                    break;
                }
            }

            String sqlInsert = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";

            Integer customerIDInt = maxID() + 1;       // maxID() pulls from DAO_Customers and finds max value in Customer_ID column
//            System.out.println("customerIDInt: " + maxID());

            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlInsert);

            preparedStatement.setInt(1, customerIDInt);
            preparedStatement.setString(2, addCustomerNameTextField.getText());
            preparedStatement.setString(3, addCustomerAddressTextField.getText());
            preparedStatement.setString(4, addCustomerPostalCodeTextField.getText());
            preparedStatement.setString(5, addCustomerPhoneNumberTextField.getText());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, "admin");
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, "admin");
            preparedStatement.setInt(10, divisionID);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row inserted successfully. (Expecting 1 row inserted)");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) addCustomerSaveButton.getScene().getWindow();
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
     * Method: blankValues. checks user input to determine if there is a blank value.
     * @return true. if value is blank alert will display per field that needs to be filled in
     * */
    public boolean blankValues(
            String addCustomerNameTextField,
            String addCustomerAddressTextField,
            String addCustomerPostalCodeTextField,
            String addCustomerCountryComboBox,
            String addCustomerStateProvinceComboBox,
            String addCustomerPhoneNumberTextField
    ) {
        if(addCustomerNameTextField.equals("")){AlertDisplay.displayAlert(3); return false;}
        else if(addCustomerAddressTextField.equals("")){AlertDisplay.displayAlert(4);return false;}
        else if(addCustomerPostalCodeTextField.equals("")){AlertDisplay.displayAlert(5);return false;}
        else if(addCustomerCountryComboBox == null){AlertDisplay.displayAlert(6);return false;}
        else if(addCustomerStateProvinceComboBox == null){AlertDisplay.displayAlert(7);return false;}
        else if(addCustomerPhoneNumberTextField.equals("")){AlertDisplay.displayAlert(8);return false;}
        return true;
    }

    /**
     * Method addCustomerCancelButtonClick. When user clicks, they are prompted by alert if they wish to discard entered data. If yes, they are redirected to Appt and customer table screen
     * @param event user click
     * @throws IOException
     * */
    @FXML void addCustomerCancelButtonClick(ActionEvent event) throws IOException {
        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertCancel.setTitle("Canceling");
        alertCancel.setHeaderText("Do you want to Cancel?");
        alertCancel.setContentText("Customer data will not be saved");
        Optional<ButtonType> result = alertCancel.showAndWait();

        if(result.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }
}
