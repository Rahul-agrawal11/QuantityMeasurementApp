package com.app.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
	CELSIUS(false), FAHRENHEIT(true);

	private final Function<Double, Double> conversionValue;

	// Lambda expression to indicate that TemperatureUnit does not support
	// arithmetic operations
	// in a meaningful way, so we set this to false
	SupportsArithmetic supportsArithmetic = () -> false;

	TemperatureUnit(boolean isFahrenheit) {
		if (isFahrenheit) {
			// Functional Interface for Farenheit to Celsius conversion
			conversionValue = (f) -> (f - 32.0) * 5.0 / 9.0;
		} else {
			// Conversion function to convert from this unit to the base unit (Celsius)
			conversionValue = (c) -> c;
		}
	}

	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return conversionValue.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		if (this == FAHRENHEIT) {
			return (baseValue * 9.0 / 5.0) + 32.0;
		}

		return baseValue;
	}

	@Override
	public boolean supportsArithmetic() {
		return false;
	}

	public double convertTo(double value, TemperatureUnit targetUnit) {

		double base = convertToBaseUnit(value);

		return targetUnit.convertFromBaseUnit(base);
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public void validateOperationSupport(String operation) {

		if (!supportsArithmetic()) {

			throw new UnsupportedOperationException(this.name() + " does not support " + operation + " operations.");
		}
	}

	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	public IMeasurable getUnitInstance(String unitName) {
		for (TemperatureUnit unit : TemperatureUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid temperature unit: " + unitName);
	}

	@Override
	public String toString() {
		return "TemperatureUnit{ " + "unit= " + name() + " baseValue" + getConversionFactor() + "}";
	}

}
