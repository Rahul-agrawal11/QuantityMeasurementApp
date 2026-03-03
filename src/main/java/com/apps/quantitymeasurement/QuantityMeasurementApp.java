package com.apps.quantitymeasurement;

/**
 * QuantityMeasurementApp UC6 Addition Operations Between Length Measurements
 * <p>This use case extends UCS by introducing addition operations between length measurements.
 * The Quantity Length API can add two measurements of potentially different units (but same
 * category-length) and return the result as per the unit of the first operand.</p>
 * 
 * <p>Examples:
 * <ul>
 * <li>Adding 1 foot and 12 inches yields 2 feet (based on the unit of the first operand)</li>
 * <li>Measurements must belong to the same category (length) but can have different units</li>
 * <li>Result is returned as per the unit of the first operand</li>
 * </ul>
 * </p>
 * @author Developer
 * @version 1.0
 * @since UC6
*/

public class QuantityMeasurementApp {

	// ================= UC4 METHODS =================

	// Generic equality check
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	// Comparison using raw values
	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1, double value2,
			Length.LengthUnit unit2) {

		Length u1 = new Length(value1, unit1);
		Length u2 = new Length(value2, unit2);
		return demonstrateLengthEquality(u1, u2);
	}

	// ================= UC5 METHODS =================

	
	 // Convert one length to target unit
	public static Length demonstrateLengthConversion(double value, Length.LengthUnit fromUnit,
			Length.LengthUnit targetUnit) {

		Length original = new Length(value, fromUnit);
		return original.convertTo(targetUnit);
	}

	
	 // Overloaded Conversion using Length object
	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {

		return length.convertTo(toUnit);
	}
	
	// ================= UC6 METHODS =================
	
	// Add length to the target length
	public static Length demonstarteLengthAddition(Length targetLength, Length givenLength) {
		return targetLength.add(givenLength);
	}
	
	// Overloaded Method & Add two lengths and then converted them into target unit
	public static Length demonstarteLengthAddition(Length length1, Length length2, Length.LengthUnit targetUnit) {
		Length totalLength = length1.add(length2);
		return totalLength.convertTo(targetUnit);
	}
	
	// Overloaded Addition to provide flexibility
	public static Length demonstarteLengthAddition(double value1, Length.LengthUnit unit1, double value2, Length.LengthUnit unit2, Length.LengthUnit targetUnit) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		return demonstarteLengthAddition(length1, length2, targetUnit);
	}

	// ================= MAIN METHOD =================

	public static void main(String[] args) {

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES)
				.convertTo(Length.LengthUnit.INCHES));

		System.out.println(demonstrateLengthConversion(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET));

		System.out.println(demonstrateLengthConversion(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS));

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES));

		System.out.println(demonstrateLengthConversion(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
		
		Length length1 = new Length(2.0, Length.LengthUnit.FEET);
		Length length2 = new Length(12.0, Length.LengthUnit.INCHES);
		
		System.out.println(length1.add(length2));
		
		System.out.println(demonstarteLengthAddition(length1, length2, Length.LengthUnit.YARDS));
		
		System.out.println(demonstarteLengthAddition(6.0, Length.LengthUnit.INCHES, 6.0, Length.LengthUnit.INCHES, Length.LengthUnit.INCHES));
	}
}