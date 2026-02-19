package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

/**
 * QunatityMeasurementApp - UC3: Unified Qunatity Measurement App
 * 
 * This class addresses the DRY (Don't Repeat Yourself) principle violations 
 * present in the previous implementation where separate Feet and Inches classes
 * contained nearly identical code with:
 * -Identical constructor patterns
 * -Duplicate equals() method implementations
 * -Redundant validation logic
 * 
 * UC3 introduces a unified approach to handle multiple units of length measurement
 * by consolidating common functionality and eliminating code duplication.
 * This refactoring promotes code reusability, maintainability and scalability
 * for adding new measurement units without repeating boilerplate code.
 * 
 */
public class QuantityMeasurementApp {
	
	// Create a generic method to demonstrate Length equality check
	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}
	
	// Create a static method to demonstrate Feet equality check
	public static void demonstrateFeetEquality() {
		Length feet1 = new Length(1.0, LengthUnit.FEET);
		Length feet2 = new Length(1.0, LengthUnit.FEET);
		System.out.println("Feet equality: " + demonstrateLengthEquality(feet1, feet2));
	}
	
	// Create a static method to demonstrate Inches equality check
	public static void demonstrateInchesEquality() {
		Length inch1 = new Length(12.0, LengthUnit.INCHES);
		Length inch2 = new Length(12.0, LengthUnit.INCHES);
		System.out.println("Inches equality: " + demonstrateLengthEquality(inch1, inch2));
	}
	
	// Create a static method to demonstrate Feet and Inches equality comparison
	public static void demonstrateFeetInchesComaprison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inch = new Length(12.0, Length.LengthUnit.INCHES);
		System.out.println("Feet vs Inches equality: " + demonstrateLengthEquality(feet, inch));
	}
	
	// Main method to demonstrate Feet and Inches equality checks
	// and comparison checks between Feet and Inches
	public static void main(String[] args) {
		demonstrateFeetEquality();
		demonstrateInchesEquality();
		demonstrateFeetInchesComaprison();
	}
}
