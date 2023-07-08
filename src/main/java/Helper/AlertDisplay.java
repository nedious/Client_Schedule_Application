package Helper;

public class AlertDisplay {

    /**
     * Method: displayAlert displays various alert messages.
     *
     * @param alertType Alert message selector.
     */

//     Alert.displayAlert(1);
    public static void displayAlert(int alertType) {

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

        switch (alertType) {

// --------------------- login errors --------------------- //
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid values");
                alert.setContentText("Username or Password is invalid.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Problem");
                alert.setContentText("Problem description");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Min");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;

// --------------------- other errors --------------------- //

            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid value for Inventory");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
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
