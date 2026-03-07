/**
* LengthUnit.java
* 	The LengthUnit enumeration defines various units of length measurement
* 	along with their conversion factors relative to a base unit (inches).
* 	This enumeration is used in the QuantityMeasurement application to facilitate
* 	conversions and comparisons between different length units.
* 	<p>The base unit for conversion is inches. Each unit stores a conversion factor * relative to 
* 	inches (the base unit). This design simplifies unit conversions by
* 	always converting through a common base unit. </p>
* 	<p>Example: 
* 	1 FOOT 12.0 inches, 1 YARD 36.0 inches,
* 	1 CENTIMETER 0.393701 inches</p>
*/

package com.apps.quantitymeasurement;

public enum LengthUnit {
	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	public double convertToBaseUnit(double value) {
		double base = value * this.getConversionFactor();
		return Math.round(base * 100.0) / 100.0;
	}

	public double convertFromBaseUnit(double baseValue) {
		double convertedValue = baseValue / this.getConversionFactor();
		return Math.round(convertedValue * 100.0) / 100.0;
	}
}
