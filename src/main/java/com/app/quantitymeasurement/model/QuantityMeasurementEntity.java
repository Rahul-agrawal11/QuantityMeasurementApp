package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity // Marks this class as a JPA Entity
@Table(name = "quantity_measurement_entity", indexes = { 
		@Index(name = "idx_operation", columnList = "operation"),
		@Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
		@Index(name = "idx_created_at", columnList = "created_at") })
@Data // Lombok annotation to generate getter, setter, toString, equals, and hashcode
		// methods
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate an all-argument constructor

public class QuantityMeasurementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "this_value", nullable = false)
	public double thisValue;
	@Column(name = "this_unit", nullable = false)
	public String thisUnit;
	@Column(name = "this_measurement_type", nullable = false)
	public String thisMeasurementType;
	@Column(name = "that_value", nullable = false)
	public double thatValue;
	@Column(name = "that_unit", nullable = false)
	public String thatUnit;
	@Column(name = "that_measurement_type", nullable = false)
	public String thatMeasurementType;

	// e.g., "COMPARE", "CONVER", "ADD", "SUBTRACT", "DIVIDE"
	@Column(name = "operation", nullable = false)
	public String operation;
	@Column(name = "result_value")
	public double resultValue;
	@Column(name = "result_unit")
	public String resultUnit;
	@Column(name = "result_measurement_type")
	public String resultMeasurementType;

	// For comparison results like "Equal" or "Not Equal"
	@Column(name = "result_string")
	public String resultString;

	// Flag to indicate if an error occured during the operation
	@Column(name = "is_error")
	public boolean isError;

	// For capturing any error message during operation
	@Column(name = "error_message")
	public String errorMessage;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getThisValue() {
		return thisValue;
	}

	public void setThisValue(double thisValue) {
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

	public double getThatValue() {
		return thatValue;
	}

	public void setThatValue(double thatValue) {
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

	public double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
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

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public QuantityMeasurementEntity() {
	}

	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, String operation,
			double resultValue, String resultUnit, String resultMeasurementType) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		this.operation = operation;

		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;

		this.isError = false;
	}

	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue,
			String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit,
			String resultMeasurementType) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;

		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;

		this.operation = operation;

		this.resultValue = resultValue;
		this.resultUnit = resultUnit;
		this.resultMeasurementType = resultMeasurementType;

		this.isError = false;
	}

	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue,
			String thatUnit, String thatMeasurementType, String operation, String resultString) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;

		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;

		this.operation = operation;
		this.resultString = resultString;

		this.isError = false;
	}

	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, String operation,
			String errorMessage) {

		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;

		this.operation = operation;
		this.errorMessage = errorMessage;

		this.isError = true;
	}
}
