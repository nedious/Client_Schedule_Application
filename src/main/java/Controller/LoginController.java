package Controller;

//import DAO.DAO_Appointments;

import DAO.DAO_Appointments;
import DAO.DAO_Users;
import Helper.AlertDisplay;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class: LoginController: validates user login credentials, tracks login's to "login_activity.txt",
 *
 * TODO: ... changes locale/language based on OS settings... disables on current system. See if it works with VM. Add note then on first line.
 *
 *      class serves as the controller for a specific view in JavaFX app. Controllers are responsible for handling user interactions, updating the view, and performing any necessary business logic.
 *
 *      The Initializable interface is part of the JavaFX framework and provides a method called initialize() that is automatically invoked after the FXML file has been loaded and all the associated UI elements have been instantiated.
 *      This method is typically used to perform initialization tasks for the controller.
 *      By implementing the Initializable interface, the LoginController class is required to provide an implementation for the initialize() method.
 */
public class LoginController implements Initializable {

    @FXML private Label loginSecureLoginLabel;      // secure login label
    // ----------- //
    @FXML private Label loginUsernameLabel;         // username label
    @FXML private Label loginPasswordLabel;         // password label
    // ----------- //
    @FXML private TextField loginUsernameTextField; // username text field
    @FXML private TextField loginPasswordField;     // password, password field
    // ----------- //
    @FXML private Button loginLoginButton;          // login's login button
    @FXML private Button loginExitButton;           // login's exit button
    // ----------- //
    @FXML private Label loginTimeZoneLabel;         // time zone label
    @FXML private Label loginTimeZoneDynamicUpdate; // dynamic local time zone
    // ----------- //

// ------------ Methods -------------//
    /**
     * Method: loginButtonOnActionLogin. Validates user and opens appointment/customer display screen
     * @param event user click button
     * @throws SQLException
     * @throws IOException
     * @throws Exception
     */
    @FXML private void loginLoginButtonClick(ActionEvent event) throws SQLException, IOException, Exception {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("translation", Locale.getDefault());

            String username = loginUsernameTextField.getText();    // get username text from text field
            String password = loginPasswordField.getText();        // get password text from text field

            boolean validUser = DAO_Users.validateLogin(username, password);      // use DAO_Users.validateLogin() method. returns true / false

            System.out.println("validUser: " + validUser);

            FileWriter loginReport = new FileWriter("login_activity.txt", true);    // creates new FileWriter object. true = new data should be appended to existing file, NOT overwrite it
            PrintWriter printOutput = new PrintWriter(loginReport);                                 // writes output to loginReport file

            ObservableList<Appointments> getAllAppointments = DAO_Appointments.getAllAppointments();

            LocalDateTime timePlus15 = LocalDateTime.now().plusMinutes(15);     // bookmark for 15 min in future
            LocalDateTime timeMinus1 = LocalDateTime.now().minusMinutes(1);     // bookmark for 1 min in past to ensure the entire current time minute is captured

            LocalDateTime appointmentStartTime;
            int upcomingApptID = 0;
            LocalDateTime showApptStartTime = null;
            String apptHourMinTime = "";
            boolean validApptTime = false;

            if (validUser) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/imhoff/dbclientappv8/ViewAppointmentsCustomers.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) loginLoginButton.getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();

                System.out.println("validUser: " + validUser);

                printOutput.print("Valid login for: '" + username + "' at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n"); // write to loginReport object

                // ------------ logic to check for upcoming appointments within 15 min of logging on ---------//
                for (Appointments appointmentWithin15Min: getAllAppointments) {
                    appointmentStartTime = appointmentWithin15Min.getApptStartDateTime();
                    if ((appointmentStartTime.isBefore(timePlus15) || (appointmentStartTime.isEqual(timePlus15))) && (appointmentStartTime.isAfter(timeMinus1) || appointmentStartTime.isEqual(timeMinus1))) {
                        upcomingApptID = appointmentWithin15Min.getApptID();
                        showApptStartTime = appointmentStartTime;

                        DateTimeFormatter hourMinuteTime = DateTimeFormatter.ofPattern("HH:mm");
                        apptHourMinTime = showApptStartTime.format(hourMinuteTime);

                        validApptTime = true;
                    }
                }
                if (validApptTime !=false){
                    Alert alertUpcomingAppointment = new Alert(Alert.AlertType.CONFIRMATION);
                    alertUpcomingAppointment.setTitle("Upcoming Appointment");
                    alertUpcomingAppointment.setHeaderText("Upcoming Appointment within 15 minutes");
                    alertUpcomingAppointment.setContentText("Appointment ID:  " + upcomingApptID + "\n\nScheduled for:  " + apptHourMinTime);
                    alertUpcomingAppointment.showAndWait();
                }
                else {
                    AlertDisplay.displayAlert(21);
                }
            } else  {
//                AlertDisplay.displayAlert(1);       // Alert: Invalid Values. Inherited from helper.AlertDisplay.

//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle(resourceBundle.getString("errorTitle"));
//                alert.setContentText(resourceBundle.getString("errorText"));
//                alert.show();

                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle(resourceBundle.getString("errorTitle"));
                alertError.setContentText(resourceBundle.getString("errorText"));
                alertError.showAndWait();

                printOutput.print("Invalid login for: '" + username + "' at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");  // write to loginReport object
            }
            printOutput.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Method: loginExitButtonClick. When user clicks exit button, application closes
     * @param event
     */
    public void loginExitButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Method: initialize. generates data for login screen. Allows text to be translated to french via resource bundle based on the local of the user. Genarates user location and time zone.
     * @param url
     * @param rb
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        // Load the appropriate resource bundle based on the user's locale
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
        ZoneId zoneId = ZoneId.systemDefault();
        loginTimeZoneDynamicUpdate.setText(String.valueOf(zoneId));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("translation", locale);

        if (locale.getLanguage().equals("fr")){
            System.out.println(resourceBundle.getString("Username") + " : " + resourceBundle.getString("Nom d'utilisateur"));
        }

        loginSecureLoginLabel.setText(resourceBundle.getString("loginSecureLoginLabel"));
        loginUsernameLabel.setText(resourceBundle.getString("loginUsernameLabel"));
        loginPasswordLabel.setText(resourceBundle.getString("loginPasswordLabel"));
        loginLoginButton.setText(resourceBundle.getString("loginLoginButton"));
        loginExitButton.setText(resourceBundle.getString("loginExitButton"));
        loginTimeZoneLabel.setText(resourceBundle.getString("loginTimeZoneLabel"));
    }
    }
