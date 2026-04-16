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

	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	@Autowired
	private QuantityMeasurementRepository repository;

	// =========================================================
	// HISTORY APIs
	// =========================================================

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		return repository.findByOperation(operation).stream().map(QuantityMeasurementDTO::from)
				.collect(Collectors.toList());
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementByType(String type) {
		return repository.findByThisMeasurementType(type).stream().map(QuantityMeasurementDTO::from)
				.collect(Collectors.toList());
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndIsErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return repository.findByIsErrorTrue().stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	// =========================================================
	// CORE OPERATIONS
	// =========================================================

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {

		try {
			QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
			QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

			boolean result = compare(m1, m2);

			QuantityMeasurementEntity entity = buildEntity("COMPARE", thisDTO, thatDTO, null, null,
					result ? "EQUAL" : "NOT EQUAL", false, null);

			return QuantityMeasurementDTO.from(repository.save(entity));

		} catch (Exception e) {
			return saveError("COMPARE", thisDTO, thatDTO, null, e);
		}
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO) {

		try {
			QuantityModel<IMeasurable> source = convertDtoToModel(thisDTO);
			QuantityModel<IMeasurable> target = convertDtoToModel(thatDTO);

			double result = convertTo(source, target);

			QuantityMeasurementEntity entity = buildEntity("CONVERT", thisDTO, thatDTO, thatDTO, result, null, false,
					null);

			return QuantityMeasurementDTO.from(repository.save(entity));

		} catch (Exception e) {
			return saveError("CONVERT", thisDTO, null, thatDTO, e);
		}
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		return add(thisDTO, thatDTO, thisDTO);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

		try {
			QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
			QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);
			QuantityModel<IMeasurable> target = convertDtoToModel(targetDTO);

			validateArithmeticOperands(m1, m2, target, true);

			double result = performArithmetic(m1, m2, target, ArithmeticOperation.ADD);

			QuantityMeasurementEntity entity = buildEntity("ADD", thisDTO, thatDTO, targetDTO, result, null, false,
					null);

			return QuantityMeasurementDTO.from(repository.save(entity));

		} catch (Exception e) {
			return saveError("ADD", thisDTO, thatDTO, targetDTO, e);
		}
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		return subtract(thisDTO, thatDTO, thisDTO);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {

		try {
			QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
			QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);
			QuantityModel<IMeasurable> target = convertDtoToModel(targetDTO);

			validateArithmeticOperands(m1, m2, target, true);

			double result = performArithmetic(m1, m2, target, ArithmeticOperation.SUBTRACT);

			QuantityMeasurementEntity entity = buildEntity("SUBTRACT", thisDTO, thatDTO, targetDTO, result, null, false,
					null);

			return QuantityMeasurementDTO.from(repository.save(entity));

		} catch (Exception e) {
			return saveError("SUBTRACT", thisDTO, thatDTO, targetDTO, e);
		}
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisDTO, QuantityDTO thatDTO) {

		try {
			if (thatDTO.getValue() == 0) {
				throw new RuntimeException("Cannot divide by zero");
			}
			QuantityModel<IMeasurable> m1 = convertDtoToModel(thisDTO);
			QuantityModel<IMeasurable> m2 = convertDtoToModel(thatDTO);

			validateArithmeticOperands(m1, m2, null, false);

			double result = performArithmetic(m1, m2, null, ArithmeticOperation.DIVIDE);

			QuantityMeasurementEntity entity = buildEntity("DIVIDE", thisDTO, thatDTO, null, result, null, false, null);

			return QuantityMeasurementDTO.from(repository.save(entity));

		} catch (Exception e) {
			logger.warning(e.getMessage());
			throw new RuntimeException(e.getMessage()); // ✅ THROW instead of returning
		}
	}

	// =========================================================
	// COMMON BUILDER METHOD (🔥 KEY FIX)
	// =========================================================

	private QuantityMeasurementEntity buildEntity(String operation, QuantityDTO thisDTO, QuantityDTO thatDTO,
			QuantityDTO targetDTO, Double resultValue, String resultString, boolean isError, String errorMessage) {

		return QuantityMeasurementEntity.builder().thisValue(thisDTO != null ? thisDTO.getValue() : null)
				.thisUnit(thisDTO != null ? thisDTO.getUnit() : null)
				.thisMeasurementType(thisDTO != null ? thisDTO.getMeasurementType() : null)

				.thatValue(thatDTO != null ? thatDTO.getValue() : null)
				.thatUnit(thatDTO != null ? thatDTO.getUnit() : null)
				.thatMeasurementType(thatDTO != null ? thatDTO.getMeasurementType() : null)

				.operation(operation)

				.resultValue(resultValue).resultUnit(targetDTO != null ? targetDTO.getUnit() : null)
				.resultMeasurementType(targetDTO != null ? targetDTO.getMeasurementType() : null)
				.resultString(resultString)

				.isError(isError).errorMessage(errorMessage).build();
	}

	// =========================================================
	// INTERNAL LOGIC
	// =========================================================

	private QuantityModel<IMeasurable> convertDtoToModel(QuantityDTO dto) {
		return new QuantityModel<>(dto.getValue(), dto.getUnitInstance());
	}

