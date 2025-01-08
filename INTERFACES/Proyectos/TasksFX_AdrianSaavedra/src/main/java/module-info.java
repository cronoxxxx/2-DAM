module org.example.tasksfx_adriansaavedra {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.desktop;

    opens org.example.tasksfx_adriansaavedra to javafx.fxml;
    exports org.example.tasksfx_adriansaavedra;

    exports org.example.tasksfx_adriansaavedra.model;
    exports org.example.tasksfx_adriansaavedra.controllers;
    opens org.example.tasksfx_adriansaavedra.controllers to javafx.fxml;
}