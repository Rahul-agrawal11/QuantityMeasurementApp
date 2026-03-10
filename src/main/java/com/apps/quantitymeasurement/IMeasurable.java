/**
 * IMeasurable interface defines the contract for measurable units.
 * 
 * This inerface serves as a common abstraction for diifferent types of measurements
 * such as weight and length units. Classes implementing this interface should provide
 * functionality to handle unit conversions and comparisons between different measurement types.
 * 
 * @see WeightUnit
 * @see LengthUnit
 * @see VolumeUnit
 */

package com.apps.quantitymeasurement;

	@FunctionalInterface
	interface SupportsArithmetic {
		boolean isSupported();
	}

public interface IMeasurable {
	
	// Default lambda – arithmetic supported
    SupportsArithmetic supportsArithmetic = () -> true;
	
	public double getConversionFactor();
	
	public double convertToBaseUnit(double value);
	
	public double convertFromBaseUnit(double baseValue);
	
	public String getUnitName();
	
	// Optional capability check
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }
    
    // Runtime validation hook
    default void validateOperationSupport(String operation) {
        // Default: allow operations
    }
	
	public static void main(String[] args) {
		System.out.println("IMeasurable Interface!");	
	}
	
}
