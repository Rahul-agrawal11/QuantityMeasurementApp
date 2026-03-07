package com.apps.quantitymeasurement;

/**
 * QuantityMeasurementApp UC8 
 * Refactoring Unit Enum to Standalone Class
 * <p>UC8 refactors the design from UC1-UC7 to overcome the disadvantage of
 * embedding the LengthUnit enum within the QuantityLength class. Since UC8
 * extracts the LengthUnit enum into a standalone class, hence it is needed to
 * update the QuantityMeasurementAppUC8 and QuantityLength classes to import 
 * the LengthUnit from the new standalone class and refactor the code accordingly.</p>
 * 
 * <p>Examples:
 * <ul>
 * 		<li>LengthUnit enum is now a standalone class in Quantity MeasurementApp package</li>
 * 		<li>QuantityLength and QuantityMeasurementAppUC8 classes import LengthUnit</li>
 * 		<li>All references to LengthUnit enum are updated accordingly</li>
 * </ul>
 * </p>
 * 
 */

public class QuantityMeasurementApp {

	// Generic equality check
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	// Comparison using raw values
	public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1, double value2,
			LengthUnit unit2) {

		Length u1 = new Length(value1, unit1);
		Length u2 = new Length(value2, unit2);
		return demonstrateLengthEquality(u1, u2);
	}

	// Convert one length to target unit
	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit,
			LengthUnit targetUnit) {

		Length original = new Length(value, fromUnit);
		return original.convertTo(targetUnit);
	}

	// Overloaded Conversion using Length object
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	// Add length to the target length
	public static Length demonstarteLengthAddition(Length targetLength, Length givenLength) {
		return targetLength.add(givenLength);
	}

	// Overloaded Method & Add two lengths and then converted them into target unit
	public static Length demonstarteLengthAddition(Length length1, Length length2, LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		Length totalLength = length1.add(length2);
		return totalLength.convertTo(targetUnit);
	}

	// Overloaded Addition to provide flexibility
	public static Length demonstarteLengthAddition(double value1, LengthUnit unit1, double value2,
			LengthUnit unit2, LengthUnit targetUnit) {
		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);
		return demonstarteLengthAddition(length1, length2, targetUnit);
	}

	// ================= MAIN METHOD =================

	public static void main(String[] args) {

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES)
				.convertTo(LengthUnit.INCHES));

		System.out.println(demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET));

		System.out.println(demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));

		System.out.println(demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES));

		Length length1 = new Length(2.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);

		System.out.println(length1.add(length2));

		System.out.println(demonstarteLengthAddition(length1, length2, LengthUnit.YARDS));

		System.out.println(demonstarteLengthAddition(6.0, LengthUnit.INCHES, 6.0, LengthUnit.INCHES,
				LengthUnit.INCHES));
	}
}