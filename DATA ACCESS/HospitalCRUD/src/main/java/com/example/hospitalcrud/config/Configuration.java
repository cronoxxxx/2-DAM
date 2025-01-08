
package com.example.hospitalcrud.config;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;


import java.util.Properties;
@Log4j2
public class Configuration {

    private static Configuration instance=null;
    private Properties p;

    private Configuration() {
        try {
            p = new Properties();
            p.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("config/properties.xml"));
        } catch (IOException e) {
            log.error("Error handling properties {}",e.getMessage());
        }
    }

    public static Configuration getInstance() {

        if (instance==null) {
            instance=new Configuration();
        }
        return instance;
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }
    public void setProperty(String key, String value) {
        p.setProperty(key, value);
    }



}