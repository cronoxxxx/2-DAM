module com.example.primerproyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;


    opens com.example.primerproyecto to javafx.fxml;
    exports com.example.primerproyecto;
}