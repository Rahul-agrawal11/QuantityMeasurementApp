package com.quantity.dto;

import com.quantity.model.IMeasurable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Length;
import org.jboss.logging.Logger;


@Data
@Schema(description = "A quantity with a value and unit")
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {
    private static final Logger logger =  Logger.getLogger(QuantityDTO.class);

    // ===================== LENGTH =====================
    public enum LengthUnit implements IMeasurable {
        FEET(12.0), INCHES(1.0), YARDS(36.0), CENTIMETERS(0.393701);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        @Override
        public double getConversionFactor() {
            return factor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return factor * value;
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "LengthUnit";
        }

        @Override
        public IMeasurable getUnitInstance(String unitName) {
            return LengthUnit.valueOf(unitName.toUpperCase());
        }
    }

    // ===================== WEIGHT =====================
    public enum WeightUnit implements IMeasurable {

        KILOGRAM(1000), GRAM(1), POUND(453.592), MILIGRAM(0.001), TONNE(1000000);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        @Override
        public double getConversionFactor() {
            return factor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        @Override
        public double convertFromBaseUnit(double base) {
            return base / factor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "WeightUnit";
        }

        @Override
        public IMeasurable getUnitInstance(String unitName) {
            return WeightUnit.valueOf(unitName.toUpperCase());
        }
    }

    // ===================== VOLUME =====================
    public enum VolumeUnit implements IMeasurable {

        LITRE(1), MILLILITRE(0.001), GALLON(3.785);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        @Override
        public double getConversionFactor() {
            return factor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        @Override
        public double convertFromBaseUnit(double base) {
            return base / factor;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "VolumeUnit";
        }

        @Override
        public IMeasurable getUnitInstance(String unitName) {
            return VolumeUnit.valueOf(unitName.toUpperCase());
        }
    }

    // ===================== TEMPERATURE =====================
    public enum TemperatureUnit implements IMeasurable {

        CELSIUS, FAHRENHEIT;

        @Override
        public double getConversionFactor() {
            return 1;
        }

        @Override
        public double convertToBaseUnit(double value) {
            if (this == FAHRENHEIT) {
                return (value - 32) * 5 / 9;
            }
            return value;
        }

        @Override
        public double convertFromBaseUnit(double base) {
            if (this == FAHRENHEIT) {
                return (base * 9 / 5) + 32;
            }
            return base;
        }

        @Override
        public String getUnitName() {
            return name();
        }

        @Override
        public String getMeasurementType() {
            return "TemperatureUnit";
        }

        @Override
        public IMeasurable getUnitInstance(String unitName) {
            return TemperatureUnit.valueOf(unitName.toUpperCase());
        }
    }

    // ===================== FIELDS =====================
    @NotNull(message = "Value cannot be empty")
    @Schema(example = "1.0")
    private double value;

    @NotNull(message = "Unit cannot be null")
    private String unit;

    @NotNull(message = "Measurement type cannot be null")
    @Pattern(regexp = "LengthUnit|WeightUnit|VolumeUnit|TemperatureUnit", message = "Invalid measurement type")
    private String measurementType;

    // ===================== VALIDATION =====================
    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {

        logger.info("Validating unit: " + unit + " for type: " + measurementType);

        try {
            switch (measurementType) {
                case "LengthUnit":
                    LengthUnit.valueOf(unit.toUpperCase());
                    break;
                case "WeightUnit":
                    WeightUnit.valueOf(unit.toUpperCase());
                    break;
                case "VolumeUnit":
                    VolumeUnit.valueOf(unit.toUpperCase());
                    break;
                case "TemperatureUnit":
                    TemperatureUnit.valueOf(unit.toUpperCase());
                    break;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public IMeasurable getUnitInstance() {

        if (measurementType == null || unit == null) {
            throw new RuntimeException("Measurement type or unit cannot be null");
        }

        return switch (measurementType) {
            case "LengthUnit" -> LengthUnit.valueOf(unit.toUpperCase());
            case "WeightUnit" -> WeightUnit.valueOf(unit.toUpperCase());
            case "VolumeUnit" -> VolumeUnit.valueOf(unit.toUpperCase());
            case "TemperatureUnit" -> TemperatureUnit.valueOf(unit.toUpperCase());
            default -> throw new RuntimeException("Invalid Measurement Type: " + measurementType);
        };
    }
}
