package com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable{
	 CELSIUS(false),
	 FAHRENHEIT(true);
	
	
	private final Function<Double,Double> conversionValue;
	
	SupportsArithmetic supportsArithmetic=()->false;
	
	TemperatureUnit(boolean isFahrenheit) {
		if (isFahrenheit) {
            conversionValue = (f) -> (f - 32) * 5 / 9;
        } else {
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
            return (baseValue * 9 / 5) + 32;
        }

        return baseValue;
	}
	
	 @Override
	 public boolean supportsArithmetic() {
	        return supportsArithmetic.isSupported();
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

	            throw new UnsupportedOperationException(
	                    this.name() + " does not support " + operation + " operations."
	            );
	        }
	    }

}