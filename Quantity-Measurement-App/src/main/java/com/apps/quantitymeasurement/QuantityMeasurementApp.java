package com.apps.quantitymeasurement;

/**
 * QunatityMeasurementApp - UC1: Feet measurement equality
 * 
 * This class is responsible for checking the equality of two numerical values
 * measured in feet in the Quantity Measurement Application
 */

public class QuantityMeasurementApp {
	
	// Inner class to represent Feet measurement
	static class Feet {
		private final double value;
		public Feet(double value) {
			this.value = value;
		}
		
		/**
		 * Override equals() method to compare two Feet objects based on their value
		 * 
		 * <p>Important Checks:</p>
		 * 1. Reference Check: If both references point to the same object, return true
		 * 2. Null Check: If the compared object is null, return false;
		 * 3. Type Check: If the compared object is not of type Feet, return false;
		 * 4. Value Comparison: 
		 */
		
		@Override
		public boolean equals(Object obj) {
			
			// Reference Check
			if (this == obj) {
				return true;
			}
			
			// Null Check
			if (obj == null) {
				return false;
			}
			
			// Type Check
			if(getClass() != obj.getClass()) {
				return false;
			}
			
			// Cast and compare value
			Feet other = (Feet) obj;
			return Double.compare(this.value, other.value) == 0;
		}
		
		public static void main(String[] args) {
			Feet f1 = new Feet(1.0);
			Feet f2 = new Feet(1.0);
			
			System.out.println("Equal (" + f1.equals(f2) + ")");
			
		}
		
	}
	
	// 
}
