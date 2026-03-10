/**
 * IMeasurable interface defines the contract for measurable units.
 * 
 * This inerface serves as a common abstraction for diifferent types of measurements
 * such as weight and length units. Classes implementing this interface should provide
 * functionality to handle unit conversions and comparisons between different measurement types.
 * 
 * @see WeightUnit
 * @see LengthUnit
 */

package com.apps.quantitymeasurement;

public interface IMeasurable {
	
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	
	public double convertFromBaseUnit(double baseValue);
	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface!");	
	}
}
