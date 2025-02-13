package com.example.hospitalcrud.common.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Getter
@Log4j2
public class Configuration {
    private String pathJsonUsers;

    public Configuration() {
           try{
               Properties properties = new Properties();
               properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
               pathJsonUsers = properties.getProperty("pathJsonUsers");
           }catch (Exception e){
               log.error(e.getMessage(),e);
           }
    }
}
