package com.apps.quantitymeasurement.core;

public class Quantity<U extends IMeasurable> {
	private double value;
	private U unit;

	private static final double EPSILON = 1e-5;

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
	 * <p>
	 * This method first converts the current value to the base unit using the
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
	 * <p>
	 * This method converts both quantities to their base unit, adds the values, and
	 * then converts the sum back to the unit of this Quantity.
	 */

	public Quantity<U> add(Quantity<U> other) {
		unit.validateOperationSupport("addition");
		validateArithmeticOperands(other, this.unit, true);

		double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = unit.convertFromBaseUnit(baseResult);

		return new Quantity<>(round(converted), unit);
	}

	/**
	 * Adds this Quantity to another Quantity of the same unit type and returns the
	 * result in the specified target unit.
	 * 
	 * <p>
	 * This method converts both quantities to their base unit, adds the values, and
	 * then converts the sum to the specified target unit.
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
	 * Divides this Quantity by another Quantity of the same unit type and returns
	 * the result as a double.
	 * 
	 */
	public double divide(Quantity<U> other) {
		unit.validateOperationSupport("division");
		validateArithmeticOperands(other, this.unit, true);
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	/**
	 * Compares this Quantity with another object for equality. Two Quantity objects
	 * are considered equal if they represent the same measurement value when
	 * converted to their respective base units.
	 *
	 * Logic to compare two Quantity objects: 1. Check if the other object is an
	 * instance of Quantity. 2. If not, return false. 3. If yes, convert both
	 * Quantity values to their base units using the convertToBaseUnit method of
	 * their respective units. 4. Compare the converted values for equality. 5.
	 * Return true if they are equal, false otherwise.
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
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}

}