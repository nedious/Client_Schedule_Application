package Helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * class: AlertDisplay. Displays alerts throughout application
 * */
public class AlertDisplay {

    /**
     * Method: displayAlert displays various alert messages.
     * @param alertType Alert message selector.
     */

//     AlertDisplay.displayAlert(1);
    public static void displayAlert(int alertType) {

        javafx.scene.control.Alert alertError = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        javafx.scene.control.Alert alertWarning = new javafx.scene.control.Alert(Alert.AlertType.WARNING);
        javafx.scene.control.Alert alertConfirmation = new javafx.scene.control.Alert(Alert.AlertType.CONFIRMATION);


        switch (alertType) {
// --------------------- login errors --------------------- //
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Invalid values");
                alertError.setContentText("Username or Password is invalid.");
                alertError.showAndWait();
                break;

// --------------------- add/update Customer errors --------------------- //
            case 2:
                alertError.setTitle("Canceling");
                alertError.setHeaderText("Do you want to Cancel?");
                alertError.setContentText("Customer data will not be saved");
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer name");
                alertError.setContentText("Enter customer name");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer address");
                alertError.setContentText("Enter customer address");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer postal code");
                alertError.setContentText("Enter customer postal code");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer country");
                alertError.setContentText("Enter customer country");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer state/province/division");
                alertError.setContentText("Enter customer state/province/division");
                alertError.showAndWait();
                break;
            case 8:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing customer phone number");
                alertError.setContentText("Enter customer phone number");
                alertError.showAndWait();
                break;
            case 9:
                alertError.setTitle("Error");
                alertError.setHeaderText("No customer selected");
                alertError.setContentText("Select a customer");
                alertError.showAndWait();
                break;
// --------------------- add/update appointment errors --------------------- //
            case 10:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Title");
                alertError.setContentText("Enter Appointment Title");
                alertError.showAndWait();
                break;
            case 11:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Type");
                alertError.setContentText("Enter Appointment Type");
                alertError.showAndWait();
                break;
            case 12:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Description");
                alertError.setContentText("Enter Appointment Description");
                alertError.showAndWait();
                break;
            case 13:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Location");
                alertError.setContentText("Enter appointment Location");
                alertError.showAndWait();
                break;
            case 14:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Date");
                alertError.setContentText("Enter appointment Date");
                alertError.showAndWait();
                break;
            case 15:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Start Time");
                alertError.setContentText("Enter appointment Start Time");
                alertError.showAndWait();
                break;
            case 16:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing End Time");
                alertError.setContentText("Enter appointment End Time");
                alertError.showAndWait();
                break;
            case 17:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Customer ID");
                alertError.setContentText("Enter appointment Customer ID");
                alertError.showAndWait();
                break;
            case 18:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing User ID");
                alertError.setContentText("Enter appointment User ID");
                alertError.showAndWait();
                break;
            case 19:
                alertError.setTitle("Error");
                alertError.setHeaderText("Missing Contact ID");
                alertError.setContentText("Enter appointment Contact ID");
                alertError.showAndWait();
                break;
// --------------------- other errors --------------------- //
            case 20:
                alertError.setTitle("Error");
                alertError.setHeaderText("No appointment selected");
                alertError.setContentText("Select appointment");
                alertError.showAndWait();
                break;
            case 21:
                alertConfirmation.setTitle("No Upcoming Appointments");
                alertConfirmation.setHeaderText("No Upcoming Appointments");
                alertConfirmation.showAndWait();
                break;
        }
    }

}


/**
 * TODO: fix the following alert with resourceBundle
 * Alert alert = new Alert(Alert.AlertType.ERROR);
 * alert.setTitle(resourceBundle.getString("errorTitle"));
 * alert.setContentText(resourceBundle.getString("errorText"));
 * alert.show();
 *   -------------
 *
 * */


//    @FXML void addCustomerCancelButtonClick(ActionEvent event) throws IOException {
//        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
//        alertCancel.setTitle("Canceling");
//        alertCancel.setHeaderText("Do you want to Cancel?");
//        alertCancel.setContentText("Customer data will not be saved");
//        Optional<ButtonType> result = alertCancel.showAndWait();
//
//        if(result.get() == ButtonType.OK){
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
//            Parent parent = loader.load();
//            Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();
//            Scene scene = new Scene(parent);
//            stage.setScene(scene);
//            stage.show();
//        }
//   }


