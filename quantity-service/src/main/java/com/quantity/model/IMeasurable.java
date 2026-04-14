package com.quantity.model;

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