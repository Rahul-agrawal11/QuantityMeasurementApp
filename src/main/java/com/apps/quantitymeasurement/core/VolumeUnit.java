/**
 * VolumeUnit.java
 * 
 * The VolumeUnit enumeration implements IMeasurable interface and provides 
 * methods for unit conversion. It defines various units of volume measurement 
 * along with their conversion factors relative to a base unit (litre). This 
 * enumeration is used in the QuantityMeasurement application to facilitate 
 * conversions and comparisons between different volume units.
 * 
 * <p>The base unit for conversion is litre. Each unit stores a conversion factor 
 * relative to litre (the base unit). This design simplifies unit conversions by 
 * always converting through a common base unit. </p>
 * 
 * <p>Example: 1 LITRE 1000.0 MILLILITRE, 1 GALLON 3.78541 LITRE</p>
*/

package com.apps.quantitymeasurement.core;

public enum VolumeUnit implements IMeasurable{
	
	// Conversion factor to the base unit(grams)
	LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);
	
	private final double conversionFactor;

	private VolumeUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	
	public double getConversionFactor() {
		return conversionFactor;
	}
	
	@Override
	public String getUnitName() {
		return this.toString();
	}
	
	/**
	 * Convert the length value to base unit value
	 */
	public double convertToBaseUnit(double value) {
	    return value * this.conversionFactor;
	}
	 /**
	  * Convert base unit value to specified unit
	  */
	public double convertFromBaseUnit(double baseValue) {
		double convertedValue = baseValue / this.getConversionFactor();
		return Math.round(convertedValue * 100.0) / 100.0;
	}
	
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
	
	public IMeasurable getUnitInstance(String unitName) {
		for (VolumeUnit unit : VolumeUnit.values()) {
			if (unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid volume unit: " + unitName);
	}
	
	public static void main(String[] args) {
		double millilitre = 5000.0;
		double litre = VolumeUnit.MILLILITRE.convertToBaseUnit(millilitre);
		System.out.println("5000.0 Millilitre = " + litre + " Litre");
		
		double gallon = VolumeUnit.GALLON.convertFromBaseUnit(litre);
		System.out.println("5.0 Litre = " + gallon + " Gallon");
	}
}
