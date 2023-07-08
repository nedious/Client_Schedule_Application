package Main;

import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


/**
 * class Main: opens login screen, connects to database.
 */
public class Main extends Application {

// -------------------- Methods -------------------- //

    /**
     * Method start: opens login screen
     * @param primaryStage primary window opens from fxml
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException{       // Method declaration. Stage = object and primary window to open
        Parent root = FXMLLoader.load(getClass().getResource("/imhoff/dbclientappv8/ViewLogin.fxml"));      // loads Login.fxml. FXMLLoader.load() method returns a Parent object representing the root node of the loaded FXML file, which is then assigned to the root variable.
        primaryStage.setTitle("Global Consulting");                 // sets window title
        primaryStage.setScene(new Scene(root, 500, 400));    // creates a new Scene object with the root as its root node. The Scene constructor takes the root node and sets scene size
        primaryStage.show();                                        // displays the Stage (the main window) on the screen.
    }

    /**
     * Method main: makes a connection to MySQL database
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

//        FruitsQuery.select(3);

        launch(args);

        JDBC.closeConnection();
    }
}
