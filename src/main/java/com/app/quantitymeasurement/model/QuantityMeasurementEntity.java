package com.app.quantitymeasurement.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity", indexes = { @Index(name = "idx_operation", columnList = "operation"),
		@Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
		@Index(name = "idx_created_at", columnList = "created_at") })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuantityMeasurementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// ------------------- INPUT (THIS) -------------------
	@Column(name = "this_value", nullable = false)
	private Double thisValue;

	@Column(name = "this_unit", nullable = false)
	private String thisUnit;

	@Column(name = "this_measurement_type", nullable = false)
	private String thisMeasurementType;

	// ------------------- INPUT (THAT) -------------------
	// Optional for some operations like CONVERT
	@Column(name = "that_value")
	private Double thatValue;

	@Column(name = "that_unit")
	private String thatUnit;

	@Column(name = "that_measurement_type")
	private String thatMeasurementType;

	// ------------------- OPERATION -------------------
	@Column(name = "operation", nullable = false)
	private String operation;

	// ------------------- RESULT -------------------
	@Column(name = "result_value")
	private Double resultValue;

	@Column(name = "result_unit")
	private String resultUnit;

	@Column(name = "result_measurement_type")
	private String resultMeasurementType;

	// For comparison (EQUAL / NOT_EQUAL)
	@Column(name = "result_string")
	private String resultString;

	// ------------------- ERROR HANDLING -------------------
	@Column(name = "is_error")
	private boolean isError;

	// FIXED: prevent "value too long" DB error
	@Column(name = "error_message", columnDefinition = "TEXT")
	private String errorMessage;

	// ------------------- AUDIT -------------------
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// ------------------- LIFECYCLE -------------------
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

	// ================= CONSTRUCTORS =================
	public QuantityMeasurementEntity() {
	}

	private QuantityMeasurementEntity(Builder builder) {
		this.thisValue = builder.thisValue != null ? builder.thisValue : 0.0;
		this.thisUnit = builder.thisUnit;
		this.thisMeasurementType = builder.thisMeasurementType;
		this.thatValue = builder.thatValue != null ? builder.thatValue : 0.0;
		this.thatUnit = builder.thatUnit;
		this.thatMeasurementType = builder.thatMeasurementType;
		this.operation = builder.operation;
		this.resultString = builder.resultString;
		this.resultValue = builder.resultValue != null ? builder.resultValue : 0.0;
		this.resultUnit = builder.resultUnit;
		this.resultMeasurementType = builder.resultMeasurementType;
		this.errorMessage = builder.errorMessage;
		this.isError = builder.isError;
	}

	// ================= BUILDER =================
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
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
		private boolean isError;

		public Builder thisValue(Double val) {
			this.thisValue = val;
			return this;
		}

		public Builder thisUnit(String unit) {
			this.thisUnit = unit;
			return this;
		}

		public Builder thisMeasurementType(String type) {
			this.thisMeasurementType = type;
			return this;
		}

		public Builder thatValue(Double val) {
			this.thatValue = val;
			return this;
		}

		public Builder thatUnit(String unit) {
			this.thatUnit = unit;
			return this;
		}

		public Builder thatMeasurementType(String type) {
			this.thatMeasurementType = type;
			return this;
		}

		public Builder operation(String op) {
			this.operation = op;
			return this;
		}

		public Builder resultString(String result) {
			this.resultString = result;
			return this;
		}

		public Builder resultValue(Double val) {
			this.resultValue = val;
			return this;
		}

		public Builder resultUnit(String unit) {
			this.resultUnit = unit;
			return this;
		}

		public Builder resultMeasurementType(String type) {
			this.resultMeasurementType = type;
			return this;
		}

		public Builder errorMessage(String msg) {
			this.errorMessage = msg;
			return this;
		}

		public Builder isError(boolean error) {
			this.isError = error;
			return this;
		}

		public QuantityMeasurementEntity build() {
			return new QuantityMeasurementEntity(this);
		}
	}
}