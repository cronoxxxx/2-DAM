package org.example.dao.jdbc.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.common.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
public class DBConnectionPool {

    private Configuration config;
    private DataSource hikariDataSource;
    private BasicDataSource basicDataSource;

    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        this.hikariDataSource = this.getHikariPool();
        this.basicDataSource = this.getBasicPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty("urlDB"));
        hikariConfig.setUsername(config.getProperty("user_name"));
        hikariConfig.setPassword(config.getProperty("password"));
        hikariConfig.setDriverClassName(config.getProperty("driver"));
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        return new HikariDataSource(hikariConfig);
    }

    private BasicDataSource getBasicPool() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(this.config.getProperty("user_name"));
        basicDataSource.setPassword(this.config.getProperty("password"));
        basicDataSource.setUrl(this.config.getProperty("urlDB"));
        return basicDataSource;
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
