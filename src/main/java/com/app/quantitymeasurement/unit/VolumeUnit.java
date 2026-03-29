package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {

	// Conversion factor to the base unit(grams)
	LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

	private final double conversionFactor;

	private VolumeUnit(double conversionFactor) {
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
		double convertedValue = baseValue / this.getConversionFactor();
		return Math.round(convertedValue * 100.0) / 100.0;
	}

	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	public IMeasurable getUnitInstance(String unitName) {
		for (VolumeUnit unit : VolumeUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid volume unit: " + unitName);
	}
}
