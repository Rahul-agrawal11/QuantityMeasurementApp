/**
 * LengthUnit.java
 * 
 * The LengthUnit enumeration implements IMeasurable interface and
 * provides methods for unit conversion. It defines various units of length
 * measurement along with their conversion factors relative to a base unit (inches).
 * This enumeration is used in the Quantity Measurement application to facilitate
 * conversions and comparisons between different length units.
 * 
 * <p>The base unit for conversion is inches. Each unit stores a conversion factor
 * relative to inches (the base unit). This design simplifies unit conversions by
 * always converting through a common base unit. </p>
 *
 * <p>Example: 1 FOOT = 12.0 inches, 1 YARD 36.0 inches,
 * 1 CENTIMETER = 0.393701 inches</p>
*/

package com.apps.quantitymeasurement;

public enum LengthUnit implements IMeasurable {
	FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

	private final double conversionFactor;

	private LengthUnit(double conversionFactor) {
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
		return convertedValue;
	}
	
	// Main method to test LengthUnit enum
	public static void main(String[] args) {
		double yard = 1.0;
		double inches = LengthUnit.YARDS.convertToBaseUnit(yard);
		System.out.println(yard + " yard is equal to " + inches + " inches"); 
		
		double feet = LengthUnit.FEET.convertFromBaseUnit(inches);
		System.out.println(inches + " inches are equal to " + feet + " feet");
	}
}
