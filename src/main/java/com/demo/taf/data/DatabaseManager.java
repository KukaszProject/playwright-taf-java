package com.demo.taf.data;

import java.sql.Connection;
import java.sql.SQLException;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
    private static HikariDataSource dataSource;
    private static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();

    private DatabaseManager() {}

    public static synchronized Connection getConnection() throws SQLException {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(CONFIG.dbUrl());
            config.setUsername(CONFIG.dbUser());
            config.setPassword(CONFIG.dbPassword());

            config.setMaximumPoolSize(5);

            dataSource = new HikariDataSource(config);
        }
        return dataSource.getConnection();
    }

    public static synchronized void closePool() {
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
    }
    
}
