package Helper;

public class AlertDisplay {

    /**
     * Method: displayAlert displays various alert messages.
     * @param alertType Alert message selector.
     */

//     AlertDisplay.displayAlert(1);
    public static void displayAlert(int alertType) {

        javafx.scene.control.Alert alertError = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

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



