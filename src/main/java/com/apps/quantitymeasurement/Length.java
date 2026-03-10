
package com.apps.quantitymeasurement;

public class Length {

	// Instance variables
	private double value;
	private LengthUnit unit;

	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Equals method is overridden to firstly check if the two objects are the same
	 * reference. If not, it checks if the other object is null or of a different
	 * class. Finally, calls the compare method to determine equality based on
	 * converted values.
	 */
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
	    return Double.hashCode(unit.convertToBaseUnit(value));
	}

	/**
	 *  Conversion Method
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

	/**
	 * Add two lengths into one specific target unit
	 */
	public Length add(Length thatLength) {

		if (thatLength == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}

		if (this.unit == thatLength.unit) {
			return new Length(this.value + thatLength.value, this.unit);
		}

		double thisInBase = this.convertToBaseUnit();
		double thatInBase = thatLength.convertToBaseUnit();

		double totalInBase = thisInBase + thatInBase;
		double convertedValue = convertFromBaseUnitToTargetUnit(totalInBase, this.unit);

		return new Length(convertedValue, this.unit);
	}
	
	/**
	 * Adding length to this length with specific target unit
	 */
	public Length add(Length length, LengthUnit targetUnit) {
		return addAndConvert(length, targetUnit);
	}

	/**
	 * Add two lengths and convert them into specific target unit
	 */
	private Length addAndConvert(Length length, LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		Length len = this.add(length);
		double lengthInInches = len.convertToBaseUnit();
		double lengthInTargetUnit = convertFromBaseUnitToTargetUnit(lengthInInches, targetUnit);
		return new Length(lengthInTargetUnit, targetUnit);
	}

	/**
	 * Compare two lengths
	 */
	private boolean compare(Length thatLength) {
		if (thatLength == null)
			return false;
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}

	/**
	 * Converts this length to base unit (inches)
	 */
	private double convertToBaseUnit() {
	    return unit.convertToBaseUnit(value);
	}

	// Convert base unit to target unit
	private double convertFromBaseUnitToTargetUnit(double baseValue, LengthUnit targetUnit) {
	    return baseValue / targetUnit.getConversionFactor();
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

		// 12 Inches + 1 Foot = 24 Inches
		Length inches = new Length(12.0, LengthUnit.INCHES);
		Length foot = new Length(1.0, LengthUnit.FEET);
		System.out.println("12 Inches + 1 Foot = " + inches.add(foot));

		// 2.54 Centimeters + 1 Inches = ~5.08 Centimeters
		Length cm = new Length(2.54, LengthUnit.CENTIMETERS);
		Length inch = new Length(1.0, LengthUnit.INCHES);
		System.out.println("2.54 Centimeters + 1 Inches = " + cm.add(inch));

		// 1.0 Foot + 24.0 Inches = 1 Yard
		Length fe = new Length(1.0, LengthUnit.FEET);
		Length in = new Length(24.0, LengthUnit.INCHES);
		System.out.println("1.0 Foot + 24.0 Inches = " + fe.add(in, LengthUnit.YARDS));

		// 36.0 Inches + 1.0 Yard = 6 Feet
		Length inchs = new Length(36.0, LengthUnit.INCHES);
		Length yard = new Length(1.0, LengthUnit.YARDS);
		System.out.println("36.0 Inches + 1.0 Yard = " + inchs.add(yard, LengthUnit.FEET));

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

		try {
			inches.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Exception caught for null target unit: " + e.getMessage());
		}
	}
}
