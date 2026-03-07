package com.apps.quantitymeasurement;

import java.util.Objects;

public class Weight {
	// Instance variable to hold weight value and unit
	private double value;
	private WeightUnit unit;

	public Weight(double value, WeightUnit unit) {
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

	public WeightUnit getUnit() {
		return unit;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Weight that = (Weight) obj;
		return compare(that);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(convertToBaseUnit());
	}

	public boolean compare(Weight thatWeight) {
		if (thatWeight == null)
			return false;
		return Double.compare(this.convertToBaseUnit(), thatWeight.convertToBaseUnit()) == 0;
	}

	public Weight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		// Convert to base (inches)
		double baseValue = convertToBaseUnit();

		// Convert from inches to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Weight(convertedValue, targetUnit);
	}

	public Weight add(Weight thatWeight) {

		if (thatWeight == null) {
			throw new IllegalArgumentException("Weight cannot be null");
		}

		if (this.unit == thatWeight.unit) {
			return new Weight(this.value + thatWeight.value, this.unit);
		}

		double thisInBase = this.convertToBaseUnit();
		double thatInBase = thatWeight.convertToBaseUnit();

		double totalInBase = thisInBase + thatInBase;
		double convertedValue = convertFromBaseUnitToTargetUnit(totalInBase, this.unit);

		return new Weight(convertedValue, this.unit);
	}

	private Weight addAndConvert(Weight weight, WeightUnit targetUnit) {
		Weight wt = this.add(weight);
		double weightInGrams = wt.convertToBaseUnit();
		double weightInTargetUnit = convertFromBaseUnitToTargetUnit(weightInGrams, targetUnit);
		return new Weight(weightInTargetUnit, targetUnit);
	}

	private double convertToBaseUnit() {
		double base = value * unit.getConversionFactor();
		return Math.round(base * 100.0) / 100.0;
	}

	// Convert base unit to target unit
	private double convertFromBaseUnitToTargetUnit(double weightInGrams, WeightUnit targetUnit) {
		Weight grams = new Weight(weightInGrams, WeightUnit.GRAM);
		Weight result = grams.convertTo(targetUnit);
		return result.value;
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit);
	}

	public static void main(String[] args) {
		
		/**
		 * Equals method to check two weights are equal or not
		 */
		Weight weight1 = new Weight(1000.0, WeightUnit.GRAM);
		Weight weight2 = new Weight(1.0, WeightUnit.KILOGRAM);
		System.out.println("1000 grams are equal to 1 kilogram -> " + weight1.equals(weight2));
		
		Weight weight3 = new Weight(1.0, WeightUnit.POUND);
		Weight weight4 = new Weight(453592.0, WeightUnit.MILIGRAM);
		System.out.println("1 pound is equal to 453592 miligram -> " + weight3.equals(weight4));
		
		/**
		 * Conversion Method
		 */
		Weight weight5 = new Weight(2000.0, WeightUnit.GRAM);
		Weight convertToMiligrams = weight5.convertTo(WeightUnit.MILIGRAM);
		System.out.println("2000 gram equal to -> " + convertToMiligrams);
		
		Weight convertToKilogram = weight5.convertTo(WeightUnit.KILOGRAM);
		System.out.println("2000 gram equal to -> " + convertToKilogram);
		
		Weight convertToPound = weight5.convertTo(WeightUnit.POUND);
		System.out.println("2000 gram equal to -> " + convertToPound);
		
		Weight weight6 = new Weight(2000.0, WeightUnit.KILOGRAM);
		Weight convertToTonne = weight6.convertTo(WeightUnit.TONNE);
		System.out.println("2000 kilogram equal to -> " + convertToTonne);
		
		/**
		 * Add different unit of weight
		 */
		Weight weight7 = new Weight(1000.0, WeightUnit.GRAM);
		Weight weight8 = new Weight(1.0, WeightUnit.KILOGRAM);
		System.out.println("1000 gram + 1 kilogram = " + weight7.add(weight8));
		
		Weight weight9 = new Weight(1000.0, WeightUnit.POUND);
		Weight weight10 = new Weight(1.0, WeightUnit.TONNE);
		System.out.println("1000 pound + 1 tonne = " + weight9.add(weight10));
		
		/**
		 * Add different unit of weight with specific target unit
		 */
		Weight weight11 = new Weight(1000.0, WeightUnit.GRAM);
		Weight weight12 = new Weight(1.0, WeightUnit.KILOGRAM);
		System.out.println("1000 gram + 1 kilogram = " + weight11.addAndConvert(weight12, WeightUnit.MILIGRAM));
		
		Weight weight13 = new Weight(10.0, WeightUnit.POUND);
		Weight weight14 = new Weight(10.0, WeightUnit.TONNE);
		System.out.println("10 pound + 10 tonne = " + weight13.addAndConvert(weight14, WeightUnit.KILOGRAM));
		
		// Exception Test
		try {
			new Weight(Double.NaN, WeightUnit.GRAM);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
		
		try {
			new Weight(10.0, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
		
		try {
			Weight gram = new Weight(100.0, WeightUnit.GRAM);
			gram.convertTo(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
		
		try {
			Weight pound = new Weight(10.0, WeightUnit.POUND);
			pound.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
		
		try {
			Weight tonne = new Weight(10.0, WeightUnit.TONNE);
			tonne.addAndConvert(weight14, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
		
		try {
			Weight tonne = new Weight(10.0, WeightUnit.TONNE);
			tonne.addAndConvert(null, WeightUnit.POUND);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " +e.getMessage());
		}
	}
}
