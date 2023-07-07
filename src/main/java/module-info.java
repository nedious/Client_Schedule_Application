module imhoff.dbclientappv8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens imhoff.dbclientappv8 to javafx.fxml;
    exports imhoff.dbclientappv8;
}