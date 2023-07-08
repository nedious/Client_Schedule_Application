package Controller;

//import DAO.DAO_Appointments;

import DAO.DAO_Users;
import Helper.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * class: LoginController: validates user login credentials, tracks login's to "login_activity.txt",
 * TODO: ... changes locale/language based on OS settings... disables on current system. See if it works with VM. Add note then on first line.
 * class serves as the controller for a specific view in JavaFX app. Controllers are responsible for handling user interactions, updating the view, and performing any necessary business logic.
 *
 * The Initializable interface is part of the JavaFX framework and provides a method called initialize() that is automatically invoked after the FXML file has been loaded and all the associated UI elements have been instantiated.
 * This method is typically used to perform initialization tasks for the controller.
 * By implementing the Initializable interface, the LoginController class is required to provide an implementation for the initialize() method.
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


    /**
     * Method: loginButtonOnActionLogin. Validates user and opens appointment screen
     * @param event user click button
     * @throws SQLException
     * @throws IOException
     * @throws Exception
     */
    @FXML private void loginLoginButtonClick(ActionEvent event) throws SQLException, IOException, Exception {
        try {
            String username = loginUsernameTextField.getText();    // get username text from text field
            String password = loginPasswordField.getText();        // get password text from text field

            boolean validUser = DAO_Users.validateLogin(username, password);      // use DAO_Users.validateLogin() method. returns true / false

            System.out.println("validUser: " + validUser);

            FileWriter loginReport = new FileWriter("login_activity.txt", true);    // creates new FileWriter object. true = new data should be appended to existing file, NOT overwrite it
            PrintWriter printOutput = new PrintWriter(loginReport);                                 // writes output to loginReport file

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

//                ObservableList<Appointments> appointmentsObservableList = DAO_Appointments.getAllAppointments();      // loads database scheduled appointments into observable list

            } else  {
                AlertDisplay.displayAlert(1);       // Alert: Invalid Values. Inherited from helper.AlertDisplay.

//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle(resourceBundle.getString("errorTitle"));
//                alert.setContentText(resourceBundle.getString("errorText"));
//                alert.show();

                printOutput.print("Invalid login for: '" + username + "' at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");  // write to loginReport object

//                printOutput.print("User: " + userInput + " login attempt failed at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

            }

            printOutput.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Method: loginExitButtonOnAction. When user clicks exit button, application closes
     * @param event
     */
    public void loginExitButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Initialize login screen
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            ZoneId zoneId = ZoneId.systemDefault();
            loginTimeZoneDynamicUpdate.setText(String.valueOf(zoneId));

            // TODO: make sure the resourceBundle key value pairs are accurate
            resourceBundle = ResourceBundle.getBundle("Languages/login",Locale.getDefault());
            loginSecureLoginLabel.setText(resourceBundle.getString("login"));
            loginUsernameLabel.setText(resourceBundle.getString("username"));
            loginPasswordLabel.setText(resourceBundle.getString("password"));
            loginLoginButton.setText(resourceBundle.getString("loginButton"));
            loginExitButton.setText(resourceBundle.getString("exitButton"));
            loginTimeZoneLabel.setText(resourceBundle.getString("location"));

        }catch (MissingResourceException e) {
            System.out.println("Resource Bundle missing: " + e);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
