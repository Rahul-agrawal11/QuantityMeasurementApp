package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

	private double value;
	private U unit;

	// Constructor
	public QuantityModel(double value, U unit) {
		this.value = value;
		this.unit = unit;
	}

	// Getters
	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	// Setters (optional but useful)
	public void setValue(double value) {
		this.value = value;
	}

	public void setUnit(U unit) {
		this.unit = unit;
	}

	// toString()
	@Override
	public String toString() {
		return "QuantityModel [value=" + value + ", unit=" + unit + "]";
	}
}