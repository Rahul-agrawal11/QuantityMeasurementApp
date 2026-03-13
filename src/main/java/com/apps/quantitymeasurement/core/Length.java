package com.apps.quantitymeasurement.core;

public class Length implements IMeasurable {

	// Instance variables
	private double value;
	private LengthUnit unit;

	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
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

	public LengthUnit getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unit.getUnitName();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Length that = (Length) obj;
		return compare(that);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(unit.convertToBaseUnit(value));
	}

	/**
	 * Conversion Method
	 */
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		// Convert to base (inches)
		double baseValue = convertToBaseUnit(this.value);

		// Convert from inches to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Length(round(convertedValue), targetUnit);
	}

	/**
	 * Add two lengths into one specific target unit
	 */
	public Length add(Length thatLength) {

		if (thatLength == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}

		if (this.unit == thatLength.unit) {
			return new Length(this.value + thatLength.value, this.unit);
		}

		double thisInBase = this.convertToBaseUnit(this.value);
		double thatInBase = thatLength.convertToBaseUnit(thatLength.value);

		double totalInBase = thisInBase + thatInBase;
		double convertedValue = convertFromBaseUnitToTargetUnit(totalInBase, this.unit);

		return new Length(round(convertedValue), this.unit);
	}

	/**
	 * Adding length to this length with specific target unit
	 */
	public Length add(Length length, LengthUnit targetUnit) {
		return addAndConvert(length, targetUnit);
	}

	/**
	 * Add two lengths and convert them into specific target unit
	 */
	private Length addAndConvert(Length length, LengthUnit targetUnit) {
		if (length == null) {
			throw new IllegalArgumentException("Length cannot be null.");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		Length len = this.add(length);
		double lengthInInches = len.convertToBaseUnit(len.value);
		double lengthInTargetUnit = convertFromBaseUnitToTargetUnit(lengthInInches, targetUnit);
		return new Length(round(lengthInTargetUnit), targetUnit);
	}

	/**
	 * Compare two lengths
	 */
	private Boolean compare(Length thatLength) {
		if (thatLength == null)
			return false;
		return Double.compare(this.convertToBaseUnit(this.value), thatLength.convertToBaseUnit(thatLength.value)) == 0;
	}

	// Convert base unit to target unit
	private double convertFromBaseUnitToTargetUnit(double baseValue, LengthUnit targetUnit) {
		return baseValue / targetUnit.getConversionFactor();
	}

	private double round(double value) {
		return Math.round(value * 1000.0) / 1000.0; // now for three decimal places for UC 7
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}

	@Override
	public double getConversionFactor() {
		return unit.getConversionFactor();
	}

	@Override
	public double convertToBaseUnit(double value) {
		return unit.convertToBaseUnit(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / unit.getConversionFactor();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
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
