package com.example.buzonfxspring_adriansaavedra;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

@Log4j2
public class DIJavafx extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = new SpringApplicationBuilder(SpringjavafxApplication.class).run();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));

    }


   public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