//	private <U extends IMeasurable> boolean compare(QuantityModel<U> m1, QuantityModel<U> m2) {
//		double v1 = m1.getUnit().convertToBaseUnit(m1.getValue());
//		double v2 = m2.getUnit().convertToBaseUnit(m2.getValue());
//		return Math.abs(v1 - v2) < 1e-5;
//	}

	private <U extends IMeasurable> boolean compare(QuantityModel<U> m1, QuantityModel<U> m2) {

		// ✅ Compare using measurement type (NOT class)
		if (!m1.getUnit().getMeasurementType().equals(m2.getUnit().getMeasurementType())) {
			throw new RuntimeException("Cannot compare different measurement types");
		}

		// Convert both to base unit
		double v1 = m1.getUnit().convertToBaseUnit(m1.getValue());
		double v2 = m2.getUnit().convertToBaseUnit(m2.getValue());

		System.out.println("DEBUG → v1: " + v1 + " | v2: " + v2); // 👈 add this

		return Math.abs(v1 - v2) < 1e-4;
	}

	private <U extends IMeasurable> double convertTo(QuantityModel<U> source, QuantityModel<U> target) {
		double base = source.getUnit().convertToBaseUnit(source.getValue());
		return target.getUnit().convertFromBaseUnit(base);
	}

	private <U extends IMeasurable> double performArithmetic(QuantityModel<U> m1, QuantityModel<U> m2,
			QuantityModel<U> target, ArithmeticOperation op) {

		double base1 = m1.getUnit().convertToBaseUnit(m1.getValue());
		double base2 = m2.getUnit().convertToBaseUnit(m2.getValue());

		double result = op.apply(base1, base2);

		if (target == null)
			return result;

		return target.getUnit().convertFromBaseUnit(result);
	}

	private <U extends IMeasurable> void validateArithmeticOperands(QuantityModel<U> m1, QuantityModel<U> m2,
			QuantityModel<U> target, boolean targetRequired) {

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
			double apply(double a, double b) {
				return a + b;
			}
		},
		SUBTRACT {
			double apply(double a, double b) {
				return a - b;
			}
		},
		DIVIDE {
			double apply(double a, double b) {

				// STRONG CHECK (before anything else)
				if (b == 0.0) {
					throw new ArithmeticException("Cannot divide by zero");
				}

				return a / b;
			}
		};

		abstract double apply(double a, double b);
	}

	// =========================================================
	// ERROR HANDLING
	// =========================================================

	private QuantityMeasurementDTO saveError(String operation, QuantityDTO thisDTO, QuantityDTO thatDTO,
			QuantityDTO targetDTO, Exception e) {

		logger.warning(e.getMessage());

		QuantityMeasurementEntity entity = buildEntity(operation, thisDTO, thatDTO, targetDTO, null, null, true,
				e.getMessage());

		return QuantityMeasurementDTO.from(repository.save(entity));
	}

}