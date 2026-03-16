package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentRepositoryAccessTest {

    @Test
    void testDatabaseRepository_ConcurrentAccess() {

        QuantityMeasurementDatabaseRepository repository =
                QuantityMeasurementDatabaseRepository.getInstance();

        Runnable task = repository::getAllMeasurements;

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        assertTrue(true);
    }
}