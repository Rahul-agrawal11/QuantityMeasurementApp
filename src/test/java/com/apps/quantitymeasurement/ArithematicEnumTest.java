package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.Quantity;
import com.apps.quantitymeasurement.core.VolumeUnit;
import com.apps.quantitymeasurement.core.WeightUnit;

public class ArithematicEnumTest {
	private static final double EPSILON = 1e-5;

	@Test
	void testAdd_UC12_BehaviorPreserved() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtract_UC12_BehaviorPreserved() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES));

		assertEquals(9.5, result.getValue(), EPSILON);
	}

	@Test
	void testDivide_UC12_BehaviorPreserved() {
		double result = new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(1.0, result, EPSILON);
	}

	// =========================================================
	// Validation Consistency
	// =========================================================

	@Test
	void testValidation_NullOperand_ConsistentAcrossOperations() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q.add(null));
		assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
		assertThrows(IllegalArgumentException.class, () -> q.divide(null));
	}

	@Test
	void testValidation_CrossCategory_ConsistentAcrossOperations() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));

		assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
	}

	@Test
	void testValidation_NullTargetUnit_AddSubtractReject() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));

		assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
	}

	// Enum Operation Dispatch (Indirect Validation)

	@Test
	void testArithmeticOperation_Add_EnumComputation() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(5.0, WeightUnit.KILOGRAM));

		assertEquals(15.0, result.getValue(), EPSILON);
	}

	@Test
	void testArithmeticOperation_Subtract_EnumComputation() {
		Quantity<WeightUnit> result = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));

		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	void testArithmeticOperation_Divide_EnumComputation() {
		double result = new Quantity<>(10.0, WeightUnit.KILOGRAM).divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));

		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testArithmeticOperation_DivideByZero_EnumThrows() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);

		assertThrows(ArithmeticException.class, () -> q1.divide(zero));
	}

	// Rounding Consistency

	@Test
	void testRounding_AddSubtract_TwoDecimalPlaces() {
		Quantity<LengthUnit> result = new Quantity<>(1.3333, LengthUnit.FEET)
				.add(new Quantity<>(1.3333, LengthUnit.FEET));

		assertEquals(2.667, result.getValue(), EPSILON);
	}

	@Test
	void testRounding_Divide_NoRounding() {
		double result = new Quantity<>(1.0, LengthUnit.FEET).divide(new Quantity<>(3.0, LengthUnit.FEET));

		assertEquals(0.333333, result, 0.0001);
	}

	// Immutability

	@Test
	void testImmutability_AfterAdd() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> q2 = q1.add(new Quantity<>(5.0, LengthUnit.FEET));

		assertNotSame(q1, q2);
		assertEquals(10.0, q1.getValue(), EPSILON);
	}

	@Test
	void testImmutability_AfterSubtract() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> result = q1.subtract(new Quantity<>(5.0, LengthUnit.FEET));

		assertEquals(10.0, q1.getValue(), EPSILON);
		assertNotSame(q1, result);
	}

	@Test
	void testImmutability_AfterDivide() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);

		q1.divide(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(10.0, q1.getValue(), EPSILON);
	}

	// All Categories

	@Test
	void testAllOperations_AcrossAllCategories() {

		// Length
		assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);

		// Weight
		assertEquals(2.0, new Quantity<>(2000.0, WeightUnit.GRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)),
				EPSILON);

		// Volume
		assertEquals(1.0, new Quantity<>(1000.0, VolumeUnit.MILLILITRE).divide(new Quantity<>(1.0, VolumeUnit.LITRE)),
				EPSILON);
	}

	// Helper Encapsulation (Reflection)

	@Test
	void testHelper_PrivateVisibility() throws Exception {

	    Class<?> arithmeticClass =
	            Class.forName("com.apps.quantitymeasurement.core.Quantity$ArithmeticOperation");

	    Method method = Quantity.class.getDeclaredMethod(
	            "performBaseArithmetic",
	            Quantity.class,
	            arithmeticClass
	    );

	    assertTrue(Modifier.isPrivate(method.getModifiers()));
	}

	@Test
	void testValidation_Helper_PrivateVisibility() throws Exception {

		Method method = Quantity.class.getDeclaredMethod("validateArithmeticOperands", Quantity.class,
				IMeasurable.class, boolean.class);

		assertTrue(Modifier.isPrivate(method.getModifiers()));
	}
}
