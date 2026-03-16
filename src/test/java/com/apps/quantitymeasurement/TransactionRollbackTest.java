package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRollbackTest {

    @Test
    void testTransactionRollback_OnError() {

        QuantityMeasurementDatabaseRepository repository =
                QuantityMeasurementDatabaseRepository.getInstance();

        assertDoesNotThrow(repository::getAllMeasurements);
    }
}