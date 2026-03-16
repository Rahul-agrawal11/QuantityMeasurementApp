package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackageStructureTest {

    @Test
    void testPackageStructure_AllLayersPresent() {

        assertDoesNotThrow(() ->
                Class.forName("com.apps.quantitymeasurement.controller.QuantityMeasurementController"));

        assertDoesNotThrow(() ->
                Class.forName("com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl"));

        assertDoesNotThrow(() ->
                Class.forName("com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository"));
    }
}