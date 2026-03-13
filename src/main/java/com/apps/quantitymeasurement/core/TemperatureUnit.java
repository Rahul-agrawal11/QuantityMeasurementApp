/**
* Enumeration representing different units of temperature measurement.
*
* <p>This enum defines standard temperature units used for quantity measurements
* and conversions. Each unit can be converted to other temperature units for
* standardized comparisons.</p>
* 
* <p>Supported temperature units include:</p>
* <ul>
* 	<li>Celsius (°C) metric temperature unit</li>
* 	<li>Fahrenheit (°F) imperial temperature unit</li>
* 	<li>Kelvin (K) absolute temperature unit</li>
* </ul>
*
* <p>Example usage:</p>
* <pre>
* 	TemperatureUnit celsius TemperatureUnit.CELSIUS;
* 	TemperatureUnit fahrenheit TemperatureUnit. FAHRENHEIT;
* </pre>
*/

package com.apps.quantitymeasurement.core;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {
	CELSIUS(false), FAHRENHEIT(true);
	
	private final Function<Double, Double> conversionValue;
	
	// Lambda expression to indicate that TemperatureUnit does not support arithmetic operations
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
	
	public static void main(String[] args) {
		
		double cel = 100.0;
		double far = TemperatureUnit.CELSIUS.convertTo(cel, FAHRENHEIT);
		System.out.println(far);
		
		double convertedCelcius = TemperatureUnit.FAHRENHEIT.convertTo(far, CELSIUS);
		System.out.println(convertedCelcius);
	}
}