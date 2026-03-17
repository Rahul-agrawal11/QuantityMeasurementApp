package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {
	// Conversion factor to the base unit(grams)
	MILIGRAM(0.001), GRAM(1.0), KILOGRAM(1000.0), POUND(453.592), TONNE(1_000_000.0);

	private final double conversionFactor;

	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.toString();
	}

	/**
	 * Convert the length value to base unit value
	 */
	public double convertToBaseUnit(double value) {
		return value * this.conversionFactor;
	}

	/**
	 * Convert base unit value to specified unit
	 */
	public double convertFromBaseUnit(double baseValue) {
		return Math.round((baseValue / this.conversionFactor) * 100.0) / 100.0;
	}

	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	public IMeasurable getUnitInstance(String unitName) {
		for (WeightUnit unit : WeightUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid weight unit: " + unitName);
	}
}
