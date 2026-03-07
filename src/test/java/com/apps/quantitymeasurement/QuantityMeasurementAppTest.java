package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
	
	// ================= WEIGHT EQUALITY TESTS =================

	@Test
	public void testKilogramEquality() {
	    Weight kg1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight kg2 = new Weight(1.0, WeightUnit.KILOGRAM);

	    assertTrue(kg1.equals(kg2));
	}

	@Test
	public void testKilogramInequality() {
	    Weight kg1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight kg2 = new Weight(2.0, WeightUnit.KILOGRAM);

	    assertFalse(kg1.equals(kg2));
	}

	@Test
	public void testKilogramGramComparison() {
	    Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight gram = new Weight(1000.0, WeightUnit.GRAM);

	    assertTrue(kg.equals(gram));
	}

	@Test
	public void testGramKilogramComparison() {
	    Weight gram = new Weight(1000.0, WeightUnit.GRAM);
	    Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);

	    assertTrue(gram.equals(kg));
	}

	@Test
	public void testMilligramGramEquality() {
	    Weight mg = new Weight(1000.0, WeightUnit.MILIGRAM);
	    Weight gram = new Weight(1.0, WeightUnit.GRAM);

	    assertTrue(mg.equals(gram));
	}

	@Test
	public void testTonneKilogramEquality() {
	    Weight tonne = new Weight(1.0, WeightUnit.TONNE);
	    Weight kg = new Weight(1000.0, WeightUnit.KILOGRAM);

	    assertTrue(tonne.equals(kg));
	}

	@Test
	public void testPoundGramComparison() {
	    Weight pound = new Weight(2.20462, WeightUnit.POUND);
	    Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);

	    assertTrue(pound.equals(kg));
	}

	@Test
	public void testWeightReferenceEquality() {
	    Weight kg = new Weight(5.0, WeightUnit.KILOGRAM);

	    assertTrue(kg.equals(kg));
	}

	@Test
	public void testWeightEqualsNull() {
	    Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);

	    assertFalse(kg.equals(null));
	}


	// ================= WEIGHT CONVERSION TESTS =================

	@Test
	public void convertKilogramToGram() {
	    Weight converted = QuantityMeasurementApp.demonstarteWeightConversion(
	            1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);

	    Weight expected = new Weight(1000.0, WeightUnit.GRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(converted, expected));
	}

	@Test
	public void convertPoundToKilogram() {
	    Weight converted = QuantityMeasurementApp.demonstarteWeightConversion(
	            2.20462, WeightUnit.POUND, WeightUnit.KILOGRAM);

	    Weight expected = new Weight(1.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(converted, expected));
	}

	@Test
	public void convertTonneToKilogram() {
	    Weight converted = QuantityMeasurementApp.demonstarteWeightConversion(
	            1.0, WeightUnit.TONNE, WeightUnit.KILOGRAM);

	    Weight expected = new Weight(1000.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(converted, expected));
	}

	@Test
	public void convertMilligramToGram() {
	    Weight converted = QuantityMeasurementApp.demonstarteWeightConversion(
	            1000.0, WeightUnit.MILIGRAM, WeightUnit.GRAM);

	    Weight expected = new Weight(1.0, WeightUnit.GRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(converted, expected));
	}


	// ================= WEIGHT ADDITION TESTS =================

	@Test
	public void addKilogramAndKilogram() {
	    Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(3.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void addKilogramAndGram() {
	    Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(2.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void addPoundAndKilogram() {
	    Weight pound = new Weight(2.20462, WeightUnit.POUND);
	    Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(pound, kg);

	    Weight expected = new Weight(4.41, WeightUnit.POUND);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void testWeightAdditionCommutativity() {

	    Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

	    Weight w1PlusW2 = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2, WeightUnit.GRAM);
	    Weight w2PlusW1 = QuantityMeasurementApp.demonstrateWeightAddition(w2, w1, WeightUnit.GRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(w1PlusW2, w2PlusW1));
	}

	@Test
	public void testWeightAdditionWithZero() {

	    Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(0.0, WeightUnit.GRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(5.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void testWeightAdditionWithNegativeValues() {

	    Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(-2000.0, WeightUnit.GRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(3.0, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void testWeightAdditionLargeValues() {

	    Weight w1 = new Weight(1e6, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1e6, WeightUnit.KILOGRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(2e6, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	@Test
	public void testWeightAdditionExplicitTargetUnit() {

	    Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2, WeightUnit.GRAM);

	    Weight expected = new Weight(2000.0, WeightUnit.GRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}
	
	// ================= ADDITIONAL WEIGHT EDGE TESTS =================

	@Test
	public void testWeightZeroEqualityAcrossUnits() {
	    Weight kg = new Weight(0.0, WeightUnit.KILOGRAM);
	    Weight gram = new Weight(0.0, WeightUnit.GRAM);

	    assertTrue(kg.equals(gram));
	}

	@Test
	public void testWeightNegativeEqualityAcrossUnits() {
	    Weight kg = new Weight(-1.0, WeightUnit.KILOGRAM);
	    Weight gram = new Weight(-1000.0, WeightUnit.GRAM);

	    assertTrue(kg.equals(gram));
	}

	@Test
	public void testLargeWeightEquality() {
	    Weight gram = new Weight(1000000.0, WeightUnit.GRAM);
	    Weight kg = new Weight(1000.0, WeightUnit.KILOGRAM);

	    assertTrue(gram.equals(kg));
	}

	@Test
	public void testSmallWeightEquality() {
	    Weight kg = new Weight(0.001, WeightUnit.KILOGRAM);
	    Weight gram = new Weight(1.0, WeightUnit.GRAM);

	    assertTrue(kg.equals(gram));
	}

	@Test
	public void testWeightRoundTripConversion() {

	    Weight original = new Weight(1.5, WeightUnit.KILOGRAM);

	    Weight converted = QuantityMeasurementApp
	            .demonstarteWeightConversion(original, WeightUnit.GRAM);

	    Weight roundTrip = QuantityMeasurementApp
	            .demonstarteWeightConversion(converted, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(original, roundTrip));
	}

	@Test
	public void testWeightAdditionTargetUnitNull() {

	    Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> QuantityMeasurementApp.demonstrateWeightAddition(w1, w2, null));

	    assertEquals("Target unit cannot be null.", exception.getMessage());
	}

	@Test
	public void testWeightAdditionSmallValues() {

	    Weight w1 = new Weight(0.01, WeightUnit.KILOGRAM);
	    Weight w2 = new Weight(0.02, WeightUnit.KILOGRAM);

	    Weight sum = QuantityMeasurementApp.demonstrateWeightAddition(w1, w2);

	    Weight expected = new Weight(0.03, WeightUnit.KILOGRAM);

	    assertTrue(QuantityMeasurementApp.demonstrateWeightEquality(sum, expected));
	}

	// ================= LENGTH EQUALITY TESTS =================

	@Test
	public void testFeetEquality() {
	    Length feet1 = new Length(1.0, LengthUnit.FEET);
	    Length feet2 = new Length(1.0, LengthUnit.FEET);

	    assertTrue(feet1.equals(feet2));
	}

	@Test
	public void testInchesEquality() {
	    Length inches1 = new Length(12.0, LengthUnit.INCHES);
	    Length inches2 = new Length(12.0, LengthUnit.INCHES);

	    assertTrue(inches1.equals(inches2));
	}

	@Test
	public void testFeetInchComparison() {
	    Length feet = new Length(1.0, LengthUnit.FEET);
	    Length inches = new Length(12.0, LengthUnit.INCHES);
	    assertTrue(feet.equals(inches));
	}

	@Test
	public void testFeetInequality() {
	    Length feet1 = new Length(1.0, LengthUnit.FEET);
	    Length feet2 = new Length(2.0, LengthUnit.FEET);

	    assertFalse(feet1.equals(feet2));
	}

	@Test
	public void testInchesInequality() {
	    Length inches1 = new Length(12.0, LengthUnit.INCHES);
	    Length inches2 = new Length(24.0, LengthUnit.INCHES);

	    assertFalse(inches1.equals(inches2));
	}

	@Test
	public void testCrossUnitInequality() {
	    Length feet = new Length(1.0, LengthUnit.FEET);
	    Length inches = new Length(14.0, LengthUnit.INCHES);
	    assertFalse(feet.equals(inches));
	}

	@Test
	public void testMultipleFeetComparison() {
	    Length feet1 = new Length(1.0, LengthUnit.FEET);
	    Length feet2 = new Length(1.0, LengthUnit.FEET);
	    Length feet3 = new Length(2.0, LengthUnit.FEET);

	    assertTrue(feet1.equals(feet2));
	    assertFalse(feet2.equals(feet3));
	}

	@Test
	public void yardEquals36Inches() {
	    Length yard = new Length(1.0, LengthUnit.YARDS);
	    Length inches = new Length(36.0, LengthUnit.INCHES);

	    assertTrue(yard.equals(inches));
	}

	@Test
	public void centimeterEquals39Point3701Inches() {
	    Length cm = new Length(100.0, LengthUnit.CENTIMETERS);
	    Length inches = new Length(39.3701, LengthUnit.INCHES);

	    assertTrue(cm.equals(inches));
	}

	@Test
	public void threeFeetEqualsOneYard() {
	    Length feet = new Length(3.0, LengthUnit.FEET);
	    Length yards = new Length(1.0, LengthUnit.YARDS);

	    assertTrue(feet.equals(yards));
	}

	@Test
	public void thirtyPoint48CmEqualsOneFoot() {
	    Length cm = new Length(30.48, LengthUnit.CENTIMETERS);
	    Length foot = new Length(1.0, LengthUnit.FEET);

	    assertTrue(cm.equals(foot));
	}

	@Test
	public void yardNotEqualsToInches() {
	    Length yards = new Length(2.0, LengthUnit.YARDS);
	    Length inches = new Length(2.0, LengthUnit.INCHES);

	    assertFalse(yards.equals(inches));
	}

	@Test
	public void referenceEqualitySameObject() {
	    Length yards = new Length(2.0, LengthUnit.YARDS);

	    assertTrue(yards.equals(yards));
	}

	@Test
	public void equalsReturnFalseForNull() {
	    Length yards = new Length(1.0, LengthUnit.YARDS);

	    assertFalse(yards.equals(null));
	}

	@Test
	public void reflexiveSymmetricAndTransitiveProperty() {

	    // Reflexive
	    Length yards = new Length(1.0, LengthUnit.YARDS);
	    assertTrue(yards.equals(yards));

	    // Symmetric
	    Length cm = new Length(91.44, LengthUnit.CENTIMETERS);
	    assertTrue(yards.equals(cm));
	    assertTrue(cm.equals(yards));

	    // Transitive
	    Length yards2 = new Length(1.0, LengthUnit.YARDS);
	    assertTrue(yards.equals(cm));
	    assertTrue(cm.equals(yards2));
	    assertTrue(yards.equals(yards2));
	}

	@Test
	public void differentValuesSameUnitNotEqual() {
	    Length yards1 = new Length(2.0, LengthUnit.YARDS);
	    Length yards2 = new Length(3.0, LengthUnit.YARDS);

	    Length cm1 = new Length(1.0, LengthUnit.CENTIMETERS);
	    Length cm2 = new Length(2.0, LengthUnit.CENTIMETERS);

	    assertFalse(yards1.equals(yards2));
	    assertFalse(cm1.equals(cm2));
	}

	@Test
	public void crossUnitEqualityDemonstrateMethod() {
	    Length yards = new Length(1.0, LengthUnit.YARDS);
	    Length cm = new Length(91.44, LengthUnit.CENTIMETERS);

	    assertTrue(yards.equals(cm));
	}
	
	// ================= LENGTH CONVERSION TESTS =================

	@Test
	public void convertFeetToInches() {
	    Length lengthInInches =
	        QuantityMeasurementApp.demonstrateLengthConversion(
	            3.0, LengthUnit.FEET, LengthUnit.INCHES);

	    Length expectedLength = new Length(36.0, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(
	        lengthInInches, expectedLength));
	}

	@Test
	public void convertYardsToInchesUsingOverloadedMethod() {
	    Length lengthInYards = new Length(2.0, LengthUnit.YARDS);

	    Length lengthInInches =
	        QuantityMeasurementApp.demonstrateLengthConversion(
	            lengthInYards, LengthUnit.INCHES);

	    Length expectedLength = new Length(72.0, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(
	        lengthInInches, expectedLength));
	}
	
	// ================= LENGTH ADDITION TESTS =================

	@Test
	public void addInchesAndInches() {
	    Length length1 = new Length(12.0, LengthUnit.INCHES);
	    Length length2 = new Length(12.0, LengthUnit.INCHES);

	    Length sumLength =
	        QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);

	    Length exceptedLength = new Length(24.0, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(
	        sumLength, exceptedLength));
	}

	@Test
	public void addFeetAndInches() {
	    Length length1 = new Length(1.0, LengthUnit.FEET);
	    Length length2 = new Length(12.0, LengthUnit.INCHES);

	    Length sumLength =
	        QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);

	    Length exceptedLength = new Length(2.0, LengthUnit.FEET);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(
	        sumLength, exceptedLength));
	}

	@Test
	public void testCommutativityAddition() {
	    Length l1 = new Length(1.0, LengthUnit.YARDS);
	    Length l2 = new Length(3.0, LengthUnit.FEET);

	    Length l1PlusL2 =
	        QuantityMeasurementApp.demonstarteLengthAddition(
	            l1, l2, LengthUnit.INCHES);

	    Length l2PlusL1 =
	        QuantityMeasurementApp.demonstarteLengthAddition(
	            l2, l1, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(
	        l1PlusL2, l2PlusL1));
	}
	
	// ================= LENGTH ADDITION EDGE CASE TESTS =================

	@Test
	public void testAdditionWithZero() {
	    Length length1 = new Length(5.0, LengthUnit.FEET);
	    Length length2 = new Length(0.0, LengthUnit.INCHES);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);
	    Length exceptedLength = new Length(5.0, LengthUnit.FEET);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionWithNegativeValues() {
	    Length length1 = new Length(5.0, LengthUnit.FEET);
	    Length length2 = new Length(-2.0, LengthUnit.FEET);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);
	    Length exceptedLength = new Length(3.0, LengthUnit.FEET);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionWithNullSecondOperand() {
	    Length length = new Length(5.0, LengthUnit.FEET);

	    IllegalArgumentException exception = assertThrows(
	        IllegalArgumentException.class,
	        () -> QuantityMeasurementApp.demonstarteLengthAddition(length, null)
	    );

	    assertEquals("Length cannot be null", exception.getMessage());
	}

	@Test
	public void testAdditionWithLargeValues() {
	    Length length1 = new Length(1e6, LengthUnit.FEET);
	    Length length2 = new Length(1e6, LengthUnit.FEET);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);
	    Length exceptedLength = new Length(2e6, LengthUnit.FEET);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionWithSmallValues() {
	    Length length1 = new Length(0.01, LengthUnit.YARDS);
	    Length length2 = new Length(0.02, LengthUnit.YARDS);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2);
	    Length exceptedLength = new Length(0.03, LengthUnit.YARDS);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
	
	// ================= LENGTH EXPLICIT TARGET UNIT ADDITION TESTS =================

	@Test
	public void testAdditionExplicitTargetUnitYards() {
	    Length length1 = new Length(1.0, LengthUnit.FEET);
	    Length length2 = new Length(12.0, LengthUnit.INCHES);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, LengthUnit.YARDS);
	    Length expectedSum = new Length(0.67, LengthUnit.YARDS);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedSum));
	}

	@Test
	public void testAdditionExplicitTargetUnitSameAsFirstOperand() {
	    Length length1 = new Length(2.0, LengthUnit.YARDS);
	    Length length2 = new Length(3.0, LengthUnit.FEET);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, LengthUnit.YARDS);
	    Length expectedSum = new Length(3.0, LengthUnit.YARDS);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedSum));
	}

	@Test
	public void testAdditionExplicitTargetUnitCommutativity() {
	    Length l1 = new Length(1.0, LengthUnit.FEET);
	    Length l2 = new Length(12.0, LengthUnit.INCHES);

	    Length l1PlusL2 = QuantityMeasurementApp.demonstarteLengthAddition(l1, l2, LengthUnit.YARDS);
	    Length l2PlusL1 = QuantityMeasurementApp.demonstarteLengthAddition(l2, l1, LengthUnit.YARDS);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(l1PlusL2, l2PlusL1));
	}

	@Test
	public void testAdditionExplicitTargetUnitNegativeValues() {
	    Length length1 = new Length(5.0, LengthUnit.FEET);
	    Length length2 = new Length(-2.0, LengthUnit.FEET);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, LengthUnit.INCHES);
	    Length exceptedLength = new Length(36.0, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionExplicitTargetUnitNullTargetUnit() {
	    Length length1 = new Length(1.0, LengthUnit.FEET);
	    Length length2 = new Length(12.0, LengthUnit.INCHES);

	    IllegalArgumentException exception = assertThrows(
	        IllegalArgumentException.class,
	        () -> QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, null)
	    );

	    assertEquals("Target unit cannot be null.", exception.getMessage());
	}

	@Test
	public void testAdditionExplicitTargetUnitLargeToSmallScale() {
	    Length length1 = new Length(1000.0, LengthUnit.FEET);
	    Length length2 = new Length(500.0, LengthUnit.FEET);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, LengthUnit.INCHES);
	    Length exceptedLength = new Length(18000.0, LengthUnit.INCHES);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}

	@Test
	public void testAdditionExplicitTargetUnitSmallToLargeScale() {
	    Length length1 = new Length(12.0, LengthUnit.INCHES);
	    Length length2 = new Length(12.0, LengthUnit.INCHES);

	    Length sumLength = QuantityMeasurementApp.demonstarteLengthAddition(length1, length2, LengthUnit.YARDS);
	    Length exceptedLength = new Length(0.67, LengthUnit.YARDS);

	    assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, exceptedLength));
	}
}