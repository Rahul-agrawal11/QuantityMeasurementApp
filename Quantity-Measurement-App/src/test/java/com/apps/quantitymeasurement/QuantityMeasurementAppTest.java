package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	@Test
	public void testFeetEquality() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(1.0, Length.LengthUnit.FEET);

		assertTrue(feet1.equals(feet2));
	}

	@Test
	public void testInchesEquality() {
		Length inches1 = new Length(12.0, Length.LengthUnit.INCHES);
		Length inches2 = new Length(12.0, Length.LengthUnit.INCHES);

		assertTrue(inches1.equals(inches2));
	}

	@Test
	public void testFeetInchComparison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertTrue(feet.equals(inches));
	}

	@Test
	public void testFeetInequality() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(2.0, Length.LengthUnit.FEET);

		assertFalse(feet1.equals(feet2));
	}

	@Test
	public void testInchesInequality() {
		Length inches1 = new Length(12.0, Length.LengthUnit.INCHES);
		Length inches2 = new Length(24.0, Length.LengthUnit.INCHES);

		assertFalse(inches1.equals(inches2));
	}

	@Test
	public void testCrossUnitInequality() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(14.0, Length.LengthUnit.INCHES);
		assertFalse(feet.equals(inches));
	}

	@Test
	public void testMultipleFeetComparison() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet3 = new Length(2.0, Length.LengthUnit.FEET);

		assertTrue(feet1.equals(feet2));
		assertFalse(feet2.equals(feet3));
	}

	@Test
	public void yardEquals36Inches() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);

		assertTrue(yard.equals(inches));
	}

	@Test
	public void centimeterEquals39Point3701Inches() {
		Length cm = new Length(100.0, Length.LengthUnit.CENTIMETERS);
		Length inches = new Length(39.3701, Length.LengthUnit.INCHES);

		assertTrue(cm.equals(inches));
	}

	@Test
	public void threeFeetEqualsOneYard() {
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		Length yards = new Length(1.0, Length.LengthUnit.YARDS);

		assertTrue(feet.equals(yards));
	}

	@Test
	public void thirtyPoint48CmEqualsOneFoot() {
		Length cm = new Length(30.48, Length.LengthUnit.CENTIMETERS);
		Length foot = new Length(1.0, Length.LengthUnit.FEET);

		assertTrue(cm.equals(foot));
	}

	@Test
	public void yardNotEqualsToInches() {
		Length yards = new Length(2.0, Length.LengthUnit.YARDS);
		Length inches = new Length(2.0, Length.LengthUnit.INCHES);

		assertFalse(yards.equals(inches));
	}

	@Test
	public void referenceEqualitySameObject() {
		Length yards = new Length(2.0, Length.LengthUnit.YARDS);

		assertTrue(yards.equals(yards));
	}

	@Test
	public void equalsReturnFalseForNull() {
		Length yards = new Length(1.0, Length.LengthUnit.YARDS);

		assertFalse(yards.equals(null));
	}

	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {

		// Reflexive
		Length yards = new Length(1.0, Length.LengthUnit.YARDS);
		assertTrue(yards.equals(yards));

		// Symmetric (Yards <-> CM)
		Length cm = new Length(91.44, Length.LengthUnit.CENTIMETERS);
		assertTrue(yards.equals(cm));
		assertTrue(cm.equals(yards));

		// Transitive
		Length yards2 = new Length(1.0, Length.LengthUnit.YARDS);
		assertTrue(yards.equals(cm));
		assertTrue(cm.equals(yards2));
		assertTrue(yards.equals(yards2));
	}

	@Test
	public void differentValuesSameUnitNotEqual() {
		Length yards1 = new Length(2.0, Length.LengthUnit.YARDS);
		Length yards2 = new Length(3.0, Length.LengthUnit.YARDS);

		Length cm1 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length cm2 = new Length(2.0, Length.LengthUnit.CENTIMETERS);

		assertFalse(yards1.equals(yards2));
		assertFalse(cm1.equals(cm2));
	}

	@Test
	public void crossUnitEqualityDemonstrateMethod() {
		Length yards = new Length(1.0, Length.LengthUnit.YARDS);
		Length cm = new Length(91.44, Length.LengthUnit.CENTIMETERS);

		assertTrue(yards.equals(cm));
	}
	
	@Test
	public void convertFeetToInches() {
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(3.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		Length expectedLength = new Length(36.0, Length.LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
	}
	
	@Test
	public void convertYardsToInchesUsingOverloadedMethod() {
		Length lengthInYards = new Length(2.0, Length.LengthUnit.YARDS);
		Length lengthInInches = QuantityMeasurementApp.demonstrateLengthConversion(lengthInYards, Length.LengthUnit.INCHES);
		Length expectedLength = new Length(72.0, Length.LengthUnit.INCHES);
		assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(lengthInInches, expectedLength));
	}
}
