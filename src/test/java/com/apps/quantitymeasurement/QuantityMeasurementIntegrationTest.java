package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;
    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setup() {

		repository = QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        controller = new QuantityMeasurementController(service);

        repository.deleteAll();
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @Test
    void testServiceWithCacheRepository_Integration() {

        QuantityDTO q1 =
                new QuantityDTO(2, QuantityDTO.LengthUnit.FEET);

        QuantityDTO q2 =
                new QuantityDTO(24, QuantityDTO.LengthUnit.INCHES);

        boolean result =
                controller.performComparison(q1, q2);

        assertTrue(result);
    }
}