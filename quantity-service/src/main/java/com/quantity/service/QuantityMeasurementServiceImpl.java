package com.quantity.service;

import com.quantity.dto.QuantityDTO;
import com.quantity.dto.QuantityMeasurementDTO;
import com.quantity.model.IMeasurable;
import com.quantity.model.QuantityMeasurementEntity;
import com.quantity.model.QuantityModel;
import com.quantity.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private QuantityHistoryService quantityHistoryService;

    // =========================================================
    // CORE OPERATIONS
    // =========================================================

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

        boolean result = compare(m1, m2);

        QuantityMeasurementEntity entity = buildEntity(
                "COMPARE", thisDTO, thatDTO, null,
                null, result ? "EQUAL" : "NOT EQUAL",
                false, null
        );

        QuantityMeasurementEntity saved = repository.save(entity);

        saveHistory("COMPARE",
                thisDTO.getValue() + " " + thisDTO.getUnit() +
                        " vs " +
                        thatDTO.getValue() + " " + thatDTO.getUnit() +
                        " → " + (result ? "EQUAL" : "NOT EQUAL"));

        return QuantityMeasurementDTO.from(saved);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> source = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> target = convertDtoToModel(thatDTO);

        double result = convertTo(source, target);

        QuantityMeasurementEntity entity = buildEntity(
                "CONVERT", thisDTO, thatDTO, thatDTO,
                result, null, false, null
        );

        QuantityMeasurementEntity saved = repository.save(entity);

        saveHistory("CONVERT",
                thisDTO.getValue() + " " + thisDTO.getUnit() +
                        " → " + result + " " + thatDTO.getUnit());

        return QuantityMeasurementDTO.from(saved);
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

        validateArithmeticOperands(m1, m2, target, true);

        double result = performArithmetic(m1, m2, target, ArithmeticOperation.ADD);

        QuantityMeasurementEntity entity = buildEntity(
                "ADD", thisDTO, thatDTO, targetDTO,
                result, null, false, null
        );

        QuantityMeasurementEntity saved = repository.save(entity);

        saveHistory("ADD",
                thisDTO.getValue() + " " + thisDTO.getUnit() +
                        " + " +
                        thatDTO.getValue() + " " + thatDTO.getUnit() +
                        " = " + result + " " + targetDTO.getUnit());

        return QuantityMeasurementDTO.from(saved);
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

        validateArithmeticOperands(m1, m2, target, true);

        double result = performArithmetic(m1, m2, target, ArithmeticOperation.SUBTRACT);

        QuantityMeasurementEntity entity = buildEntity(
                "SUBTRACT", thisDTO, thatDTO, targetDTO,
                result, null, false, null
        );

        QuantityMeasurementEntity saved = repository.save(entity);

        saveHistory("SUBTRACT",
                thisDTO.getValue() + " " + thisDTO.getUnit() +
                        " - " +
                        thatDTO.getValue() + " " + thatDTO.getUnit() +
                        " = " + result + " " + targetDTO.getUnit());

        return QuantityMeasurementDTO.from(saved);
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisDTO, QuantityDTO thatDTO) {

        QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
        QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

        validateArithmeticOperands(m1, m2, null, false);

        double result = performArithmetic(m1, m2, null, ArithmeticOperation.DIVIDE);

        QuantityMeasurementEntity entity = buildEntity(
                "DIVIDE", thisDTO, thatDTO, null,
                result, null, false, null
        );

        QuantityMeasurementEntity saved = repository.save(entity);

        saveHistory("DIVIDE",
                thisDTO.getValue() + " " + thisDTO.getUnit() +
                        " / " +
                        thatDTO.getValue() + " " + thatDTO.getUnit() +
                        " = " + result);

        return QuantityMeasurementDTO.from(saved);
    }

    // =========================================================
    // HISTORY HELPER (JWT BASED)
    // =========================================================

    private void saveHistory(String operation, String details) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        quantityHistoryService.save(email, operation, details);
    }

    // =========================================================
    // INTERNAL METHODS
    // =========================================================

    private QuantityModel<IMeasurable> convertDtoToModel(QuantityDTO dto) {
        return new QuantityModel<>(dto.getValue(), dto.getUnitInstance());
    }

    private <U extends IMeasurable> boolean compare(QuantityModel<U> m1, QuantityModel<U> m2) {

        if (!m1.getUnit().getMeasurementType()
                .equals(m2.getUnit().getMeasurementType())) {
            throw new RuntimeException("Cannot compare different measurement types");
        }

        double v1 = m1.getUnit().convertToBaseUnit(m1.getValue());
        double v2 = m2.getUnit().convertToBaseUnit(m2.getValue());

        return Math.abs(v1 - v2) < 1e-4;
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

        return (target == null)
                ? result
                : target.getUnit().convertFromBaseUnit(result);
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

    // =========================================================
    // ARITHMETIC ENUM
    // =========================================================

    private enum ArithmeticOperation {
        ADD { double apply(double a, double b) { return a + b; } },
        SUBTRACT { double apply(double a, double b) { return a - b; } },
        DIVIDE {
            double apply(double a, double b) {
                if (b == 0.0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            }
        };

        abstract double apply(double a, double b);
    }

    private Double round(Double value) {
        if (value == null) return null;

        return Math.round(value * 100.0) / 100.0; // 2 decimal places
    }

    // =========================================================
    // ENTITY BUILDER
    // =========================================================

    private QuantityMeasurementEntity buildEntity(
            String operation,
            QuantityDTO thisDTO,
            QuantityDTO thatDTO,
            QuantityDTO targetDTO,
            Double resultValue,
            String resultString,
            boolean isError,
            String errorMessage) {

        return QuantityMeasurementEntity.builder()
                .thisValue(thisDTO != null ? thisDTO.getValue() : null)
                .thisUnit(thisDTO != null ? thisDTO.getUnit() : null)
                .thisMeasurementType(thisDTO != null ? thisDTO.getMeasurementType() : null)

                .thatValue(thatDTO != null ? thatDTO.getValue() : null)
                .thatUnit(thatDTO != null ? thatDTO.getUnit() : null)
                .thatMeasurementType(thatDTO != null ? thatDTO.getMeasurementType() : null)

                .operation(operation)

                .resultValue(round(resultValue))
                .resultUnit(targetDTO != null ? targetDTO.getUnit() : null)
                .resultMeasurementType(targetDTO != null ? targetDTO.getMeasurementType() : null)
                .resultString(resultString)

                .isError(isError)
                .errorMessage(errorMessage)
                .build();
    }

    // =========================================================
    // OPTIONAL OLD METHODS (KEEP OR REMOVE)
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
}