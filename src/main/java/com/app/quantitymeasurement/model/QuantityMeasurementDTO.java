package com.app.quantitymeasurement.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data class QuantityMeasurementDTO {

	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;
	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;
	public String operation;
	public String resultString;
	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;
	public String errorMessage;

	public double getThisValue() {
		return thisValue;
	}

	public String getThisUnit() {
		return thisUnit;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public double getThatValue() {
		return thatValue;
	}

	public String getThatUnit() {
		return thatUnit;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public String getOperation() {
		return operation;
	}

	public String getResultString() {
		return resultString;
	}

	public double getResultValue() {
		return resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setThisValue(double thisValue) {
		this.thisValue = thisValue;
	}

	public void setThisUnit(String thisUnit) {
		this.thisUnit = thisUnit;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public void setThatValue(double thatValue) {
		this.thatValue = thatValue;
	}

	public void setThatUnit(String thatUnit) {
		this.thatUnit = thatUnit;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
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
	public boolean error;

	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		if (entity == null) {
			return null;
		}
		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

		dto.thisValue = entity.thisValue;
		dto.thisUnit = entity.thisUnit;
		dto.thisMeasurementType = entity.thisMeasurementType;

		dto.thatValue = entity.thatValue;
		dto.thatUnit = entity.thatUnit;
		dto.thatMeasurementType = entity.thatMeasurementType;

		dto.operation = entity.operation;
		dto.resultString = entity.resultString;
		dto.resultValue = entity.resultValue;
		dto.resultUnit = entity.resultUnit;
		dto.resultMeasurementType = entity.resultMeasurementType;

		dto.errorMessage = entity.errorMessage;
		dto.error = entity.isError;
		return dto;
	}

	public QuantityMeasurementEntity toEntity() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
		entity.setThisValue(thisValue);
		entity.setThisUnit(thisUnit);
		entity.setThisMeasurementType(thisMeasurementType);

		entity.setThatValue(thatValue);
		entity.setThatUnit(thatUnit);
		entity.setThatMeasurementType(thatMeasurementType);

		entity.setOperation(this.operation);
		entity.setResultString(this.resultString);
		entity.setResultValue(this.resultValue);
		entity.setResultUnit(this.resultUnit);
		entity.setResultMeasurementType(this.resultMeasurementType);

		entity.setErrorMessage(this.errorMessage);
		entity.setError(this.error);

		return entity;
	}

	public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
		if (entities == null) {
			return List.of();
		}
		return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
		if (dtos == null) {
			return List.of();
		}
		return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
	}
}
