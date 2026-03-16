package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.util.ApplicationConfig;

public class ApplicationConfigTest {

    @Test
    public void testDatabaseConfiguration_LoadedFromProperties() {

        ApplicationConfig config = ApplicationConfig.getInstance();

        String driver =
                config.get(ApplicationConfig.ConfigKey.DB_DRIVER);

        assertNotNull(driver);
        assertTrue(driver.length() > 0);
    }
}