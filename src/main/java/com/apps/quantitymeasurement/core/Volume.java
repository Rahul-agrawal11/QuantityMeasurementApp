package com.apps.quantitymeasurement.core;

public class Volume {
	// Instance variable to hold volume value and unit
	private double value;
	private VolumeUnit unit;

	public Volume(double value, VolumeUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null.");
		}
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public VolumeUnit getUnit() {
		return unit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Volume that = (Volume) obj;
		return compare(that);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(unit.convertToBaseUnit(value));
	}

	/**
	 * Conversion Method
	 */
	public Volume convertTo(VolumeUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		// Convert to base (litre)
		double baseValue = convertToBaseUnit();

		// Convert from litre to target unit
		double convertedValue = baseValue / targetUnit.getConversionFactor();

		convertedValue = Math.round(convertedValue * 100.0) / 100.0;

		return new Volume(convertedValue, targetUnit);
	}

	/**
	 * Add two volumes into one specific target unit
	 */
	public Volume add(Volume thatVolume) {

		if (thatVolume == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}

		if (this.unit == thatVolume.unit) {
			return new Volume(this.value + thatVolume.value, this.unit);
		}

		double thisInBase = this.convertToBaseUnit();
		double thatInBase = thatVolume.convertToBaseUnit();

		double totalInBase = thisInBase + thatInBase;
		double convertedValue = convertFromBaseUnitToTargetUnit(totalInBase, this.unit);

		return new Volume(convertedValue, this.unit);
	}

	/**
	 * Adding volume to this volume with specific target unit
	 */
	public Volume add(Volume volume, VolumeUnit targetUnit) {
		return addAndConvert(volume, targetUnit);
	}

	/**
	 * Add two volumes and convert them into specific target unit
	 */
	private Volume addAndConvert(Volume volume, VolumeUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null.");
		}
		Volume vol = this.add(volume);
		double volumeInLitre = vol.convertToBaseUnit();
		double volumeInTargetUnit = convertFromBaseUnitToTargetUnit(volumeInLitre, targetUnit);
		return new Volume(volumeInTargetUnit, targetUnit);
	}

	/**
	 * Compare two volumes
	 */
	private boolean compare(Volume thatVolume) {
		if (thatVolume == null)
			return false;
		return Double.compare(this.convertToBaseUnit(), thatVolume.convertToBaseUnit()) == 0;
	}

	/**
	 * Converts this volume to base unit (litre)
	 */
	private double convertToBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	// Convert base unit to target unit
	private double convertFromBaseUnitToTargetUnit(double baseValue, VolumeUnit targetUnit) {
		return baseValue / targetUnit.getConversionFactor();
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", value, unit.getUnitName());
	}

	public static void main(String[] args) {
		/**
		 * Equals method to check two volumes are equal or not
		 */
		Volume volume1 = new Volume(1.0, VolumeUnit.LITRE);
		Volume volume2 = new Volume(1000.0, VolumeUnit.MILLILITRE);
		System.out.println("Are Volumes equal ? " + volume1.equals(volume2));

		/**
		 * Conversion Method
		 */
		Volume volume3 = new Volume(10.0, VolumeUnit.GALLON);
		Volume convertedVolume = volume3.convertTo(VolumeUnit.LITRE);
		System.out.println("Converted Volume: 10.0 Gallon = " + convertedVolume);

		System.out.println("Converted Volume: 1.0 Litre = " + volume1.convertTo(VolumeUnit.MILLILITRE));

		/**
		 * Add different units of volume
		 */
		Volume litre = volume1.add(volume2);
		System.out.println("1.0 Litre + 1000.0 Millilitre = " + litre);

		Volume gallons = volume3.add(litre);
		System.out.println("10.0 Gallon + 2.0 Litre = " + gallons);

		/**
		 * Add different units of volume with specific target unit
		 */
		Volume gallon = volume1.add(volume2, VolumeUnit.GALLON);
		System.out.println("1.0 Litre + 1000.0 Millilitre = " + gallon);

		Volume millilitre = volume3.add(litre, VolumeUnit.MILLILITRE);
		System.out.println("10.0 Gallon + 2.0 Litre = " + millilitre);

		// Exception Test
		try {
			new Volume(Double.NaN, VolumeUnit.MILLILITRE);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {
			new Volume(10.0, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {
			litre.convertTo(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {
			gallon.add(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {
			gallon.add(litre, null);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}

		try {
			litre.add(null, VolumeUnit.MILLILITRE);
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
