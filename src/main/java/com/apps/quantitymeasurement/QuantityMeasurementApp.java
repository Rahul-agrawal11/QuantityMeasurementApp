/**
* QuantityMeasurementApp — UC10 — Generic Quantity Class with Unit Interface for
* Multi-Category Support
*
* This class demonstrates the complete functionality of the Quantity Measurement system 
* by showcasing all operations including Comparison, Conversion, and Addition of quantities.
* 
* <p>
* <b>Use Cases Covered:</b>
* <ul>
* 	<li><b>Comparison:</b> Compare two quantities of the same type to determine equality or magnitude</li>
* 	<li><b>Conversion:</b> Convert quantities from one unit to another within the same measurement type</li>
* 	<li><b>Addition:</b> Add two quantities of compatible types and return the result in a specified unit</li>
* </ul>
* </p>
*
* <p>
* <b>Architecture:</b>
* This class acts as a demonstration/driver class that internally utilizes the {@link Quantity} class
* to perform all mathematical and conversion operations on various measurement types such as:
* <ul>
* 	<li>Length (Feet, Inch, Yard, Centimeter, etc.)</li>
* 	<li>Volume (Liter, Milliliter, Gallon, Pint, etc.)</li>
* 	<li>Weight (Kilogram, Gram, Tonne, Pound, etc.)</li>
* 	<li>Temperature (Celsius, Fahrenheit)</li>
* </ul>
* </p>
*/

package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	
	/**
	 * Demonstrate Equality Comparison between two quantities.
	 * 
	 * @param quantity1 the first quantity to compare
	 * @param quantity2 the second quantity to compare
	 * @return true if quantities are equal, false otherwise
	 */
	
	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.equals(quantity2);
	}
	
	/**
	 * Demonstrate Conversion of a quantity to a target unit.
	 * 
	 * @param quantity the quantity to convert
	 * @param targetUnit the target unit for conversion
	 * @return a new Quantity with the converted value and unit
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		double convertedValue = quantity.convertTo(targetUnit);
		return new Quantity<U>(convertedValue, targetUnit);
	}
	
	/**
	 * Demonstrate Addition of two quantities and return the result in the unit of the first quantity.
	 * 
	 * @param quantity1 the first quantity to add
	 * @param @param quantity1 the first quantity to add
	 * @return a new Quantity representing the sum
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,
			Quantity<U> quantity2) {
		return quantity1.add(quantity2);
	}
	
	/**
	 * Demonstrate Addition of two quantities and return the result in a specified target unit.
	 * 
	 * @param quantity1 the first quantity to add
	 * @param quantity2 the second quantity to add
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the sum in the target unit
	*/
	
	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2,
			U targetUnit) {
		return quantity1.add(quantity2, targetUnit);
	}
	

	// ================= MAIN METHOD =================

	public static void main(String[] args) {
		
		// Demonstration equality between the two quantities
		Quantity<LengthUnit> lengthInInches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> lengthInFeet = new Quantity<>(2.0, LengthUnit.FEET);
		boolean areEqual = demonstrateEquality(lengthInInches, lengthInFeet);
		System.out.println("Are lengths equal? " + areEqual);
		
		System.out.println("Hashcode: " + lengthInInches.hashCode() + " " + lengthInFeet.hashCode());

		Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		System.out.println("Are weights equal? " + demonstrateEquality(weightInGrams, weightInKilograms));
		
		// Demonstration conversion between the two quantities
		Quantity<LengthUnit> convertedLength = demonstrateConversion(lengthInInches, LengthUnit.FEET);
		System.out.println("Converted Length: " + convertedLength.getValue() + " " + convertedLength.getUnit());

		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInKilograms, WeightUnit.POUND);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " + convertedWeight.getUnit());
		
		// Demonstration addition of two quantities and return the result in the unit
		// of the first quantity
		Quantity<LengthUnit> lengthInYards = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> sumLength = demonstrateAddition(lengthInFeet, lengthInYards);
		System.out.println("Sum Length: " + sumLength.getValue() + " " + sumLength.getUnit());

		Quantity<WeightUnit> weightInPound = new Quantity<>(1.0, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPound);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " + sumWeight.getUnit());
		
		// Demonstration addition of two quantities and return the result in a specified
		// target unit
		Quantity<LengthUnit> lengthInCm = new Quantity<>(39.3701, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> sumLengthInYards = demonstrateAddition(lengthInInches, lengthInCm, LengthUnit.YARDS);
		System.out.println("Sum Length in Yards: " + sumLengthInYards.getValue() + " " + sumLengthInYards.getUnit());

		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPound, WeightUnit.GRAM);
		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());

	}
}