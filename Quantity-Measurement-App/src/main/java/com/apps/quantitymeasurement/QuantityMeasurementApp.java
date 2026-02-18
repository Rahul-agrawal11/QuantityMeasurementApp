package com.apps.quantitymeasurement;

/**
 * QunatityMeasurementApp - UC2: Inches measurement equality
 * 
 * This class is responsible for checking the equality of two numerical values
 * measured in inches in the Quantity Measurement Application
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
		 * 4. Value Comparison: Use Double.compare() to comapre the double values from equality
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
	
	// Inner class to represent Inches measurement
	static class Inches {
		private final double value;
		public Inches(double value) {
			this.value = value;
		}
		
		/**
		 * Override equals() method to compare two inches objects based on their value
		 * 
		 * <p>Important Checks:</p>
		 * 1. Reference Check: If both references point to the same object, return true
		 * 2. Null Check: If the compared object is null, return false;
		 * 3. Type Check: If the compared object is not of type inches, return false;
		 * 4. Value Comparison: Use Double.compare() to comapre the double values from equality
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
			Inches other = (Inches) obj;
			return Double.compare(this.value, other.value) == 0;
		}
		
		// Define a static method to demonstrate Feet equality check
		public static void demonstrateFeetEquality() {
			Feet f1 = new Feet(1.0);
			Feet f2 = new Feet(1.0);
			
			System.out.println("Equal (" + f1.equals(f2) + ")");
		}
		
		// Define a static method to demonstrate Inches equality check
		public static void demonstrateInchesEquality() {
			Inches i1 = new Inches(1.0);
			Inches i2 = new Inches(1.0);
			
			System.out.println("Equal (" + i1.equals(i2) + ")");
		}
		
		public static void main(String[] args) {
			demonstrateInchesEquality();
			demonstrateFeetEquality();
		}
	}
}
