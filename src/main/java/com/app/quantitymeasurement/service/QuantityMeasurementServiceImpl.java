package com.app.quantitymeasurement.service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.QuantityModel;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

    @Autowired
    private QuantityMeasurementRepository repository;

    // =========================================================
    // HISTORY APIs
    // =========================================================

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        return repository.findByOperation(operation)
                .stream()
                .map(QuantityMeasurementDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementByType(String type) {
        return repository.findByThisMeasurementType(type)
                .stream()
                .map(QuantityMeasurementDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return repository.findByIsErrorTrue()
                .stream()
                .map(QuantityMeasurementDTO::from)
                .collect(Collectors.toList());
    }

    // =========================================================
    // CORE OPERATIONS
    // =========================================================

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

        try {
            boolean result = compare(m1, m2);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation("COMPARE");
            entity.setResultString(result ? "Equal" : "Not Equal");
            entity.setError(false);

            return QuantityMeasurementDTO.from(repository.save(entity));

        } catch (Exception e) {
            return saveError("COMPARE", e);
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> source = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> target = convertDtoToModel(thatDTO);

        try {
            double result = convertTo(source, target);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation("CONVERT");
            entity.setResultValue(result);
            entity.setResultUnit(thatDTO.getUnit());
            entity.setError(false);

            return QuantityMeasurementDTO.from(repository.save(entity));

        } catch (Exception e) {
            return saveError("CONVERT", e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO) {
        return add(thisDTO, thatDTO, thisDTO);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);
        QuantityModel<IMeasurable> target = convertDtoToModel(targetDTO);

        try {
            validateArithmeticOperands(m1, m2, target, true);

            double result = performArithmetic(m1, m2, target, ArithmeticOperation.ADD);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation("ADD");
            entity.setResultValue(result);
            entity.setResultUnit(targetDTO.getUnit());
            entity.setError(false);

            return QuantityMeasurementDTO.from(repository.save(entity));

        } catch (Exception e) {
            return saveError("ADD", e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO) {
        return subtract(thisDTO, thatDTO, thisDTO);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);
        QuantityModel<IMeasurable> target = convertDtoToModel(targetDTO);

        try {
            validateArithmeticOperands(m1, m2, target, true);

            double result = performArithmetic(m1, m2, target, ArithmeticOperation.SUBTRACT);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation("SUBTRACT");
            entity.setResultValue(result);
            entity.setResultUnit(targetDTO.getUnit());
            entity.setError(false);

            return QuantityMeasurementDTO.from(repository.save(entity));

        } catch (Exception e) {
            return saveError("SUBTRACT", e);
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

        try {
            validateArithmeticOperands(m1, m2, null, false);

            double result = performArithmetic(m1, m2, null, ArithmeticOperation.DIVIDE);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation("DIVIDE");
            entity.setResultValue(result);
            entity.setError(false);

            return QuantityMeasurementDTO.from(repository.save(entity));

        } catch (Exception e) {
            return saveError("DIVIDE", e);
        }
    }

    // =========================================================
    // INTERNAL LOGIC
    // =========================================================

    private QuantityModel<IMeasurable> convertDtoToModel(QuantityDTO dto) {
        return new QuantityModel<>(dto.getValue(), dto.getUnitInstance());
    }

    private <U extends IMeasurable> boolean compare(QuantityModel<U> m1, QuantityModel<U> m2) {

        double v1 = m1.getUnit().convertToBaseUnit(m1.getValue());
        double v2 = m2.getUnit().convertToBaseUnit(m2.getValue());

        return Math.abs(v1 - v2) < 1e-5;
    }

    private <U extends IMeasurable> double convertTo(QuantityModel<U> source, QuantityModel<U> target) {

        double base = source.getUnit().convertToBaseUnit(source.getValue());
        return target.getUnit().convertFromBaseUnit(base);
    }

    private <U extends IMeasurable> double performArithmetic(
            QuantityModel<U> m1,
            QuantityModel<U> m2,
            QuantityModel<U> target,
            ArithmeticOperation op) {

        double base1 = m1.getUnit().convertToBaseUnit(m1.getValue());
        double base2 = m2.getUnit().convertToBaseUnit(m2.getValue());

        double result = op.apply(base1, base2);

        if (target == null)
            return result;

        return target.getUnit().convertFromBaseUnit(result);
    }

    private <U extends IMeasurable> void validateArithmeticOperands(
            QuantityModel<U> m1,
            QuantityModel<U> m2,
            QuantityModel<U> target,
            boolean targetRequired) {

        if (m1 == null || m2 == null)
            throw new RuntimeException("Operands cannot be null");

        if (targetRequired && target == null)
            throw new RuntimeException("Target unit required");

        if (!m1.getUnit().getClass().equals(m2.getUnit().getClass()))
            throw new RuntimeException("Cross measurement operation not allowed");

        if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue()))
            throw new RuntimeException("Invalid values");
    }

    private enum ArithmeticOperation {
        ADD {
            double apply(double a, double b) { return a + b; }
        },
        SUBTRACT {
            double apply(double a, double b) { return a - b; }
        },
        DIVIDE {
            double apply(double a, double b) {
                if (Math.abs(b) < 1e-5)
                    throw new ArithmeticException("Division by zero");
                return a / b;
            }
        };

        abstract double apply(double a, double b);
    }

    // =========================================================
    // ERROR HANDLING
    // =========================================================

    private QuantityMeasurementDTO saveError(String operation, Exception e) {

        logger.warning(e.getMessage());

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation(operation);
        entity.setError(true);
        entity.setErrorMessage(e.getMessage());

        return QuantityMeasurementDTO.from(repository.save(entity));
    }
}