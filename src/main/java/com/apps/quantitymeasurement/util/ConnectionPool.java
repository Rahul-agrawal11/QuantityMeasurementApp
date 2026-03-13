package com.apps.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.apps.quantitymeasurement.exception.DatabaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton wrapper around HikariCP connection pool. Provides database
 * connections to the repository layer.
 */
public class ConnectionPool {

    private static final Logger logger
            = Logger.getLogger(ConnectionPool.class.getName());

    private static ConnectionPool instance;
    private HikariDataSource dataSource;

    // ── Constructor ───────────────────────────────────────────────────────────
    private ConnectionPool() {
        initializePool();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    // ── Initialize ────────────────────────────────────────────────────────────
    private void initializePool() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        try {
            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setDriverClassName(
                    config.get(ApplicationConfig.ConfigKey.DB_DRIVER_CLASS));
            hikariConfig.setJdbcUrl(
                    config.get(ApplicationConfig.ConfigKey.DB_URL));
            hikariConfig.setUsername(
                    config.get(ApplicationConfig.ConfigKey.DB_USERNAME));
            hikariConfig.setPassword(
                    config.get(ApplicationConfig.ConfigKey.DB_PASSWORD));

            hikariConfig.setMaximumPoolSize(
                    config.getInt(ApplicationConfig.ConfigKey.HIKARI_MAX_POOL_SIZE, 10));
            hikariConfig.setMinimumIdle(
                    config.getInt(ApplicationConfig.ConfigKey.HIKARI_MIN_IDLE, 2));
            hikariConfig.setConnectionTimeout(
                    config.getLong(ApplicationConfig.ConfigKey.HIKARI_CONNECTION_TIMEOUT, 30000));
            hikariConfig.setIdleTimeout(
                    config.getLong(ApplicationConfig.ConfigKey.HIKARI_IDLE_TIMEOUT, 600000));
            hikariConfig.setMaxLifetime(
                    config.getLong(ApplicationConfig.ConfigKey.HIKARI_MAX_LIFETIME, 1800000));
            hikariConfig.setPoolName(
                    config.get(ApplicationConfig.ConfigKey.HIKARI_POOL_NAME));
            hikariConfig.setConnectionTestQuery(
                    config.get(ApplicationConfig.ConfigKey.HIKARI_CONNECTION_TEST_QUERY));

            // PostgreSQL-specific optimizations
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(hikariConfig);

            logger.info("HikariCP connection pool initialized | Pool: "
                    + hikariConfig.getPoolName()
                    + " | MaxSize: " + hikariConfig.getMaximumPoolSize());

        } catch (Exception e) {
            throw DatabaseException.connectionFailed("HikariCP initialization failed", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Connection pool not initialized");
        }
        return dataSource.getConnection();
    }

    public void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("HikariCP connection pool closed");
        }
    }
}