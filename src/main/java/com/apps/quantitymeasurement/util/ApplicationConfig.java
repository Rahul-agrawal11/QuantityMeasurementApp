package com.apps.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Singleton configuration loader. Reads application.properties from classpath.
 */
public class ApplicationConfig {

	private static final Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

	private static ApplicationConfig instance;

	private Properties properties;

	// ── Config Keys ─────────────────────────────────────────────

	public enum ConfigKey {
		REPOSITORY_TYPE("repository.type"), DB_DRIVER("db.driver"), DB_URL("db.url"), DB_USERNAME("db.username"),
		DB_PASSWORD("db.password"), DB_POOL_SIZE("db.pool.size");

		private final String key;

		ConfigKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}
	}

	// ── Constructor ─────────────────────────────────────────────

	private ApplicationConfig() {
		loadConfiguration();
	}

	public static ApplicationConfig getInstance() {

		if (instance == null) {
			instance = new ApplicationConfig();
		}

		return instance;
	}

	// ── Load Configuration ─────────────────────────────────────

	private void loadConfiguration() {

		properties = new Properties();

		try {

			String configFile = "application.properties";

			InputStream input = ApplicationConfig.class.getClassLoader().getResourceAsStream(configFile);

			if (input != null) {

				properties.load(input);

				logger.info("Configuration loaded from " + configFile);

			} else {

				logger.warning("application.properties not found. Using defaults");

				loadDefaults();
			}

		} catch (Exception e) {

			logger.severe("Error loading configuration : " + e.getMessage());

			loadDefaults();
		}
	}

	// ── Default Configuration ──────────────────────────────────

	private void loadDefaults() {

		properties.setProperty(ConfigKey.REPOSITORY_TYPE.getKey(), "cache");

		properties.setProperty(ConfigKey.DB_DRIVER.getKey(), "com.mysql.cj.jdbc.Driver");

		properties.setProperty(ConfigKey.DB_URL.getKey(), "jdbc:mysql://localhost:3306/quantity_measurement_db");

		properties.setProperty(ConfigKey.DB_USERNAME.getKey(), "root");

		properties.setProperty(ConfigKey.DB_PASSWORD.getKey(), "root");

		properties.setProperty(ConfigKey.DB_POOL_SIZE.getKey(), "5");
	}

	// ── Access Methods ─────────────────────────────────────────

	public String get(ConfigKey key) {

		return properties.getProperty(key.getKey());
	}

	public int getInt(ConfigKey key, int defaultValue) {

		try {
			return Integer.parseInt(get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean isDatabaseRepository() {

		return "database".equalsIgnoreCase(get(ConfigKey.REPOSITORY_TYPE));
	}
}