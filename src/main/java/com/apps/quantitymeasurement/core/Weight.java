package com.apps.quantitymeasurement.core;

public class Weight implements IMeasurable {
	// Instance variable to hold weight value and unit
	private double value;
	private WeightUnit unit;

	private static final double EPSILON = 1e-5;

	public Weight(double value, WeightUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Weight that = (Weight) obj;
		return compare(that);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(unit.convertToBaseUnit(value));
	}

	public boolean compare(Weight thatWeight) {
		if (thatWeight == null)
			return false;
		return Double.compare(this.convertToBaseUnit(this.value), thatWeight.convertToBaseUnit(thatWeight.value)) == 0;
	}

	public Weight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue = convertToBaseUnit(value);

		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Weight(convertedValue, targetUnit);
	}

	public Weight add(Weight thatWeight) {

		if (thatWeight == null) {
			throw new IllegalArgumentException("Weight cannot be null");
		}

		if (this.unit == thatWeight.unit) {
			return new Weight(this.value + thatWeight.value, this.unit);
		}

		double thisInBase = this.convertToBaseUnit(this.value);
		double thatInBase = thatWeight.convertToBaseUnit(thatWeight.value);

		double totalInBase = thisInBase + thatInBase;
		double convertedValue = convertFromBaseUnitToTargetUnit(totalInBase, this.unit);

		return new Weight(convertedValue, this.unit);
	}

	/**
	 * Adding weight to this weight with specific target unit
	 */
	public Weight add(Weight weight, WeightUnit targetUnit) {
		return addAndConvert(weight, targetUnit);
	}

	/**
	 * Add two weights into one specific target unit
	 */
	private Weight addAndConvert(Weight weight, WeightUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		Weight wt = this.add(weight);
		double weightInGrams = wt.convertToBaseUnit(wt.value);
		double weightInTargetUnit = convertFromBaseUnitToTargetUnit(weightInGrams, targetUnit);
		return new Weight(weightInTargetUnit, targetUnit);
	}

	// Convert base unit to target unit
	private double convertFromBaseUnitToTargetUnit(double baseValue, WeightUnit targetUnit) {
		return baseValue / targetUnit.getConversionFactor();
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}

	@Override
	public double getConversionFactor() {
		return unit.getConversionFactor();
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * getConversionFactor();
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / unit.getConversionFactor();
	}

	@Override
	public String getUnitName() {
		return unit.getUnitName();
	}

	@Override
	public String getMeasurementType() {
		return unit.getClass().getSimpleName();
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for (WeightUnit unit : WeightUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid weight unit: " + unitName);
	}
}
