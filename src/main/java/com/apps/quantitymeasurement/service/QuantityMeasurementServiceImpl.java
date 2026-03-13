package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.TemperatureUnit;
import com.apps.quantitymeasurement.core.VolumeUnit;
import com.apps.quantitymeasurement.core.WeightUnit;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.dto.QuantityModel;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
	}

//	private enum Opeartion {
//		COMPARISON, CONVERSION, ARITHMETIC
//	}

	private enum ArithmeticOperation {

		ADD {
			@Override
			double compute(double a, double b) {
				return a + b;
			}
		},

		SUBTRACT {
			@Override
			double compute(double a, double b) {
				return a - b;
			}
		},

		DIVIDE {
			@Override
			double compute(double a, double b) {
				if (Math.abs(b) < 1e-5)
					throw new ArithmeticException("Division by zero");
				return a / b;
			}
		};

		abstract double compute(double a, double b);
	}

	private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO dto) {
		if (dto == null)
			throw new QuantityMeasurementException("QuantityDTO cannot be null");
		IMeasurable unit = resolveUnit(dto.getMeasurementType(), dto.getUnit());
		return new QuantityModel<>(dto.getValue(), unit);
	}

	private IMeasurable resolveUnit(String measurementType, String unitName) {
		switch (measurementType) {
		case "LengthUnit":
			return LengthUnit.FEET.getUnitInstance(unitName);
		case "WeightUnit":
			return WeightUnit.KILOGRAM.getUnitInstance(unitName);
		case "VolumeUnit":
			return VolumeUnit.LITRE.getUnitInstance(unitName);
		case "TemperatureUnit":
			return TemperatureUnit.CELSIUS.getUnitInstance(unitName);
		default:
			throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
		}
	}

	private void validateArithmeticOperands(QuantityModel<IMeasurable> m1, QuantityModel<IMeasurable> m2,
			IMeasurable targetUnit, boolean targetRequired) {

		if (m1 == null || m2 == null)
			throw new QuantityMeasurementException("Operands cannot be null");
		if (targetRequired && targetUnit == null)
			throw new QuantityMeasurementException("Target unit cannot be null");
		if (!m1.getUnit().getClass().equals(m2.getUnit().getClass()))
			throw new QuantityMeasurementException("Cross-category operation not allowed");
		if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue()))
			throw new QuantityMeasurementException("Values must be finite");
	}

	private double performArithmetic(QuantityModel<IMeasurable> m1, QuantityModel<IMeasurable> m2,
			ArithmeticOperation op) {

		m1.getUnit().validateOperationSupport(op.name());
		m2.getUnit().validateOperationSupport(op.name());
		double base1 = m1.getUnit().convertToBaseUnit(m1.getValue());
		double base2 = m2.getUnit().convertToBaseUnit(m2.getValue());
		return op.compute(base1, base2);
	}

	private double round(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	private QuantityDTO toDTO(double value, IMeasurable unit) {
		return new QuantityDTO(value, unit.getUnitName(), unit.getMeasurementType());
	}

	// ── COMPARE
	@Override
	public boolean compare(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			if (!m1.getUnit().getClass().equals(m2.getUnit().getClass())) {
				repository.save(new QuantityMeasurementEntity(m1, m2, "COMPARE", "Not Equal"));
				return false;
			}
			double base1 = m1.getUnit().convertToBaseUnit(m1.getValue());
			double base2 = m2.getUnit().convertToBaseUnit(m2.getValue());
			boolean isEqual = Math.abs(base1 - base2) <= 1e-5;
			String result = isEqual ? "Equal" : "Not Equal";
			repository.save(new QuantityMeasurementEntity(m1, m2, "COMPARE", result));
			return isEqual;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "COMPARE", e.getMessage(), true));
			throw new QuantityMeasurementException("Compare failed: " + e.getMessage(), e);
		}
	}

	// ── CONVERT
	@Override
	public QuantityDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			IMeasurable targetUnit = m2.getUnit();
			double base = m1.getUnit().convertToBaseUnit(m1.getValue());
			double converted = round(targetUnit.convertFromBaseUnit(base));

			QuantityModel<IMeasurable> resultModel = new QuantityModel<>(converted, targetUnit);
			repository.save(new QuantityMeasurementEntity(m1, m2, "CONVERT", resultModel));
			return toDTO(converted, targetUnit);
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "CONVERT", e.getMessage(), true));
			throw new QuantityMeasurementException("Convert failed: " + e.getMessage(), e);
		}
	}

	// ── ADD (implicit target = first operand's unit)
	@Override
	public QuantityDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			validateArithmeticOperands(m1, m2, null, false);
			double baseResult = performArithmetic(m1, m2, ArithmeticOperation.ADD);
			double result = round(m1.getUnit().convertFromBaseUnit(baseResult));
			QuantityModel<IMeasurable> resultModel = new QuantityModel<>(result, m1.getUnit());
			repository.save(new QuantityMeasurementEntity(m1, m2, "ADD", resultModel));
			return toDTO(result, m1.getUnit());
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "ADD", e.getMessage(), true));
			throw new QuantityMeasurementException("Add failed: " + e.getMessage(), e);
		}
	}

	// ── ADD (explicit target unit)
	@Override
	public QuantityDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			IMeasurable targetUnit = resolveUnit(targetDTO.getMeasurementType(), targetDTO.getUnit());
			validateArithmeticOperands(m1, m2, targetUnit, true);
			double baseResult = performArithmetic(m1, m2, ArithmeticOperation.ADD);
			double result = round(targetUnit.convertFromBaseUnit(baseResult));
			QuantityModel<IMeasurable> resultModel = new QuantityModel<>(result, targetUnit);
			repository.save(new QuantityMeasurementEntity(m1, m2, "ADD", resultModel));
			return toDTO(result, targetUnit);
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "ADD", e.getMessage(), true));
			throw new QuantityMeasurementException("Add failed: " + e.getMessage(), e);
		}
	}

	// ── SUBTRACT (implicit)
	@Override
	public QuantityDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			validateArithmeticOperands(m1, m2, null, false);
			double baseResult = performArithmetic(m1, m2, ArithmeticOperation.SUBTRACT);
			double result = round(m1.getUnit().convertFromBaseUnit(baseResult));
			QuantityModel<IMeasurable> resultModel = new QuantityModel<>(result, m1.getUnit());
			repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT", resultModel));
			return toDTO(result, m1.getUnit());
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT", e.getMessage(), true));
			throw new QuantityMeasurementException("Subtract failed: " + e.getMessage(), e);
		}
	}

	// ── SUBTRACT (explicit target)
	@Override
	public QuantityDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			IMeasurable targetUnit = resolveUnit(targetDTO.getMeasurementType(), targetDTO.getUnit());
			validateArithmeticOperands(m1, m2, targetUnit, true);
			double baseResult = performArithmetic(m1, m2, ArithmeticOperation.SUBTRACT);
			double result = round(targetUnit.convertFromBaseUnit(baseResult));
			QuantityModel<IMeasurable> resultModel = new QuantityModel<>(result, targetUnit);
			repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT", resultModel));
			return toDTO(result, targetUnit);
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT", e.getMessage(), true));
			throw new QuantityMeasurementException("Subtract failed: " + e.getMessage(), e);
		}
	}

	// ── DIVIDE
	@Override
	public double divide(QuantityDTO thisDTO, QuantityDTO thatDTO) {
		QuantityModel<IMeasurable> m1 = getQuantityModel(thisDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatDTO);
		try {
			validateArithmeticOperands(m1, m2, null, false);
			double result = performArithmetic(m1, m2, ArithmeticOperation.DIVIDE);
			repository.save(new QuantityMeasurementEntity(m1, m2, "DIVIDE", String.valueOf(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(m1, m2, "DIVIDE", e.getMessage(), true));
			throw new QuantityMeasurementException("Divide failed: " + e.getMessage(), e);
		}
	}
}
