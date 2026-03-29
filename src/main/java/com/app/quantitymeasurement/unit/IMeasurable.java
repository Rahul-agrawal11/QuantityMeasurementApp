package com.app.quantitymeasurement.unit;

@FunctionalInterface
interface SupportsArithmetic {
	boolean isSupported();
}

public interface IMeasurable {
	// Default lambda – arithmetic supported
	SupportsArithmetic supportsArithmetic = () -> true;

	// PART 1: The following methods are Mandatory conversion methods for all
	// measurable units
	public double getConversionFactor();

	public double convertToBaseUnit(double value);

	public double convertFromBaseUnit(double baseValue);

	public String getUnitName();

	// PART 2: The following methods are Optional and can be implemented by specific
	// measurable units if needed
	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	// Runtime validation hook
	default void validateOperationSupport(String operation) {
		// Default: allow operations
	}

	// returns class name e.g. "WeightUnit"
	public String getMeasurementType();

	// NEW - resolves unit name string to IMeasurable instance
	public IMeasurable getUnitInstance(String unitName);
}
