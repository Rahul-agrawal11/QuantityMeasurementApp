/**
 * Represents a quantity with a numeric value and a unit of measurement.
 * 
 * This class encapsulate a numeric value along with an associated measurable unit,
 * allowing for flexible representation of measurements across different unit types.
 * 
 */

package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
	private double value;
	private U unit;
	
	public Quantity(double value, U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}
	
	/**
	 * Converts this Quantity to the specified target unit.
	 * 
	 * <p> This method first converts the current value to the base unit using the 
	 * convertToBaseUnit method of the current unit, then converts that base value 
	 * to the target unit using the convertFromBaseUnit method of the target unit.
	 */
	
	public <U extends IMeasurable> double convertTo(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		double baseValue = this.convertToBaseUnit(this.value);
		
		double convertedValue = baseValue / targetUnit.getConversionFactor();
		
		return Math.round(convertedValue * 100.0) / 100.0;
	}
	
	private double convertToBaseUnit(double value) {
		
		return Math.round(value * this.unit.getConversionFactor() * 100.0) / 100.0;
	}
	
	/**
	 * Adds this Quantity to another Quantity of the same unit type.
	 * 
	 * <p> This method converts both quantities to their base unit, adds the values, 
	 * and then converts the sum back to the unit of this Quantity.
	 */
	
	public Quantity<U> add(Quantity<U> other) {
		if (other == null) {
			throw new IllegalArgumentException("Other cannot be null");
		}

		if (this.unit == other.unit) {
			return new Quantity<>(this.value + other.value, this.unit);
		}
		
		double otherBase = other.convertToBaseUnit(other.value);
		double thisBase = this.convertToBaseUnit(this.value);
		
		double sumOfBase = otherBase + thisBase;
		
		double finalSum = sumOfBase / this.unit.getConversionFactor();
		
		return new Quantity<>(finalSum, this.unit);
	}
	
	/**
	 * Adds this Quantity to another Quantity of the same unit type and returns 
	 * the result in the specified target unit.
	 * 
	 * <p> This method converts both quantities to their base unit, adds the values, 
	 * and then converts the sum to the specified target unit.
	 */
	
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		Quantity<U> totalValue = this.add(other);
		double totalInTargetUnit = totalValue.convertTo(targetUnit);
		return new Quantity<U>(totalInTargetUnit, targetUnit);
	}
	
	/**
	* Compares this Quantity with another object for equality. Two Quantity 
	* objects are considered equal if they represent the same measurement value
	* when converted to their respective base units.
	*
	* Logic to compare two Quantity objects:
	* 1. Check if the other object is an instance of Quantity.
	* 2. If not, return false.
	* 3. If yes, convert both Quantity values to their base units using the 
	* 	convertToBaseUnit method of their respective units.
	* 4. Compare the converted values for equality.
	* 5. Return true if they are equal, false otherwise.
	*/
	
	@Override
	public boolean equals(Object obj) {

	    if (this == obj) {
	        return true;
	    }

	    if (!(obj instanceof Quantity<?> that)) {
	        return false;
	    }

	    return this.compare(that);
	}
	
	@Override
	public int hashCode() {
	    double baseValue = unit.convertToBaseUnit(value);
	    return Double.hashCode(baseValue);
	}

	private boolean compare(Quantity<?> that) {

	    if (that == null) {
	        return false;
	    }

	    double thisBase = this.convertToBaseUnit(this.value);
	    double thatBase = that.convertToBaseUnit(that.value);

	    return Double.compare(thisBase, thatBase) == 0;
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	public static void main(String[] args) {
		
		// Conversion method 
		Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.YARDS);
		System.out.println("10 yards = " + lengthInFeet.convertTo(LengthUnit.INCHES) + " inches");
		
		Quantity<WeightUnit> weightInKG = new Quantity<>(5.0, WeightUnit.KILOGRAM);
		System.out.println("5 KG = " + weightInKG.convertTo(WeightUnit.GRAM) + " grams");
		
		// Exception case of conversion method
		try {
			Quantity<WeightUnit> weightInTonne = new Quantity<>(1.0, WeightUnit.TONNE);
			System.out.println(weightInTonne.convertTo(null));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		// Addition method 
		Quantity<LengthUnit> lengthInYards = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> lengthInInch = new Quantity<>(36.0, LengthUnit.INCHES);
		System.out.println("1 yard + 36 inches = " + lengthInYards.add(lengthInInch));
		
		Quantity<WeightUnit> weightInGram = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> weightInMiligram = new Quantity<>(10000.0, WeightUnit.MILIGRAM);
		System.out.println("1000 gram + 10000 miligram = " + weightInGram.add(weightInMiligram));
		
		// Exception case of addition method
		try {
			weightInGram.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		// Overloaded addition method with specific target unit
		System.out.println(lengthInYards.add(lengthInInch, LengthUnit.FEET));
		
		System.out.println(weightInGram.add(weightInMiligram, WeightUnit.KILOGRAM));
		
		// Equal Method example
		Quantity<LengthUnit> lengthInCm = new Quantity<>(254.0, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> lengthInInches = new Quantity<>(100.0, LengthUnit.INCHES);
		System.out.println("254.0 centimeters = 100.0 inches -> " + lengthInCm.equals(lengthInInches));
		
		Quantity<WeightUnit> lengthInPound = new Quantity<>(1000.0, WeightUnit.POUND);
		Quantity<WeightUnit> lengthInKilogram = new Quantity<>(453.592, WeightUnit.KILOGRAM);
		System.out.println("1000.0 pound = 453.592 kilogram -> " + lengthInPound.equals(lengthInKilogram));
		
	}
}
