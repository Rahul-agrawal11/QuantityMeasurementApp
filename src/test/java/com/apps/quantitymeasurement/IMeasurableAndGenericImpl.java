package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.core.Quantity;
import com.apps.quantitymeasurement.core.WeightUnit;

public class IMeasurableAndGenericImpl {
	private static final double EPSILON = 1e-5;

	// ------------------------------------------------
	// IMeasurable Interface Tests
	// ------------------------------------------------

	@Test
	void testIMeasurableInterface_LengthUnitImplementation() {
		IMeasurable unit = LengthUnit.FEET;

		assertNotNull(unit.getConversionFactor());
		assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(LengthUnit.FEET.convertToBaseUnit(1.0)), EPSILON);
	}

	@Test
	void testIMeasurableInterface_WeightUnitImplementation() {
		WeightUnit unit = WeightUnit.KILOGRAM;

		assertNotNull(unit.getConversionFactor());
		assertEquals(1000.0, WeightUnit.GRAM.convertFromBaseUnit(WeightUnit.KILOGRAM.convertToBaseUnit(1.0)),
				EPSILON);
	}

	@Test
	void testIMeasurableInterface_ConsistentBehavior() {

	    double feetBase = LengthUnit.FEET.convertToBaseUnit(1);
	    double kgBase = WeightUnit.KILOGRAM.convertToBaseUnit(1);

	    assertEquals(12.0, feetBase, EPSILON);
	    assertEquals(1000.0, kgBase, EPSILON);
	}

	// ------------------------------------------------
	// Generic Equality Tests
	// ------------------------------------------------

	@Test
	void testGenericQuantity_LengthOperations_Equality() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(a.equals(b));
	}

	@Test
	void testGenericQuantity_WeightOperations_Equality() {
		Quantity<WeightUnit> a = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> b = new Quantity<>(1000.0, WeightUnit.GRAM);

		assertTrue(a.equals(b));
	}

	// ------------------------------------------------
	// Conversion Tests
	// ------------------------------------------------

	@Test
	void testGenericQuantity_LengthOperations_Conversion() {

	    Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);

	    double inches = feet.convertTo(LengthUnit.INCHES);

	    assertEquals(12.0, inches, EPSILON);
	}

	@Test
	void testGenericQuantity_WeightOperations_Conversion() {
		Quantity<WeightUnit> kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		double grams = kg.convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, grams, EPSILON);
	}

	// ------------------------------------------------
	// Addition Tests
	// ------------------------------------------------

	@Test
	void testGenericQuantity_LengthOperations_Addition() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testGenericQuantity_WeightOperations_Addition() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	// ------------------------------------------------
	// Cross Category Safety
	// ------------------------------------------------

	@Test
	void testCrossCategoryPrevention_LengthVsWeight() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals(weight));
	}

	// ------------------------------------------------
	// Constructor Validation
	// ------------------------------------------------

	@Test
	void testGenericQuantity_ConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
	}

	@Test
	void testGenericQuantity_ConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	// ------------------------------------------------
	// HashCode Contract
	// ------------------------------------------------

	@Test
	void testHashCode_GenericQuantity_Consistency() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		assertEquals(a.hashCode(), b.hashCode());
	}

	// ------------------------------------------------
	// Equals Contract
	// ------------------------------------------------

	@Test
	void testEquals_GenericQuantity_ContractPreservation() {

		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> c = new Quantity<>(36.0, LengthUnit.INCHES);

		// Reflexive
		assertTrue(a.equals(a));

		// Symmetric
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));

		// Transitive
		assertFalse(a.equals(c));
	}

	// ------------------------------------------------
	// Immutability
	// ------------------------------------------------

	@Test
	void testImmutability_GenericQuantity() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result = a.add(new Quantity<>(12.0, LengthUnit.INCHES));

		assertNotSame(a, result);
	}
}
