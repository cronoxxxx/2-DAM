module com.example.miprimeraaplicacionfx_adriansaavedra {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.google.gson;


    exports com.example.miprimeraaplicacionfx_adriansaavedra.ui;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.ui to com.google.gson, javafx.fxml;
    exports com.example.miprimeraaplicacionfx_adriansaavedra.dao;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.dao to com.google.gson, javafx.fxml;
    exports com.example.miprimeraaplicacionfx_adriansaavedra.common;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.common to com.google.gson, javafx.fxml;
    exports com.example.miprimeraaplicacionfx_adriansaavedra.domain.model;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.domain.model to com.google.gson, javafx.fxml;
    exports com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl to com.google.gson, javafx.fxml;
    exports com.example.miprimeraaplicacionfx_adriansaavedra.common.config;
    opens com.example.miprimeraaplicacionfx_adriansaavedra.common.config to com.google.gson, javafx.fxml;
}