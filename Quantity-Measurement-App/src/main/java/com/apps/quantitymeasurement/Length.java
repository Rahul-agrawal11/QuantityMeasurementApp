package com.apps.quantitymeasurement;

public class Length {
	// Instance variables
	private double value;
	private LengthUnit unit;
	
	// Enum to represent different length units and their conversion factors
	// with the base unit being inches. This means all the conversion factors 
	// are defined in terms of inches.
	public enum LengthUnit {
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;

		private LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	// Constructor to initialize length value and unit
	public Length(double value, LengthUnit unit) {
		this.value = value;
		this.unit = unit;
	}
	
	// Convert the length value to the base unit (inches) and round off two decimal places
	private double convertToBaseUnit() {
		String result = String.format("%.2f", value * unit.getConversionFactor());
		return Double.valueOf(result);
	}
	
	private boolean compare(Length thatLength) {
		if (thatLength == null) return false;
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit()) == 0;
	}
	
	// Equals method is overridden to firstly check if the two objects are the same reference.
	// If not, it checks if the other object is null or of a different class.
	// Finally, calls the compare method to determine equality based on converted values.
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Length)) return false;
		Length that = (Length) obj;
		return compare(that);
	}
	
	@Override
	public int hashCode() {
		return Double.valueOf(convertToBaseUnit()).hashCode();
	}
	
	// Main method to standalone testing
	
	public static void main(String[] args) {
		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);		
		System.out.println("Are lengths equal? " +length1.equals(length2));  // Should print true
		
		Length length3 = new Length(1.0, LengthUnit.YARDS);
		Length length4 = new Length(36.0, LengthUnit.INCHES);		
		System.out.println("Are lengths equal? " +length3.equals(length4));  // Should print true
		
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);		
		System.out.println("Are lengths equal? " +length5.equals(length6));  // Should print true
	}
}
