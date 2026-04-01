package com.app.quantitymeasurement.model;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityMeasurementDTO {

	private Double thisValue;
	private String thisUnit;
	private String thisMeasurementType;

	private Double thatValue;
	private String thatUnit;
	private String thatMeasurementType;

	private String operation;

	private String resultString;
	private Double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	private String errorMessage;

	public Double getThisValue() {
		return thisValue;
	}

	public void setThisValue(Double thisValue) {
		this.thisValue = thisValue;
	}

	public String getThisUnit() {
		return thisUnit;
	}

	public void setThisUnit(String thisUnit) {
		this.thisUnit = thisUnit;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public Double getThatValue() {
		return thatValue;
	}

	public void setThatValue(Double thatValue) {
		this.thatValue = thatValue;
	}

	public String getThatUnit() {
		return thatUnit;
	}

	public void setThatUnit(String thatUnit) {
		this.thatUnit = thatUnit;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@JsonProperty("error")
	private boolean error;

	public QuantityMeasurementDTO(Double thisValue, String thisUnit, String thisMeasurementType, Double thatValue,
			String thatUnit, String thatMeasurementType, String operation, String resultString, Double resultValue,
			String resultUnit, String resultMeasurementType, String errorMessage, boolean error) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;

		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;

		this.operation = operation;

		this.resultString = resultString;
		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;

		this.errorMessage = errorMessage;
		this.error = error;
	}

	public QuantityMeasurementDTO() {

	}

	// ------------------- ENTITY → DTO -------------------
	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		if (entity == null)
			return null;

		return new QuantityMeasurementDTO(entity.getThisValue(), entity.getThisUnit(), entity.getThisMeasurementType(),
				entity.getThatValue(), entity.getThatUnit(), entity.getThatMeasurementType(), entity.getOperation(),
				entity.getResultString(), entity.getResultValue(), entity.getResultUnit(),
				entity.getResultMeasurementType(), entity.getErrorMessage(), entity.isError());
	}

	// ------------------- DTO → ENTITY -------------------
	public QuantityMeasurementEntity toEntity() {
		return QuantityMeasurementEntity.builder().thisValue(thisValue).thisUnit(thisUnit)
				.thisMeasurementType(thisMeasurementType)

				.thatValue(thatValue).thatUnit(thatUnit).thatMeasurementType(thatMeasurementType)

				.operation(operation)

				.resultString(resultString).resultValue(resultValue).resultUnit(resultUnit)
				.resultMeasurementType(resultMeasurementType)

				.errorMessage(errorMessage).isError(error).build();
	}

	// ------------------- LIST MAPPERS -------------------
	public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
		if (entities == null)
			return List.of();
		return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
		if (dtos == null)
			return List.of();
		return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
	}
}