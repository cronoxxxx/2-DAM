package com.example.buzonfxspring_adriansaavedra;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Log4j2
@Component
public class MainFX implements ApplicationListener<DIJavafx.StageReadyEvent> {


    private final FXMLLoader fxmlLoader;

    public MainFX(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }


    @Override
    public void onApplicationEvent(DIJavafx.StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Constantes.RUTA_GRAFICA));
            stage.setScene(new Scene(fxmlParent));
            stage.show();
        } catch (IOException e) {
           log.error(e.getMessage(), e);
        }
    }
}
