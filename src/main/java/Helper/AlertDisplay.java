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

// --------------------- addCustomer errors --------------------- //
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
// --------------------- errors --------------------- //
            case 9:
                alertError.setTitle("Error");
                alertError.setHeaderText("No customer selected");
                alertError.setContentText("Select customer to update");
                alertError.showAndWait();
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


//    /**
//     * Method addCustomerCancelButtonClick. When user clicks, they are prompted by alert if they wish to discard entered data. If yes, they are redirected to Appt and customer table screen
//     * @param event user click
//     * @throws IOException
//     * */
//    @FXML
//    void addCustomerCancelButtonClick(ActionEvent event) throws IOException {
//        Alert alertCancel = new Alert(Alert.AlertType.CONFIRMATION);
//        alertCancel.setTitle("Canceling");
//        alertCancel.setHeaderText("Do you want to Cancel?");
//        alertCancel.setContentText("Customer data will not be saved");
//        Optional<ButtonType> result = alertCancel.showAndWait();
//
//        if(result.get() == ButtonType.OK){            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
//            Parent parent = loader.load();
//            Stage stage = (Stage) addCustomerCancelButton.getScene().getWindow();
//            Scene scene = new Scene(parent);
//            stage.setScene(scene);
//            stage.show();
//        }
//    }


