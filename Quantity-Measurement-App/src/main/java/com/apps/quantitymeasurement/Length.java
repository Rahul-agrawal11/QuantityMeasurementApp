package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

	// Instance variables
	private double value;
	private LengthUnit unit;

	/**
	 * Nested enumeration representing different length units. Base unit: Inches
	 */
	public enum LengthUnit {
		FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

		private final double conversionFactor;

		private LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value.");
		}
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Converts this length to base unit (inches)
	 */
	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0;
	}

	private boolean compare(Length thatLength) {
		if (thatLength == null)
			return false;
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}

	// Equals method is overridden to firstly check if the two objects are the same
	// reference.
	// If not, it checks if the other object is null or of a different class.
	// Finally, calls the compare method to determine equality based on converted
	// values.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Length that = (Length) obj;
		return compare(that);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}

	/**
	 * UC5 Conversion Method
	 */
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		// Convert to base (inches)
		double baseValue = convertToBaseUnit();

		// Convert from inches to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Length(convertedValue, targetUnit);
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	// Main method for standalone testing
	public static void main(String[] args) {

		// 1 Foot = 12 Inches
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("1 Foot == 12 Inches -> " + length1.equals(length2));

		// 1 Yard = 36 Inches
		Length length3 = new Length(1.0, LengthUnit.YARDS);
		Length length4 = new Length(36.0, LengthUnit.INCHES);
		System.out.println("1 Yard == 36 Inches -> " + length3.equals(length4));

		// 100 cm ≈ 39.3701 Inches
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);
		System.out.println("100 CM == 39.3701 Inches -> " + length5.equals(length6));

		// Conversion Test
		Length feet = new Length(2.0, LengthUnit.FEET);
		Length convertedToYards = feet.convertTo(LengthUnit.YARDS);
		System.out.println("2 Feet in Yards = " + convertedToYards);

		Length convertedToCm = feet.convertTo(LengthUnit.CENTIMETERS);
		System.out.println("2 Feet in CM = " + convertedToCm);

		// toString Test
		System.out.println("String Representation: " + feet);

		// Exception Test
		try {
			new Length(Double.NaN, LengthUnit.FEET);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception caught for invalid value: " + e.getMessage());
		}

		try {
			new Length(5.0, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception caught for null unit: " + e.getMessage());
		}

		try {
			feet.convertTo(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception caught for null target unit: " + e.getMessage());
		}
	}
}
