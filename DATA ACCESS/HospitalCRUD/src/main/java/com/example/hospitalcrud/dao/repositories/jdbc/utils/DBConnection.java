package com.example.hospitalcrud.dao.repositories.jdbc.utils;


import com.example.hospitalcrud.config.Configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.sql.*;
@Log4j2
@Component
public class DBConnection {
    private final Configuration config;
    public DBConnection() {
        this.config = Configuration.getInstance();
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getProperty("dbUrl"), config.getProperty("dbUser"), config.getProperty("dbPass"));
    }
}
