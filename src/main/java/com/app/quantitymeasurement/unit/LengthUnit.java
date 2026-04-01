package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {

	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * this.conversionFactor; // base = inches
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / this.conversionFactor;
	}

	@Override
	public String getMeasurementType() {
		return "LengthUnit";
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {

		for (LengthUnit unit : LengthUnit.values()) {
			if (unit.name().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}

		throw new IllegalArgumentException("Invalid length unit: " + unitName);
	}
}