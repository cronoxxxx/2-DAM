module com.example.pixelart {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pixelart to javafx.fxml;
    exports com.example.pixelart;
}