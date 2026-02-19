package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.QuantityMeasurementApp.*;


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
    public void testFeetInchEquality() {
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
    public void testNullComparison() {
    	Length feet = new Length(1.0, Length.LengthUnit.FEET);
    	Length inches = new Length(12.0, Length.LengthUnit.INCHES);
    	assertFalse(feet.equals(null));
    	assertFalse(inches.equals(null));
    }
}
