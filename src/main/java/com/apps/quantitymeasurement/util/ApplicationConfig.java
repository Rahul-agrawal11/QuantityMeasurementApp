package com.apps.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Singleton config loader.
 * Reads application.properties from classpath.
 * Supports environment-aware configuration via system properties / env vars.
 */
public class ApplicationConfig {

    private static final Logger logger =
            Logger.getLogger(ApplicationConfig.class.getName());

    private static ApplicationConfig instance;
    private Properties properties;
    private Environment environment;

    // ── Enums ─────────────────────────────────────────────────────────────────

    public enum Environment {
        DEVELOPMENT, TESTING, PRODUCTION
    }

    public enum ConfigKey {
        REPOSITORY_TYPE         ("repository.type"),
        DB_DRIVER_CLASS         ("db.driver"),
        DB_URL                  ("db.url"),
        DB_USERNAME             ("db.username"),
        DB_PASSWORD             ("db.password"),
        HIKARI_MAX_POOL_SIZE    ("db.hikari.maximum-pool-size"),
        HIKARI_MIN_IDLE         ("db.hikari.minimum-idle"),
        HIKARI_CONNECTION_TIMEOUT("db.hikari.connection-timeout"),
        HIKARI_IDLE_TIMEOUT     ("db.hikari.idle-timeout"),
        HIKARI_MAX_LIFETIME     ("db.hikari.max-lifetime"),
        HIKARI_POOL_NAME        ("db.hikari.pool-name"),
        HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");

        private final String key;

        ConfigKey(String key) { this.key = key; }

        public String getKey() { return key; }
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    private ApplicationConfig() {
        loadConfiguration();
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    // ── Load ──────────────────────────────────────────────────────────────────

    private void loadConfiguration() {
        properties = new Properties();
        try {
            // Detect environment from system property → env var → default
            String env = System.getProperty("app.env");
            if (env == null || env.isEmpty()) env = System.getenv("APP_ENV");
            if (env == null || env.isEmpty()) env = "development";

            this.environment = Environment.valueOf(env.toUpperCase());

            String configFile = "application.properties";
            InputStream input = ApplicationConfig.class
                    .getClassLoader()
                    .getResourceAsStream(configFile);

            if (input != null) {
                properties.load(input);
                logger.info("Configuration loaded from: " + configFile
                        + " | Environment: " + environment);
            } else {
                logger.warning("application.properties not found. Using defaults.");
                loadDefaults();
            }
        } catch (Exception e) {
            logger.severe("Error loading configuration: " + e.getMessage());
            loadDefaults();
        }
    }

    private void loadDefaults() {
        properties.setProperty(ConfigKey.REPOSITORY_TYPE.getKey(),        "cache");
        properties.setProperty(ConfigKey.DB_DRIVER_CLASS.getKey(),        "org.postgresql.Driver");
        properties.setProperty(ConfigKey.DB_URL.getKey(),
                "jdbc:postgresql://localhost:5432/quantitymeasurementdb");
        properties.setProperty(ConfigKey.DB_USERNAME.getKey(),            "postgres");
        properties.setProperty(ConfigKey.DB_PASSWORD.getKey(),            "postgres");
        properties.setProperty(ConfigKey.HIKARI_MAX_POOL_SIZE.getKey(),   "10");
        properties.setProperty(ConfigKey.HIKARI_MIN_IDLE.getKey(),        "2");
        properties.setProperty(ConfigKey.HIKARI_CONNECTION_TIMEOUT.getKey(), "30000");
        properties.setProperty(ConfigKey.HIKARI_POOL_NAME.getKey(),
                "QuantityMeasurementPool");
        properties.setProperty(ConfigKey.HIKARI_CONNECTION_TEST_QUERY.getKey(), "SELECT 1");
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public String get(ConfigKey key) {
        return properties.getProperty(key.getKey());
    }

    public int getInt(ConfigKey key, int defaultValue) {
        try { return Integer.parseInt(get(key)); }
        catch (Exception e) { return defaultValue; }
    }

    public long getLong(ConfigKey key, long defaultValue) {
        try { return Long.parseLong(get(key)); }
        catch (Exception e) { return defaultValue; }
    }

    public Environment getEnvironment() { return environment; }

    public boolean isDatabaseRepository() {
        return "database".equalsIgnoreCase(get(ConfigKey.REPOSITORY_TYPE));
    }
}