/**
* QuantityMeasurementApp UC12 extends UC11 to implement subtraction and division operations
* for quantity measurement.
* 
* This class provides functionality to perform subtraction and division operations on
* different units of measurement. It extends the capabilities of UC11 by adding support for
* these additional arithmetic operations.
* 
* Key Features:
* 1. Subtraction of quantities with automatic unit conversion
* 2. Division of quantities with automatic unit conversion
* 3. Demonstration methods showcasing subtraction operations
* 4. Demonstration methods showcasing division operations.
*
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
	
	/**
	 * Demonstrate Subtraction of two quantities and return result in the unit of the first quantity.
	 * 
	 * @param quantity1 the first quantity to subtract from
	 * @param quantity1 the first quantity to subtract from
	 * @return a new Quantity representing the difference
	 * @throws IllegalArgumentException if the result is negative
	 * 
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction (Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.subtract(quantity2);
	}
	
	/**
	 * Demonstrate Subtraction of two quantities and return result in a specified target unit.
	 * 
	 * @param quantity1 the first quantity to subtract from
	 * @param quantity2 the second quantity to subtract
	 * @param targetUnit the target unit for the result
	 * @return a new Quantity representing the difference in the target unit
	 */
	
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction (Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
		return quantity1.subtract(quantity2, targetUnit);
	}
	
	/**
	* Demonstrate Division of two quantities and return result in double.
	*
	* @param quantity1 the first quantity to divide
	* @param quantity2 the second quantity to divide by
	* @return result of the division as a double
	* @throws IllegalArgumentException if division by zero occurs
	*/
	
	public static <U extends IMeasurable> double demonstrateDivision (Quantity<U> quantity1, Quantity<U> quantity2) {
		return quantity1.divide(quantity2);
	}
	
	// ================= MAIN METHOD =================

	public static void main(String[] args) {
		
		// Demonstration equality between the two quantities
		Quantity<LengthUnit> lengthInInches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> lengthInFeet = new Quantity<>(2.0, LengthUnit.FEET);
		boolean areEqual = demonstrateEquality(lengthInInches, lengthInFeet);
		System.out.println("Are lengths equal? " + areEqual);

		Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		System.out.println("Are weights equal? " + demonstrateEquality(weightInGrams, weightInKilograms));
		
		Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		System.out.println("Are volumes equal? " + demonstrateEquality(volume1, volume2));
		
		// Demonstration conversion between the two quantities
		Quantity<LengthUnit> convertedLength = demonstrateConversion(lengthInInches, LengthUnit.FEET);
		System.out.println("Converted Length: " + convertedLength.getValue() + " " + convertedLength.getUnit());

		Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInKilograms, WeightUnit.POUND);
		System.out.println("Converted Weight: " + convertedWeight.getValue() + " " + convertedWeight.getUnit());
		
		Quantity<VolumeUnit> convertedVolume = demonstrateConversion(volume1, VolumeUnit.GALLON);
		System.out.println("Converted Volume: " + convertedVolume.getValue() + " " + convertedVolume.getUnit());
		
		// Demonstration addition of two quantities and return the result in the unit of the first quantity
		Quantity<LengthUnit> lengthInYards = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> sumLength = demonstrateAddition(lengthInFeet, lengthInYards);
		System.out.println("Sum Length: " + sumLength.getValue() + " " + sumLength.getUnit());

		Quantity<WeightUnit> weightInPound = new Quantity<>(1.0, WeightUnit.POUND);
		Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPound);
		System.out.println("Sum Weight: " + sumWeight.getValue() + " " + sumWeight.getUnit());
		
		Quantity<VolumeUnit> volume3 = new Quantity<>(0.264172, VolumeUnit.GALLON);
		Quantity<VolumeUnit> sumVolume = demonstrateAddition(volume1, volume3);
		System.out.println("Sum Volume: " + sumVolume.getValue() + " " + sumVolume.getUnit());
		
		// Demonstration addition of two quantities and return the result in a specified target unit
		Quantity<LengthUnit> lengthInCm = new Quantity<>(39.3701, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> sumLengthInYards = demonstrateAddition(lengthInInches, lengthInCm, LengthUnit.YARDS);
		System.out.println("Sum Length in Yards: " + sumLengthInYards.getValue() + " " + sumLengthInYards.getUnit());

		Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPound, WeightUnit.GRAM);
		System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());
		
		Quantity<VolumeUnit> sumVolumeInMillilitre = demonstrateAddition(volume1, volume3, VolumeUnit.MILLILITRE);
		System.out.println("Sum Volume in Grams: " + sumVolumeInMillilitre.getValue() + " " + sumVolumeInMillilitre.getUnit());
		
		try {
			Quantity<VolumeUnit> volume = new Quantity<>(1.0, null);
	    	Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
	    	System.out.println(volume.equals(litre));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
    	Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
    	System.out.println(gallon.add(litre));
    	
    	// Subtraction with Implicit Target Unit
    	Quantity<LengthUnit> feetSubtraction = demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES));
    	System.out.println("10.0 feet - 6.0 inches = " + feetSubtraction);
    			
    	Quantity<WeightUnit> kgSubtraction = demonstrateSubtraction(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5000.0, WeightUnit.GRAM));
    	System.out.println("10.0 kg - 5000.0 grams = " + kgSubtraction);
    			
    	Quantity<VolumeUnit> litreSubtraction = demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));
    	System.out.println("5.0 litre - 500.0 millilitre = " + litreSubtraction);
    			
    	// Subtraction with Explicit Target Unit
    	Quantity<LengthUnit> feetMinus = demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
    	System.out.println("10.0 feet - 6.0 inches = " + feetMinus);
    			
    	Quantity<WeightUnit> kgMinus = demonstrateSubtraction(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM);
    	System.out.println("10.0 kg - 5000.0 grams = " + kgMinus);
    			
    	Quantity<VolumeUnit> litreMinus = demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
    	System.out.println("5.0 litre - 2.0 litre = " + litreMinus);
    			
    	// Subtraction Resulting in Negative Values
    	Quantity<LengthUnit> negFeet = demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
    	System.out.println("5.0 feet - 10.0 feet = " + negFeet);
    			
    	Quantity<WeightUnit> negKG = demonstrateSubtraction(new Quantity<>(2.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM));
    	System.out.println("2.0 kg - 5.0 kg = " + negKG);
    			
    	// Subtraction Resulting in Zero
    	Quantity<LengthUnit> zeroLength = demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(120.0, LengthUnit.INCHES));
    	System.out.println("10.0 feet - 120.0 inches = " + zeroLength);
    			
    	Quantity<VolumeUnit> zeroVolume = demonstrateSubtraction(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
    	System.out.println("1.0 litre - 1000.0 millilitre = " + zeroVolume);
    			
    	// Division Operations
    	double feetDivision = demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(2.0, LengthUnit.FEET));
    	System.out.println("10.0 feet / 2.0 feet = " + feetDivision + " feet");
    			
    	double inchDivision = demonstrateDivision(new Quantity<>(24.0, LengthUnit.INCHES), new Quantity<>(2.0, LengthUnit.FEET));
    	System.out.println("24.0 inches / 2.0 feet = " + inchDivision + " inch");
    			
    	double kgDivision = demonstrateDivision(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM));
    	System.out.println("10.0 kg / 5.0 kg = " + kgDivision + " kilogram");
    			
    	double litreDivision = demonstrateDivision(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(10.0, VolumeUnit.LITRE));
    	System.out.println("5.0 litre / 10.0 litre = " + litreDivision + " litre");
    			
    	// Division with Different Units (Same Category):
    	double inchFeetDivision = demonstrateDivision(new Quantity<>(12.0, LengthUnit.INCHES), new Quantity<>(1.0, LengthUnit.FEET));
    	System.out.println("12.0 inches / 1.0 feet = " + inchFeetDivision + " inch");

    	double gramKgDivision = demonstrateDivision(new Quantity<>(2000.0, WeightUnit.GRAM), new Quantity<>(1.0, WeightUnit.KILOGRAM));
    	System.out.println("2000.0 gram / 1.0 kilogram = " + gramKgDivision + " grams");

    	double milliLitreLitreDivision = demonstrateDivision(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
    	System.out.println("1000.0 millilitre / 1.0 litre = " + milliLitreLitreDivision + " mililitre");
    			
    	// Error case
    	try {
    		demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), null);
    	} catch (IllegalArgumentException e) {
    		System.out.println(e.getMessage());
    	}
    			
    	try {
    		demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET));
    	} catch (ArithmeticException e) {
    		System.out.println(e.getMessage());
    	}
    			
    	try {
//    		 demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.KILOGRAM));
    	} catch (IllegalArgumentException e) {
    		System.out.println(e.getMessage());
    	}
	}
}