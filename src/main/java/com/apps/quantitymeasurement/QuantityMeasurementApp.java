package com.apps.quantitymeasurement;

/**
 * Quantity Measurement Application
 *
 * <p>This application demonstrates measurement operations for different
 * physical quantities such as Length and Weight. Each quantity uses
 * a dedicated Unit enum that defines conversion factors relative
 * to a base unit.
 *
 * <p>Supported Features:
 * <ul>
 *     <li>Unit Equality Comparison</li>
 *     <li>Unit Conversion</li>
 *     <li>Addition of Quantities with Different Units</li>
 *     <li>Conversion of Result to a Target Unit</li>
 * </ul>
 *
 * <p>Length functionality was implemented in earlier use cases,
 * while UC9 extends similar capabilities to Weight measurements.
 *
 * <p>Base Units Used:
 * <ul>
 *     <li>Length → Base Unit: Inches</li>
 *     <li>Weight → Base Unit: Grams</li>
 * </ul>
 *
 * <p>This class acts as a demonstration utility for testing
 * measurement operations using Length and Weight objects.
 */

public class QuantityMeasurementApp {

	/*
	 * ------------------------------------------------------------
	 * Weight Demonstration Methods
	 * ------------------------------------------------------------
	 */

	/*
	 * Demonstrates equality comparison between two Weight objects.
	 */
	public static boolean demonstrateWeightEquality(Weight weight1, Weight weight2) {
		return weight1.equals(weight2);
	}

	/*
	 * Demonstrates equality comparison between two weights defined
	 * by primitive values and WeightUnit.
	 */
	public static boolean demonstrateWeightComparison(double value1, WeightUnit unit1,
			double value2, WeightUnit unit2) {

		Weight weight1 = new Weight(value1, unit1);
		Weight weight2 = new Weight(value2, unit2);
		return demonstrateWeightEquality(weight1, weight2);
	}

	/*
	 * Demonstrates conversion of a weight value from one unit to another.
	 */
	public static Weight demonstarteWeightConversion(double value, WeightUnit fromUnit, WeightUnit targetUnit) {
		Weight original = new Weight(value, fromUnit);
		return original.convertTo(targetUnit);
	}

	/*
	 * Demonstrates conversion of a Weight object to another unit.
	 * Method overloading is used here.
	 */
	public static Weight demonstarteWeightConversion(Weight weight, WeightUnit toUnit) {
		return weight.convertTo(toUnit);
	}

	/*
	 * Demonstrates addition of two Weight objects.
	 */
	public static Weight demonstrateWeightAddition(Weight targetWeight, Weight givenWeight) {
		return targetWeight.add(givenWeight);
	}

	/*
	 * Demonstrates addition of two weights and converts the result
	 * to a specified target unit.
	 */
	public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2, WeightUnit targetUnit) {

		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}

		Weight totalWeight = weight1.add(weight2);
		return totalWeight.convertTo(targetUnit);
	}

	/*
	 * ------------------------------------------------------------
	 * Length Demonstration Methods
	 * ------------------------------------------------------------
	 */

	/*
	 * Demonstrates equality comparison between two Length objects.
	 */
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	/*
	 * Demonstrates equality comparison between two lengths defined
	 * by primitive values and LengthUnit.
	 */
	public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1,
			double value2, LengthUnit unit2) {

		Length u1 = new Length(value1, unit1);
		Length u2 = new Length(value2, unit2);

		return demonstrateLengthEquality(u1, u2);
	}

	/*
	 * Demonstrates conversion of a length value from one unit to another.
	 */
	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit,
			LengthUnit targetUnit) {

		Length original = new Length(value, fromUnit);
		return original.convertTo(targetUnit);
	}

	/*
	 * Demonstrates conversion of a Length object to another unit.
	 * Method overloading is used.
	 */
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	/*
	 * Demonstrates addition of two Length objects.
	 */
	public static Length demonstarteLengthAddition(Length targetLength, Length givenLength) {
		return targetLength.add(givenLength);
	}

	/*
	 * Demonstrates addition of two lengths and converts the result
	 * into a specified target unit.
	 */
	public static Length demonstarteLengthAddition(Length length1, Length length2, LengthUnit targetUnit) {

		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}

		Length totalLength = length1.add(length2);
		return totalLength.convertTo(targetUnit);
	}

	/*
	 * Overloaded method to allow primitive inputs instead of Length objects.
	 */
	public static Length demonstarteLengthAddition(double value1, LengthUnit unit1,
			double value2, LengthUnit unit2, LengthUnit targetUnit) {

		Length length1 = new Length(value1, unit1);
		Length length2 = new Length(value2, unit2);

		return demonstarteLengthAddition(length1, length2, targetUnit);
	}

	// ================= MAIN METHOD =================

	public static void main(String[] args) {

		/**
		 * ============================================================
		 * LENGTH MEASUREMENT DEMONSTRATION
		 * ============================================================
		 */
		
		System.out.println("----- LENGTH OPERATIONS -----");
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

		System.out.println(demonstarteLengthAddition(6.0, LengthUnit.INCHES,
				6.0, LengthUnit.INCHES, LengthUnit.INCHES));



		/**
		 * ============================================================
		 * WEIGHT MEASUREMENT DEMONSTRATION
		 * ============================================================
		 */
		
		System.out.println("\n----- WEIGHT OPERATIONS -----");
		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.GRAM, 1.0, WeightUnit.KILOGRAM));

		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.MILIGRAM, 1.0, WeightUnit.GRAM));

		System.out.println(demonstrateWeightComparison(1000.0, WeightUnit.KILOGRAM, 1.0, WeightUnit.TONNE));

		System.out.println(demonstarteWeightConversion(1.0, WeightUnit.TONNE, WeightUnit.KILOGRAM));

		System.out.println(demonstarteWeightConversion(2.0, WeightUnit.POUND, WeightUnit.GRAM));

		System.out.println(demonstarteWeightConversion(5000.0, WeightUnit.MILIGRAM, WeightUnit.GRAM));
		
		System.out.println(demonstarteWeightConversion(0.0, WeightUnit.GRAM, WeightUnit.KILOGRAM));

		Weight weight1 = new Weight(500.0, WeightUnit.GRAM);
		Weight weight2 = new Weight(1.0, WeightUnit.KILOGRAM);
		System.out.println(demonstrateWeightAddition(weight1, weight2));

		System.out.println(demonstrateWeightAddition(weight1, weight2, WeightUnit.POUND));

		Weight weight3 = new Weight(1.0, WeightUnit.TONNE);
		Weight weight4 = new Weight(500.0, WeightUnit.KILOGRAM);
		System.out.println(demonstrateWeightAddition(weight3, weight4, WeightUnit.TONNE));
	}
}