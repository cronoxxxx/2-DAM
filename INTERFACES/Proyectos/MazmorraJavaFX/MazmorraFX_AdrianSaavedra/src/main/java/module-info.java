module org.example.mazmorrafx_adriansaavedra {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.xml.bind;

    exports org.example.mazmorrafx_adriansaavedra;
    exports org.example.mazmorrafx_adriansaavedra.model;  // Add this line

    opens org.example.mazmorrafx_adriansaavedra to javafx.fxml;
    opens org.example.mazmorrafx_adriansaavedra.model to javafx.fxml, java.xml.bind;  // Add this line
}