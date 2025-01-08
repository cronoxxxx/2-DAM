package com.example.hospitalcrud.dao.repositories.jdbc.utils;

import com.example.hospitalcrud.config.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Log4j2
public class DBConnectionPool {

    private final Configuration config;
    private final DataSource hikariDataSource;

    public DBConnectionPool() {
        this.config = Configuration.getInstance();
        hikariDataSource = getHikariPool();

    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty("dbUrl"));
        hikariConfig.setUsername(config.getProperty("dbUser"));
        hikariConfig.setPassword(config.getProperty("dbPass"));
        hikariConfig.setDriverClassName(config.getProperty("driver"));
        hikariConfig.setMaximumPoolSize(4);

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);

        return new HikariDataSource(hikariConfig);
    }


    public Connection getConnection() {
        Connection con = null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error("Cannot connect error {}",e.getMessage());
        }

        return con;
    }


    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }

}