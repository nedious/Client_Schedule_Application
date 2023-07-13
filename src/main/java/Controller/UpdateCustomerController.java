//package Controller;
//
//import Model.Customers;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import static Controller.AppointmentsCustomersController.updateCustomer;
//
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TextField;
//
//public class UpdateCustomerController implements Initializable{
//    @FXML private TextField updateCustomerAutoGenerateIDNumTextField;
//    @FXML private TextField updateCustomerNameTextField;
//    @FXML private TextField updateCustomerAddressTextField;
//    @FXML private TextField updateCustomerPostalCodeTextField;
//
//    @FXML private ComboBox<?> updateCustomerCountryComboBox;
//    @FXML private ComboBox<?> updateCustomerStateProvinceComboBox;
//
//    @FXML private TextField updateCustomerPhoneNumberTextField;
//
//// --- buttons --- //
//    @FXML private Button updateCustomerSaveButton;
//    @FXML private Button updateCustomerCancelButton;
//
//
//    private ObservableList<Customers> customersObservableList = FXCollections.observableArrayList();
//
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        modifyProductTable.setItems(Inventory.getAllParts());
//        modifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        //add parts to associated parts table below product table
//        associatedProductTable.setItems(associatedPartsList);
//        associatedProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
//    }
//
//    @FXML void updateCustomerCancelButtonClick(ActionEvent event) {
//
//    }
//    @FXML void updateCustomerCountryComboBoxSelectionChange(ActionEvent event) {
//
//    }
//
//    @FXML
//    void updateCustomerSaveButtonClick(ActionEvent event) {
//
//    }
//
//}
