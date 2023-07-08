module imhoff.dbclientappv8 {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens Model;

    exports Main;

    opens Controller;
    exports Controller;
}