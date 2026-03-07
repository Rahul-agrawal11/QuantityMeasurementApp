package com.apps.quantitymeasurement;

public enum WeightUnit {
	// Conversion factor to the base unit(grams)
	MILIGRAM(0.001), GRAM(1.0), KILOGRAM(1000.0), POUND(453.592), TONNE(1_000_000.0);

	private final double conversionFactor;

	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}
	
	public double convertToBaseUnit(double value) {
		return Math.round((value * this.conversionFactor) * 100.0) / 100.0;
	}

	public double convertFromBaseUnit(double baseValue) {
		return Math.round((baseValue / this.conversionFactor) * 100.0) / 100.0;
	}
	
	public static void main(String[] args) {
		double kilograms = 5.0;
		double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kilograms);
		System.out.println(kilograms + " kilograms are equal to " + grams + " grams");
		
		double miligrams = WeightUnit.MILIGRAM.convertFromBaseUnit(grams);
		System.out.println(miligrams);
	}
}
