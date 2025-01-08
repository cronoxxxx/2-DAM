package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    private static Configuration instance = null;
    private Properties p;

    private Configuration() {
        Path p1 = Paths.get("src/main/resources/configFiles/mysql-properties.xml");
        this.p = new Properties();

        try {
            InputStream propertiesStream = Files.newInputStream(p1);
            this.p.loadFromXML(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public String getProperty(String key) {
        return this.p.getProperty(key);
    }
}
