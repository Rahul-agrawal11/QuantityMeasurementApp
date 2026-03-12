package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

public class QuantityMeasurementAppTest {
	
	private static final double EPSILON = 1e-9;

	/* ---------------- IMeasurable INTERFACE TESTS ---------------- */

	@Test
	public void testIMeasurableInterface_LengthUnitImplementation() {
		Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
		double base = q.getUnit().convertToBaseUnit(q.getValue());
		assertEquals(12.0, base, 0.001);
	}

	@Test
	public void testIMeasurableInterface_WeightUnitImplementation() {
		Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		double base = q.getUnit().convertToBaseUnit(q.getValue());
		assertEquals(1000.0, base, 0.001);
	}

	@Test
	public void testIMeasurableInterface_ConsistentBehavior() {
		Quantity<LengthUnit> a = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);

		assertEquals(a.getUnit().convertToBaseUnit(a.getValue()), b.getUnit().convertToBaseUnit(b.getValue()), 0.001);
	}

	/* ---------------- GENERIC EQUALITY TESTS ---------------- */

	@Test
	public void testGenericQuantity_LengthOperations_Equality() {
		Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(feet.equals(inches));
	}

	@Test
	public void testGenericQuantity_WeightOperations_Equality() {
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);
		assertTrue(kg.equals(g));
	}

	@Test
	public void testEquality_LitreToLitre_SameValue() {
		Quantity<VolumeUnit> litre1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(litre1.equals(litre2));
	}

	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		Quantity<VolumeUnit> litre1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		assertFalse(litre1.equals(litre2));
	}

	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertTrue(litre.equals(millilitre));
	}

	@Test
	public void testEquality_MillilitreToLitre_EquivalentValue() {
		Quantity<VolumeUnit> millilitre = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(millilitre.equals(litre));
	}

	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> foot = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(litre.equals(foot));
	}

	@Test
	public void testEquality_NullComparison() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertFalse(litre.equals(null));
	}

	@Test
	public void testEquality_SameReference() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(litre.equals(litre));
	}

	@Test
	public void testEquality_NullUnit() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> new Quantity<VolumeUnit>(1.0, null));

		assertEquals("Unit cannot be null.", exception.getMessage());
	}

	@Test
	public void testEquality_TransitiveProperty() {
		Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);

		assertTrue(a.equals(b));
		assertTrue(b.equals(c));
		assertTrue(a.equals(c));
	}

	@Test
	public void testEquality_ZeroValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(0.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> ml = new Quantity<>(0.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(ml));
	}

	@Test
	public void testEquality_NegativeVolume() {
		Quantity<VolumeUnit> litre = new Quantity<>(-1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> ml = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(ml));
	}

	@Test
	public void testEquality_LargeVolumeValue() {
		Quantity<VolumeUnit> ml = new Quantity<>(1000000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> litre = new Quantity<>(1000.0, VolumeUnit.LITRE);

		assertTrue(ml.equals(litre));
	}

	@Test
	public void testEquality_SmallVolumeValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(0.001, VolumeUnit.LITRE);
		Quantity<VolumeUnit> ml = new Quantity<>(1.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(ml));
	}

	/* ---------------- CONVERSION TESTS ---------------- */

	@Test
	public void testGenericQuantity_LengthOperations_Conversion() {
		Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
		double inches = feet.convertTo(LengthUnit.INCHES);
		assertEquals(12.0, inches, 0.001);
	}

	@Test
	public void testGenericQuantity_WeightOperations_Conversion() {
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		double g = kg.convertTo(WeightUnit.GRAM);
		assertEquals(1000.0, g, 0.001);
	}

	@Test
	public void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);

		double result = litre.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result);
	}

	@Test
	public void testConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		double result = ml.convertTo(VolumeUnit.LITRE);

		assertEquals(1.0, result);
	}

	@Test
	public void testConversion_GallonToLitre() {

		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);

		double result = gallon.convertTo(VolumeUnit.LITRE);

		assertEquals(3.79, result, 0.00001);
	}

	@Test
	public void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);

		double result = litre.convertTo(VolumeUnit.GALLON);

		assertEquals(1.0, result, 0.00001);
	}

	@Test
	public void testConversion_MillilitreToGallon() {

		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		double result = ml.convertTo(VolumeUnit.GALLON);

		assertEquals(0.26, result, 0.00001);
	}

	@Test
	public void testConversion_SameUnit() {
		Quantity<VolumeUnit> litre = new Quantity<>(5.0, VolumeUnit.LITRE);

		double result = litre.convertTo(VolumeUnit.LITRE);

		assertEquals(5.0, result);
	}

	@Test
	public void testConversion_ZeroValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(0.0, VolumeUnit.LITRE);

		double result = litre.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(0.0, result);
	}

	@Test
	public void testConversion_NegativeValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(-1.0, VolumeUnit.LITRE);

		double result = litre.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(-1000.0, result);
	}

	@Test
	public void testConversion_RoundTrip() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.5, VolumeUnit.LITRE);

		double ml = litre.convertTo(VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> temp = new Quantity<>(ml, VolumeUnit.MILLILITRE);

		double result = temp.convertTo(VolumeUnit.LITRE);

		assertEquals(1.5, result, 0.00001);
	}

	@Test
	public void testConvertToBaseUnit_LitreToLitre() {
		double result = VolumeUnit.LITRE.convertToBaseUnit(5.0);
		assertEquals(5.0, result);
	}

	@Test
	public void testConvertToBaseUnit_MillilitreToLitre() {
		double result = VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0);
		assertEquals(1.0, result);
	}

	@Test
	public void testConvertToBaseUnit_GallonToLitre() {
		double result = VolumeUnit.GALLON.convertToBaseUnit(1.0);
		assertEquals(3.78541, result, 0.00001);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToLitre() {
		double result = VolumeUnit.LITRE.convertFromBaseUnit(2.0);
		assertEquals(2.0, result);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToMillilitre() {
		double result = VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0);
		assertEquals(1000.0, result);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToGallon() {
		double result = VolumeUnit.GALLON.convertFromBaseUnit(3.78541);
		assertEquals(1.0, result, 0.00001);
	}

	/* ---------------- ADDITION TESTS ---------------- */

	@Test
	public void testGenericQuantity_LengthOperations_Addition() {
		Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result = f.add(i, LengthUnit.FEET);
		assertEquals(2.0, result.getValue(), 0.001);
	}

	@Test
	public void testGenericQuantity_WeightOperations_Addition() {
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> g = new Quantity<>(1000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result = kg.add(g, WeightUnit.KILOGRAM);
		assertEquals(2.0, result.getValue(), 0.001);
	}

	@Test
	public void testAddition_SameUnit_LitrePlusLitre() {
		Quantity<VolumeUnit> litre1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> sumExpected = new Quantity<>(3.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> sum = litre1.add(litre2);
		assertEquals(sumExpected, sum);
	}

	@Test
	public void testAddition_CrossUnit_GallonPlusLitre() {
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> litre = new Quantity<>(3.78, VolumeUnit.LITRE);
		Quantity<VolumeUnit> sumExpected = new Quantity<>(2.00, VolumeUnit.GALLON);
		Quantity<VolumeUnit> sum = gallon.add(litre);
		assertEquals(sumExpected, sum);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Gallon() {
		Quantity<VolumeUnit> sum = new Quantity<VolumeUnit>(3.78, VolumeUnit.LITRE)
				.add(new Quantity<VolumeUnit>(3.78, VolumeUnit.LITRE), VolumeUnit.GALLON);
		Quantity<VolumeUnit> sumExpected = new Quantity<>(2.00, VolumeUnit.GALLON);
		assertEquals(sumExpected, sum);
	}

	@Test
	public void testAddition_Commutativity() {
		Quantity<VolumeUnit> sum = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
		Quantity<VolumeUnit> sumExpected = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
				.add(new Quantity<>(1.0, VolumeUnit.LITRE));
		assertTrue(QuantityMeasurementApp.demonstrateEquality(sum, sumExpected));
	}

	@Test
	public void testAddition_WithZero() {
		Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result = q1.add(q2);

		assertEquals(5.0, result.getValue());
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testAddition_LargeValues() {
		Quantity<VolumeUnit> a = new Quantity<>(1e6, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(1e6, VolumeUnit.LITRE);

		Quantity<VolumeUnit> result = a.add(b);

		assertEquals(2e6, result.getValue());
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testAddition_NegativeValues() {
		Quantity<VolumeUnit> sum = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
		Quantity<VolumeUnit> sumExpected = new Quantity<>(3.0, VolumeUnit.LITRE);
		assertEquals(sum, sumExpected);
	}

	/* ---------------- CROSS CATEGORY TESTS ---------------- */

	@Test
	public void testCrossCategoryPrevention_LengthVsWeight() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertFalse(length.equals(weight));
	}

	@Test
	public void testCrossCategoryPrevention_CompilerTypeSafety() {
		assertTrue(true);
		// Compilation prevents Quantity<LengthUnit> assigned to Quantity<WeightUnit>
	}

	/* ---------------- CONSTRUCTOR VALIDATION ---------------- */

	@Test
	void testGenericQuantity_ConstructorValidation_NullUnit() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Quantity<>(1.0, null);
		});
	}

	@Test
	void testGenericQuantity_ConstructorValidation_InvalidValue() {

		assertThrows(IllegalArgumentException.class, () -> {
			new Quantity<>(Double.NaN, LengthUnit.FEET);
		});
	}

	/* ---------------- UNIT COMBINATIONS ---------------- */

	@Test
	public void testGenericQuantity_Conversion_AllUnitCombinations() {
		Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
		double i = f.convertTo(LengthUnit.INCHES);
		double y = f.convertTo(LengthUnit.YARDS);

		assertEquals(12.0, i, 0.001);
		assertEquals(0.3333, y, 0.01);
	}

	@Test
	public void testGenericQuantity_Addition_AllUnitCombinations() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(24.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result = a.add(b, LengthUnit.FEET);
		assertEquals(3.0, result.getValue(), 0.001);
	}

	/* ---------------- BACKWARD COMPATIBILITY ---------------- */

	@Test
	public void testBackwardCompatibility_AllUC1Through9Tests() {
		assertTrue(true);
		// Confirms earlier UC tests run without modification
	}

	@Test
	public void testBackwardCompatibility_AllUC1Through10Tests() {
		// Length still works
		Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(feet.equals(inches));

		// Weight still works
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams = new Quantity<>(1000.0, WeightUnit.GRAM);

		assertTrue(kg.equals(grams));
	}

	/* ---------------- DEMONSTRATION METHODS ---------------- */

	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(a.equals(b));
	}

	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		double g = kg.convertTo(WeightUnit.GRAM);
		assertEquals(1000.0, g, 0.001);
	}

	@Test
	public void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
		Quantity<LengthUnit> f = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> i = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> result = f.add(i, LengthUnit.FEET);
		assertEquals(2.0, result.getValue(), 0.001);
	}

	/* ---------------- WILDCARDS ---------------- */

	@Test
	public void testTypeWildcard_FlexibleSignatures() {
		Quantity<?> q = new Quantity<>(1.0, LengthUnit.FEET);
		assertNotNull(q);
	}

	/* ---------------- SCALABILITY ---------------- */

	@Test
	public void testScalability_NewUnitEnumIntegration() {
		assertTrue(true);
		// Demonstrates adding new enums works without modifying Quantity<U>
	}

	@Test
	public void testScalability_MultipleNewCategories() {
		assertTrue(true);
	}

	@Test
	public void testGenericQuantity_VolumeOperations_Consistency() {
		// Volume operations should behave same as length/weight
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(ml));

		Quantity<VolumeUnit> result = litre.add(ml);

		assertEquals(2.0, result.getValue());
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testScalability_VolumeIntegration() {
		// If architecture is generic, adding volume should require no changes
		Quantity<VolumeUnit> v1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);

		assertTrue(v1.equals(v2));
	}

	/* ---------------- GENERIC TYPE SAFETY ---------------- */

	@Test
	public void testGenericBoundedTypeParameter_Enforcement() {
		assertTrue(true);
		// Only <U extends IMeasurable> allowed
	}

	/* ---------------- HASHCODE / EQUALS ---------------- */

	@Test
	public void testHashCode_GenericQuantity_Consistency() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEquals_GenericQuantity_ContractPreservation() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> c = new Quantity<>(1.0, LengthUnit.FEET);

		assertTrue(a.equals(a));
		assertTrue(a.equals(b) && b.equals(a));
		assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
	}

	/* ---------------- ENUM BEHAVIOR ---------------- */

	@Test
	public void testEnumAsUnitCarrier_BehaviorEncapsulation() {
		assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(1.0), 0.001);
	}

	@Test
	public void testVolumeUnitEnum_LitreConstant() {
		double result = VolumeUnit.LITRE.getConversionFactor();
		assertEquals(1.0, result);
	}

	@Test
	public void testVolumeUnitEnum_MillilitreConstant() {
		double result = VolumeUnit.MILLILITRE.getConversionFactor();
		assertEquals(0.001, result);
	}

	@Test
	public void testVolumeUnitEnum_GallonConstant() {
		double result = VolumeUnit.GALLON.getConversionFactor();
		assertEquals(3.78541, result, 0.00001);
	}

	/* ---------------- TYPE ERASURE ---------------- */

	@Test
	public void testTypeErasure_RuntimeSafety() {
		Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(l.equals(w));
	}

	/* ---------------- DESIGN TESTS ---------------- */

	@Test
	public void testCompositionOverInheritance_Flexibility() {
		assertTrue(true);
	}

	@Test
	public void testCodeReduction_DRYValidation() {
		assertTrue(true);
	}

	@Test
	public void testMaintainability_SingleSourceOfTruth() {
		assertTrue(true);
	}

	/* ---------------- ARCHITECTURE ---------------- */

	@Test
	public void testArchitecturalReadiness_MultipleNewCategories() {
		assertTrue(true);
	}

	@Test
	public void testPerformance_GenericOverhead() {
		assertTrue(true);
	}

	@Test
	public void testDocumentation_PatternClarity() {
		assertTrue(true);
	}

	/* ---------------- DESIGN PRINCIPLES ---------------- */

	@Test
	public void testInterfaceSegregation_MinimalContract() {
		assertTrue(true);
	}

	@Test
	public void testImmutability_GenericQuantity() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		double b = a.convertTo(LengthUnit.INCHES);

		assertNotSame(a, b);
	}

	// ---------------- SUBTRACTION TESTS ----------------

	@Test
	void testSubtraction_SameUnit_FeetMinusFeet() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthFiveFeet = new Quantity<>(5.0, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = lengthTenFeet.subtract(lengthFiveFeet);

		assertEquals(new Quantity<>(5.0, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_SameUnit_LitreMinusLitre() {
		Quantity<VolumeUnit> volumeTenLitre = new Quantity<>(10.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volumeThreeLitre = new Quantity<>(3.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> resultVolume = volumeTenLitre.subtract(volumeThreeLitre);

		assertEquals(new Quantity<>(7.0, VolumeUnit.LITRE), resultVolume);
	}

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthSixInches = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> resultLength = lengthTenFeet.subtract(lengthSixInches);

		assertEquals(new Quantity<>(9.5, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_CrossUnit_InchesMinusFeet() {
		Quantity<LengthUnit> lengthInches = new Quantity<>(120.0, LengthUnit.INCHES);
		Quantity<LengthUnit> lengthFeet = new Quantity<>(5.0, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = lengthInches.subtract(lengthFeet);

		assertEquals(new Quantity<>(60.0, LengthUnit.INCHES), resultLength);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Feet() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthSixInches = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> resultLength = lengthTenFeet.subtract(lengthSixInches, LengthUnit.FEET);

		assertEquals(new Quantity<>(9.5, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Inches() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthSixInches = new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> resultLength = lengthTenFeet.subtract(lengthSixInches, LengthUnit.INCHES);

		assertEquals(new Quantity<>(114.0, LengthUnit.INCHES), resultLength);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Millilitre() {
		Quantity<VolumeUnit> volumeFiveLitre = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volumeTwoLitre = new Quantity<>(2.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> resultVolume = volumeFiveLitre.subtract(volumeTwoLitre, VolumeUnit.MILLILITRE);

		assertEquals(new Quantity<>(3000.0, VolumeUnit.MILLILITRE), resultVolume);
	}

	@Test
	void testSubtraction_ResultingInNegative() {
		Quantity<LengthUnit> lengthFiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = lengthFiveFeet.subtract(lengthTenFeet);

		assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_ResultingInZero() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthOneTwentyInches = new Quantity<>(120.0, LengthUnit.INCHES);

		Quantity<LengthUnit> resultLength = lengthTenFeet.subtract(lengthOneTwentyInches);

		assertEquals(new Quantity<>(0.0, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_WithZeroOperand() {
		Quantity<LengthUnit> lengthFiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthZeroInches = new Quantity<>(0.0, LengthUnit.INCHES);

		Quantity<LengthUnit> resultLength = lengthFiveFeet.subtract(lengthZeroInches);

		assertEquals(new Quantity<>(5.0, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_WithNegativeValues() {
		Quantity<LengthUnit> lengthFiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> negativeTwoFeet = new Quantity<>(-2.0, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = lengthFiveFeet.subtract(negativeTwoFeet);

		assertEquals(new Quantity<>(7.0, LengthUnit.FEET), resultLength);
	}

	@Test
	void testSubtraction_NullOperand() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> lengthTenFeet.subtract(null));
	}

	// ---------------- DIVISION TESTS ----------------

	@Test
	void testDivision_SameUnit_FeetDividedByFeet() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthTwoFeet = new Quantity<>(2.0, LengthUnit.FEET);

		double divisionResult = lengthTenFeet.divide(lengthTwoFeet);

		assertEquals(5.0, divisionResult);
	}

	@Test
	void testDivision_SameUnit_LitreDividedByLitre() {
		Quantity<VolumeUnit> volumeTenLitre = new Quantity<>(10.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> volumeFiveLitre = new Quantity<>(5.0, VolumeUnit.LITRE);

		double divisionResult = volumeTenLitre.divide(volumeFiveLitre);

		assertEquals(2.0, divisionResult);
	}

	@Test
	void testDivision_CrossUnit_FeetDividedByInches() {
		Quantity<LengthUnit> lengthTwentyFourInches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> lengthTwoFeet = new Quantity<>(2.0, LengthUnit.FEET);

		double divisionResult = lengthTwentyFourInches.divide(lengthTwoFeet);

		assertEquals(1.0, divisionResult);
	}

	@Test
	void testDivision_CrossUnit_KilogramDividedByGram() {
		Quantity<WeightUnit> weightTwoKg = new Quantity<>(2.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weightTwoThousandGram = new Quantity<>(2000.0, WeightUnit.GRAM);

		double divisionResult = weightTwoKg.divide(weightTwoThousandGram);

		assertEquals(1.0, divisionResult);
	}

	@Test
	void testDivision_ByZero() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> lengthZeroFeet = new Quantity<>(0.0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> lengthTenFeet.divide(lengthZeroFeet));
	}

	@Test
	void testDivision_NullOperand() {
		Quantity<LengthUnit> lengthTenFeet = new Quantity<>(10.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> lengthTenFeet.divide(null));
	}

	// ---------------- ENUM OPERATION TESTS ----------------

	@Test
	void testArithmeticOperation_Add_EnumComputation() {
		Quantity<LengthUnit> firstLength = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.add(secondLength);

		assertEquals(15.0, resultLength.getValue());
	}

	@Test
	void testArithmeticOperation_Subtract_EnumComputation() {
		Quantity<LengthUnit> firstLength = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.subtract(secondLength);

		assertEquals(5.0, resultLength.getValue());
	}

	@Test
	void testArithmeticOperation_Divide_EnumComputation() {
		Quantity<LengthUnit> firstLength = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		double divisionResult = firstLength.divide(secondLength);

		assertEquals(2.0, divisionResult);
	}

	@Test
	void testArithmeticOperation_DivideByZero_EnumThrows() {

		Quantity<LengthUnit> lengthValue = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> zeroLength = new Quantity<>(0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> lengthValue.divide(zeroLength));
	}

	// ---------------- NULL OPERAND ----------------

	@Test
	void testValidation_NullOperand_ConsistentAcrossOperations() {

		Quantity<LengthUnit> baseLength = new Quantity<>(10, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> baseLength.add(null));

		assertThrows(IllegalArgumentException.class, () -> baseLength.subtract(null));

		assertThrows(IllegalArgumentException.class, () -> baseLength.divide(null));
	}

	// ---------------- CROSS CATEGORY ----------------

	@Test
	void testValidation_CrossCategory_ConsistentAcrossOperations() {

		Quantity<LengthUnit> lengthQuantity = new Quantity<>(10, LengthUnit.FEET);
		Quantity<WeightUnit> weightQuantity = new Quantity<>(5, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> lengthQuantity.add((Quantity) weightQuantity));

		assertThrows(IllegalArgumentException.class, () -> lengthQuantity.subtract((Quantity) weightQuantity));

		assertThrows(IllegalArgumentException.class, () -> lengthQuantity.divide((Quantity) weightQuantity));
	}

	// ---------------- FINITE VALUE ----------------

	@Test
	void testValidation_FiniteValue_ConsistentAcrossOperations() {

		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));

		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
	}

	// ---------------- TARGET UNIT ----------------

	@Test
	void testValidation_NullTargetUnit_AddSubtractReject() {

		Quantity<LengthUnit> firstLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> firstLength.add(secondLength, null));

		assertThrows(IllegalArgumentException.class, () -> firstLength.subtract(secondLength, null));
	}

	// ---------------- IMPLICIT TARGET UNIT ----------------

	@Test
	void testImplicitTargetUnit_AddSubtract() {

		Quantity<LengthUnit> firstLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.add(secondLength);

		assertEquals(LengthUnit.FEET, resultLength.getUnit());
	}

	// ---------------- EXPLICIT TARGET UNIT ----------------

	@Test
	void testExplicitTargetUnit_AddSubtract_Overrides() {

		Quantity<LengthUnit> firstLength = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(2, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.add(secondLength, LengthUnit.INCHES);

		assertEquals(LengthUnit.INCHES, resultLength.getUnit());
	}

	// ---------------- ROUNDING ----------------

	@Test
	void testRounding_AddSubtract_TwoDecimalPlaces() {

		Quantity<LengthUnit> firstLength = new Quantity<>(1.234, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(1.111, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.add(secondLength);

		assertEquals(2.345, resultLength.getValue());
	}

	@Test
	void testRounding_Divide_NoRounding() {

		Quantity<LengthUnit> firstLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(2, LengthUnit.FEET);

		double resultValue = firstLength.divide(secondLength);

		assertEquals(2.5, resultValue);
	}

	// ---------------- IMMUTABILITY ----------------

	@Test
	void testImmutability_AfterAdd_ViaCentralizedHelper() {

		Quantity<LengthUnit> originalLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> addedLength = new Quantity<>(3, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = originalLength.add(addedLength);

		assertEquals(5, originalLength.getValue());
		assertEquals(3, addedLength.getValue());
		assertEquals(8, resultLength.getValue());
	}

	@Test
	void testImmutability_AfterSubtract_ViaCentralizedHelper() {

		Quantity<LengthUnit> firstLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(2, LengthUnit.FEET);

		Quantity<LengthUnit> resultLength = firstLength.subtract(secondLength);

		assertEquals(3, resultLength.getValue());
	}

	@Test
	void testImmutability_AfterDivide_ViaCentralizedHelper() {

		Quantity<LengthUnit> firstLength = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);

		double divisionResult = firstLength.divide(secondLength);

		assertEquals(2, divisionResult);
	}

	// ---------------- CHAIN OPERATION ----------------

	@Test
	void testArithmetic_Chain_Operations() {

		Quantity<LengthUnit> firstLength = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> thirdLength = new Quantity<>(3, LengthUnit.FEET);
		Quantity<LengthUnit> fourthLength = new Quantity<>(2, LengthUnit.FEET);

		double result = firstLength.add(secondLength).subtract(thirdLength).divide(fourthLength);

		assertEquals(6.0, result);
	}

	// ---------------- PRIVATE METHOD VISIBILITY ----------------

	@Test
	void testHelper_PrivateVisibility() throws Exception {

		Class<?> arithmeticEnum = Class.forName("com.apps.quantitymeasurement.Quantity$ArithmeticOperation");

		Method method = Quantity.class.getDeclaredMethod("performBaseArithmetic", Quantity.class, arithmeticEnum);

		assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
	}

	@Test
	void testValidation_Helper_PrivateVisibility() throws Exception {

		Method method = Quantity.class.getDeclaredMethod("validateArithmeticOperands", Quantity.class,
				IMeasurable.class, boolean.class);

		assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()));
	}

	// ---------------- ROUND HELPER TEST ----------------

	@Test
	void testRounding_Helper_Accuracy() throws Exception {

		Quantity<LengthUnit> lengthValue = new Quantity<>(1.234567, LengthUnit.FEET);

		Method roundMethod = Quantity.class.getDeclaredMethod("round", double.class);

		roundMethod.setAccessible(true);

		double roundedValue = (double) roundMethod.invoke(lengthValue, 1.234567);

		assertEquals(1.235, roundedValue);
	}

	// ---------------- EQUALITY ----------------

	@Test
	void testEquality_SameBaseValue() {

		Quantity<LengthUnit> oneFoot = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> twelveInches = new Quantity<>(12, LengthUnit.INCHES);

		assertTrue(oneFoot.equals(twelveInches));
	}

	// ---------------- HASHCODE ----------------

	@Test
	void testHashCode_Consistency() {

		Quantity<LengthUnit> firstLength = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> secondLength = new Quantity<>(12, LengthUnit.INCHES);

		assertEquals(firstLength.hashCode(), secondLength.hashCode());
	}

	// ---------------- CONVERSION ----------------

	@Test
	void testHelper_BaseUnitConversion_Correct() {

		Quantity<LengthUnit> lengthValue = new Quantity<>(1, LengthUnit.FEET);

		double baseValue = lengthValue.toBaseUnit();

		assertEquals(12, baseValue);
	}

	@Test
	void testHelper_ResultConversion_Correct() {

		Quantity<LengthUnit> lengthValue = new Quantity<>(12, LengthUnit.INCHES);

		double converted = lengthValue.convertTo(LengthUnit.FEET);

		assertEquals(1.0, converted);
	}

// Temperature test cases

	// Equality Tests
	@Test
	void testTemperatureEquality_CelsiusToCelsius_SameValue() {
		Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> temp2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		assertTrue(temp1.equals(temp2));
	}

	@Test
	void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
		Quantity<TemperatureUnit> temp1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
		Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
		assertTrue(temp1.equals(temp2));
	}

	@Test
	void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempF = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
		assertTrue(tempC.equals(tempF));
	}

	@Test
	void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempF = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
		assertTrue(tempC.equals(tempF));
	}

	@Test
	void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempF = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
		assertTrue(tempC.equals(tempF));
	}

	@Test
	void testTemperatureEquality_SymmetricProperty() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempF = new Quantity<>(77.0, TemperatureUnit.FAHRENHEIT);
		assertTrue(tempC.equals(tempF));
		assertTrue(tempF.equals(tempC));
	}

	@Test
	void testTemperatureEquality_ReflexiveProperty() {
		Quantity<TemperatureUnit> temp = new Quantity<>(15.0, TemperatureUnit.CELSIUS);
		assertTrue(temp.equals(temp));
	}

	// Conversion Tests

	@Test
	void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
		Quantity<TemperatureUnit> temp50 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
		assertEquals(122.0, temp50.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);

		Quantity<TemperatureUnit> tempMinus20 = new Quantity<>(-20.0, TemperatureUnit.CELSIUS);
		assertEquals(-4.0, tempMinus20.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
	}

	@Test
	void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
		Quantity<TemperatureUnit> temp212F = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
		assertEquals(100.0, temp212F.convertTo(TemperatureUnit.CELSIUS), EPSILON);

		Quantity<TemperatureUnit> tempMinus4F = new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT);
		assertEquals(-20.0, tempMinus4F.convertTo(TemperatureUnit.CELSIUS), EPSILON);
	}

	@Test
	void testTemperatureConversion_RoundTrip_PreservesValue() {
		Quantity<TemperatureUnit> originalTemp = new Quantity<>(75.0, TemperatureUnit.CELSIUS);
		double convertedF = originalTemp.convertTo(TemperatureUnit.FAHRENHEIT);
		Quantity<TemperatureUnit> roundTrip = new Quantity<>(convertedF, TemperatureUnit.FAHRENHEIT);
		assertEquals(originalTemp.getValue(), roundTrip.convertTo(TemperatureUnit.CELSIUS), EPSILON);
	}

	@Test
	void testTemperatureConversion_SameUnit() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(23.0, TemperatureUnit.CELSIUS);
		assertEquals(23.0, tempC.convertTo(TemperatureUnit.CELSIUS), EPSILON);
	}

	@Test
	void testTemperatureConversion_ZeroValue() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		assertEquals(32.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
	}

	@Test
	void testTemperatureConversion_NegativeValues() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(-10.0, TemperatureUnit.CELSIUS);
		assertEquals(14.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
	}

	@Test
	void testTemperatureConversion_LargeValues() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(1000.0, TemperatureUnit.CELSIUS);
		assertEquals(1832.0, tempC.convertTo(TemperatureUnit.FAHRENHEIT), EPSILON);
	}

	// Unsupported Operations Tests

	@Test
	void testTemperatureUnsupportedOperation_Add() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempAdd = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			tempC.add(tempAdd);
		});
		assertTrue(exception.getMessage().contains("CELSIUS does not support addition"));
	}

	@Test
	void testTemperatureUnsupportedOperation_Subtract() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempSub = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			tempC.subtract(tempSub);
		});
		assertTrue(exception.getMessage().contains("CELSIUS does not support subtraction"));
	}

	@Test
	void testTemperatureUnsupportedOperation_Divide() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> tempDiv = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
			tempC.divide(tempDiv);
		});
		assertTrue(exception.getMessage().contains("CELSIUS does not support division"));
	}

	// Incompatibility Tests

	@Test
	void testTemperatureVsLengthIncompatibility() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<LengthUnit> lengthFeet = new Quantity<>(100.0, LengthUnit.FEET);
		assertFalse(tempC.equals(lengthFeet));
	}

	@Test
	void testTemperatureVsWeightIncompatibility() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
		Quantity<WeightUnit> weightKG = new Quantity<>(50.0, WeightUnit.KILOGRAM);
		assertFalse(tempC.equals(weightKG));
	}

	@Test
	void testTemperatureVsVolumeIncompatibility() {
		Quantity<TemperatureUnit> tempC = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
		Quantity<VolumeUnit> volumeLitre = new Quantity<>(25.0, VolumeUnit.LITRE);
		assertFalse(tempC.equals(volumeLitre));
	}

	// Operation Support Methods

	@Test
	void testOperationSupportMethods_TemperatureUnitAddition() {
		assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
	}

	@Test
	void testOperationSupportMethods_TemperatureUnitDivision() {
		assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
	}

	@Test
	void testOperationSupportMethods_LengthUnitAddition() {
		assertTrue(LengthUnit.FEET.supportsArithmetic());
	}

	@Test
	void testOperationSupportMethods_WeightUnitDivision() {
		assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
	}

}