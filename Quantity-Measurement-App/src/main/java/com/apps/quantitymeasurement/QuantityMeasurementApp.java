package com.apps.quantitymeasurement;

/**
 * QuantityMeasurementAppUC5 UC4: Extended Units UC5: Add Conversion Support
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

	// ================= UC5 METHODS (NEW) =================

	/**
	 * UC5: Convert one length to target unit
	 */
	public static Length demonstrateLengthConversion(double value, Length.LengthUnit fromUnit,
			Length.LengthUnit targetUnit) {

		Length original = new Length(value, fromUnit);
		return original.convertTo(targetUnit);
	}

	/**
	 * UC5: Overloaded Conversion using Length object
	 */
	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {

		return length.convertTo(toUnit);
	}

	// ================= MAIN METHOD =================

	public static void main(String[] args) {

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES)
				.convertTo(Length.LengthUnit.INCHES).toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET).toString()
				.split(" ")[0]);

		System.out.println(demonstrateLengthConversion(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES)
				.toString().split(" ")[0]);

		System.out.println(demonstrateLengthConversion(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES).toString()
				.split(" ")[0]);
	}
}