package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;

class QuantityMeasurementDatabaseRepositoryTest {

    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setup() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();
    }

    @Test
    void testDatabaseRepository_SaveEntity() {

        QuantityMeasurementEntity entity = createEntity();

        repository.save(entity);

        List<QuantityMeasurementEntity> list =
                repository.getAllMeasurements();

        assertEquals(1, list.size());
    }

    @Test
    void testDatabaseRepository_RetrieveAllMeasurements() {

        repository.save(createEntity());
        repository.save(createEntity());

        List<QuantityMeasurementEntity> list =
                repository.getAllMeasurements();

        assertEquals(2, list.size());
    }

    @Test
    void testDatabaseRepository_DeleteAll() {

        repository.save(createEntity());

        repository.deleteAll();

        List<QuantityMeasurementEntity> list =
                repository.getAllMeasurements();

        assertEquals(0, list.size());
    }
    
    private QuantityMeasurementEntity createEntity() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.thisValue = 1;
        entity.thisUnit = "FEET";
        entity.thisMeasurementType = "LengthUnit";

        entity.thatValue = 12;
        entity.thatUnit = "INCHES";
        entity.thatMeasurementType = "LengthUnit";

        entity.operation = "COMPARE";

        entity.resultValue = 1;
        entity.resultUnit = "FEET";
        entity.resultMeasurementType = "LengthUnit";

        entity.resultString = "Equal";

        entity.isError = false;
        entity.errorMessage = null;

        return entity;
    }
}