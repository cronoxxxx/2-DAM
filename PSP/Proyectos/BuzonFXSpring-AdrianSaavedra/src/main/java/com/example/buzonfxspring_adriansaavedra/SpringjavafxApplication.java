package com.example.buzonfxspring_adriansaavedra;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class SpringjavafxApplication {

    public static void main(String[] args) {
        Application.launch(DIJavafx.class, args);
    }

}