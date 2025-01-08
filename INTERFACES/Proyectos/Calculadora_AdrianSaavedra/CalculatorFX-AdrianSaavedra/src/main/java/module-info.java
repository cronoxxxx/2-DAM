module org.example.calculatorfxadriansaavedra {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;


    opens org.example.calculatorfxadriansaavedra to javafx.fxml;
    exports org.example.calculatorfxadriansaavedra;
}