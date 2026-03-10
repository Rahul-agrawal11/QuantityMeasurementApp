/**
 * WeightUnit.java
 * 
 * The WeightUnit enumeration implements IMeasurable interface and provides 
 * methods for unit conversion. It defines various units of weight measurement 
 * along with their conversion factors relative to a base unit (grams). This 
 * enumeration is used in the QuantityMeasurement application to facilitate 
 * conversions and comparisons between different weight units.
 * 
 * <p>The base unit for conversion is grams. Each unit stores a conversion factor 
 * relative to grams (the base unit). This design simplifies unit conversions by 
 * always converting through a common base unit. </p>
 * 
 * <p>Example: 1 KILOGRAM 1000.0 grams, 1 POUND 453.592 grams,
 * 1 OUNCE 28.3495 grams</p>
*/

package com.apps.quantitymeasurement;

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
	
	// Main method to test WeightUnit enum
	public static void main(String[] args) {
		double kilograms = 5.0;
		double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kilograms);
		System.out.println(kilograms + " kilograms are equal to " + grams + " grams");
		
		double miligrams = WeightUnit.MILIGRAM.convertFromBaseUnit(grams);
		System.out.println(miligrams);
		
	}
}
