package org.example.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.example.config.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
@Log4j2
@Singleton
public class DBConnectionPool {

    private Configuration config;
    private DataSource hikariDataSource;

    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        this.hikariDataSource = this.getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(this.config.getProperty("urlDB"));
        hikariConfig.setUsername(this.config.getProperty("user_name"));
        hikariConfig.setPassword(this.config.getProperty("password"));
        hikariConfig.setDriverClassName(this.config.getProperty("driver"));
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        return new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() {
        Connection con = null;

        try {
            con = this.hikariDataSource.getConnection();
        } catch (SQLException var3) {
            SQLException e = var3;
            log.error("ERROR CONNECTING TO THE DATABASE " + e.getMessage());
        }

        return con;
    }

    public DataSource getDataSource() {
        return this.hikariDataSource;
    }

    public void closeConnection(Connection connArg) {
        try {
            connArg.close();
        } catch (SQLException var3) {
            SQLException e = var3;
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource)this.hikariDataSource).close();
    }
}
