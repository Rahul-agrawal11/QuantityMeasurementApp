package com.apps.quantitymeasurement.dto;

public class QuantityDTO {
	public interface IMeasurableUnit {
		String getUnitName();

		String getMeasurementType();
	}

	public enum LengthUnit implements IMeasurableUnit {
		FEET, INCHES, YARDS, CENTIMETERS;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	public enum WeightUnit implements IMeasurableUnit {
		KILOGRAM, GRAM, POUND, MILIGRAM, TONNE;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	public enum VolumeUnit implements IMeasurableUnit {
		LITRE, MILLILITRE, GALLON;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	public enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	// Fields
	public double value;
	public String unit;
	public String measurementType;

	public QuantityDTO(double value, IMeasurableUnit unit) {
		this.value = value;
		this.unit = unit.getUnitName();
		this.measurementType = unit.getMeasurementType();
	}

	public QuantityDTO(double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	@Override
	public String toString() {
		return "QuantityDTO(" + value + ", " + unit + ", " + measurementType + ")";
	}
}