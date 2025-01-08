package com.example.buzonfxspring_adriansaavedra.common.config;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Properties;
@Getter
@Component
@Log4j2
public class Configuracion {
    private String pathJsonGroup;
    private String pathJsonMessages;
    private String pathJsonUsuarios;


    private Configuracion(){

        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            pathJsonGroup = p.getProperty("pathJsonGroup");
            pathJsonMessages = p.getProperty("pathJsonMessages");
            pathJsonUsuarios = p.getProperty("pathJsonUsuarios");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

}
