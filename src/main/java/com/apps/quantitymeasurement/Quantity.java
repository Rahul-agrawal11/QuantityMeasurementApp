/**
* In UC13, the Quantity class has been further enhanced to ensure DRY principles by
* introducing the following:
* 1. The private method validateArithmeticOperands to centralize 
*    validation logic for arithmetic operations.
* 2. Additionally, an internal enum ArithmeticOperation has been added to 
*    represent the type of arithmetic operation being performed,
* 3. The performArithmetic method has been added to execute the specified 
*    arithmetic operation on the base unit values of the quantities involved. 
*    
* The performArithmetic method now handles addition, subtraction, and division 
* operations in a unified manner, reducing code duplication and improving readability.
**/


package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
	private double value;
	private U unit;
	
	private static final double EPSILON = 1e-9;
	
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
	
	public double toBaseUnit() {
		return this.convertToBaseUnit(this.value);
	}
	
	// Arithmetic enum
	private enum ArithmeticOperation {
		
		ADD {
			@Override
			double compute(double a, double b) {
				return a + b;
			}
		},
		
		SUBTRACT {
			@Override
			double compute(double a, double b) {
				return a - b;
			}
		}, 
		
		DIVIDE {
			@Override
            double compute(double a, double b) {
                if (Math.abs(b) < EPSILON)
                    throw new ArithmeticException("Division by zero");
                return a / b;
            }
		};
		
		abstract double compute(double a, double b);
	}
	
	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
		if (other == null) {
			throw new IllegalArgumentException("Other cannot be null.");
		}
		
		if (targetUnitRequired && targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		
		if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");
    }
	
	// Core Base Arithmetic

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

        double base1 = this.toBaseUnit();
        double base2 = other.toBaseUnit();

        return operation.compute(base1, base2);
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
		double baseValue = this.unit.convertToBaseUnit(this.value);
		
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
		
		return round(convertedValue);
	}
	
	private double convertToBaseUnit(double value) {
	    return this.unit.convertToBaseUnit(value);
	}
	
	/**
	 * Adds this Quantity to another Quantity of the same unit type.
	 * 
	 * <p> This method converts both quantities to their base unit, adds the values, 
	 * and then converts the sum back to the unit of this Quantity.
	 */
	
	public Quantity<U> add(Quantity<U> other) {
		unit.validateOperationSupport("addition");
		validateArithmeticOperands(other, this.unit, true);

        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double converted = unit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(converted), unit);
	}
	
	/**
	 * Adds this Quantity to another Quantity of the same unit type and returns 
	 * the result in the specified target unit.
	 * 
	 * <p> This method converts both quantities to their base unit, adds the values, 
	 * and then converts the sum to the specified target unit.
	 */
	
	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		unit.validateOperationSupport("addition");
		validateArithmeticOperands(other, targetUnit, true);
		
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(baseResult);
		
		return new Quantity<U>(round(converted), targetUnit);
	}
	
	/**
	 * Subtracts this Quantity from another Quantity of the same unit type and
	 * returns the result in the unit of this Quantity.
	 * 
	 */
	public Quantity<U> subtract(Quantity<U> other) {
		unit.validateOperationSupport("subtraction");
		validateArithmeticOperands(other, this.unit, true);
		
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = unit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round(converted), this.unit);
	}
	
	/**
	 * Subtracts this Quantity from another Quantity of the same unit type and
	 * returns the result in a specified target unit.
	 * 
	 */
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		unit.validateOperationSupport("subtraction");
		validateArithmeticOperands(other, targetUnit, true);
		double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(baseResult);
		return new Quantity<>(round(converted), targetUnit);
	}
	
	/**
	 * Divides this Quantity by another Quantity of the same unit type and
	 * returns the result as a double.
	 * 
	 */
	public double divide(Quantity<U> other) {
		unit.validateOperationSupport("division");
		validateArithmeticOperands(other, this.unit, true);
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
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

	    if (this == obj)
	        return true;

	    if (!(obj instanceof Quantity<?> that))
	        return false;

	    return compare(that);
	}
	
	@Override
	public int hashCode() {
	    return Double.hashCode(this.toBaseUnit());
	}
	
	private boolean compare(Quantity<?> that) {

	    if (!this.unit.getClass().equals(that.unit.getClass())) {
	        return false;
	    }

	    double thisBase = this.toBaseUnit();
	    double thatBase = that.toBaseUnit();

	    return Math.abs(thisBase - thatBase) < EPSILON;
	}
	
	private double round(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}

	public static void main(String[] args) {
		
		// Equal Method example
		Quantity<LengthUnit> lengthInCm = new Quantity<>(254.0, LengthUnit.CENTIMETERS);
		Quantity<LengthUnit> lengthInInches = new Quantity<>(100.0, LengthUnit.INCHES);
		System.out.println("254.0 centimeters = 100.0 inches -> " + lengthInCm.equals(lengthInInches));
				
		Quantity<WeightUnit> lengthInPound = new Quantity<>(1000.0, WeightUnit.POUND);
		Quantity<WeightUnit> lengthInKilogram = new Quantity<>(453.592, WeightUnit.KILOGRAM);
		System.out.println("1000.0 pound = 453.592 kilogram -> " + lengthInPound.equals(lengthInKilogram));
		
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
		
		// Checking VoulemUnit Methods
		
		Quantity<VolumeUnit> volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> volume3 = new Quantity<>(0.264172, VolumeUnit.GALLON);
		
		// Equal Method
		System.out.println("Is volume1 equal to volume2? " + volume1.equals(volume2));
		System.out.println("Is volume1 equal to volume3? " + volume1.equals(volume3));
		
		// Unit Conversion
		System.out.println("1 Litre = " + volume1.convertTo(VolumeUnit.MILLILITRE) + " Millilitre");
		System.out.println("0.264172 Gallon = " + volume3.convertTo(VolumeUnit.LITRE) + " Litre");
		System.out.println("1 Millilitre = " + volume2.convertTo(VolumeUnit.GALLON) + " Gallon");
		
		// Add Method
		System.out.println("1.0 Litre + 1000.0 Millilitre = " + volume1.add(volume2));
		
		// Add Method with target unit-specification
		System.out.println("1.0 Litre + 0.264172 Gallon = " + volume1.add(volume3, VolumeUnit.MILLILITRE));
		
		System.out.println("1000.0 Millilitre + 0.264172 Gallon = " + volume2.add(volume3, VolumeUnit.GALLON));
		
		// Exception case of addition method
		try {
			volume1.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			volume2.add(volume3, null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Quantity<VolumeUnit> volume = new Quantity<>(1.0, null);
	    	Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
	    	System.out.println(volume.equals(litre));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		// Subtraction with Implicit Target Unit
		Quantity<LengthUnit> feetSubtraction = new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(6.0, LengthUnit.INCHES));
		System.out.println("10.0 feet - 6.0 inches = " + feetSubtraction);
		
		Quantity<WeightUnit> kgSubtraction = new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5000.0, WeightUnit.GRAM));
		System.out.println("10.0 kg - 5000.0 grams = " + kgSubtraction);
		
		Quantity<VolumeUnit> litreSubtraction = new Quantity<>(5.0, VolumeUnit.LITRE).subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
		System.out.println("5.0 litre - 500.0 millilitre = " + litreSubtraction);
		
		// Subtraction with Explicit Target Unit
		Quantity<LengthUnit> feetMinus = new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
		System.out.println("10.0 feet - 6.0 inches = " + feetMinus);
		
		Quantity<WeightUnit> kgMinus = new Quantity<>(10.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM);
		System.out.println("10.0 kg - 5000.0 grams = " + kgMinus);
		
		Quantity<VolumeUnit> litreMinus = new Quantity<>(5.0, VolumeUnit.LITRE).subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
		System.out.println("5.0 litre - 2.0 litre = " + litreMinus);
		
		// Subtraction Resulting in Negative Values
		Quantity<LengthUnit> negFeet = new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<>(10.0, LengthUnit.FEET));
		System.out.println("5.0 feet - 10.0 feet = " + negFeet);
		
		Quantity<WeightUnit> negKG = new Quantity<>(2.0, WeightUnit.KILOGRAM).subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));
		System.out.println("2.0 kg - 5.0 kg = " + negKG);
		
		// Subtraction Resulting in Zero
		Quantity<LengthUnit> zeroLength = new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(120.0, LengthUnit.INCHES));
		System.out.println("10.0 feet - 120.0 inches = " + zeroLength);
		
		Quantity<VolumeUnit> zeroVolume = new Quantity<>(1.0, VolumeUnit.LITRE).subtract(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
		System.out.println("1.0 litre - 1000.0 millilitre = " + zeroVolume);
		
		// Division Operations
		double feetDivision = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		System.out.println("10.0 feet / 2.0 feet = " + feetDivision + " feet");
		
		double inchDivision = new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET));
		System.out.println("24.0 inches / 2.0 feet = " + inchDivision + " inch");
		
		double kgDivision = new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));
		System.out.println("10.0 kg / 5.0 kg = " + kgDivision + " kilogram");
		
		double litreDivision = new Quantity<>(5.0, VolumeUnit.LITRE).divide(new Quantity<>(10.0, VolumeUnit.LITRE));
		System.out.println("5.0 litre / 10.0 litre = " + litreDivision + " litre");
		
		// Division with Different Units (Same Category):
		double inchFeetDivision = new Quantity<>(12.0, LengthUnit.INCHES)
		        .divide(new Quantity<>(1.0, LengthUnit.FEET));
		System.out.println("12.0 inches / 1.0 feet = " + inchFeetDivision + " inch");

		double gramKgDivision = new Quantity<>(2000.0, WeightUnit.GRAM)
		        .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));
		System.out.println("2000.0 gram / 1.0 kilogram = " + gramKgDivision + " grams");

		double milliLitreLitreDivision = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
		        .divide(new Quantity<>(1.0, VolumeUnit.LITRE));
		System.out.println("1000.0 millilitre / 1.0 litre = " + milliLitreLitreDivision + " mililitre");
		
		// Error case
		try {
			new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET));
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}
		
		try {
//			new Quantity<>(10.0, LengthUnit.FEET).subtract(new Quantity<>(5.0, LengthUnit.KILOGRAM));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}