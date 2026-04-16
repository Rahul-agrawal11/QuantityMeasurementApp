package com.app.quantitymeasurement.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

	@Autowired
	private IQuantityMeasurementService service;

	// =========================================================
	// CORE OPERATIONS
	// =========================================================

	@PostMapping("/compare")
	@Operation(summary = "Compare two quantities")
	public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/convert")
	@Operation(summary = "Convert quantity to target unit")
	public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/add")
	@Operation(summary = "Add two quantities")
	public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/add-with-target-unit")
	@Operation(summary = "Add with target unit")
	public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(
					service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/subtract")
	@Operation(summary = "Subtract two quantities")
	public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/subtract-with-target-unit")
	@Operation(summary = "Subtract with target unit")
	public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO(),
					input.getTargetQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	@PostMapping("/divide")
	@Operation(summary = "Divide two quantities")
	public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO input) {

		try {
			return ResponseEntity.ok(service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return ResponseEntity.badRequest().body(errorDTO(e));
		}
	}

	// =========================================================
	// HISTORY APIs
	// =========================================================

	@GetMapping("/history/operation/{operation}")
	@Operation(summary = "Get operation history")
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {

		return ResponseEntity.ok(service.getOperationHistory(operation));
	}

	@GetMapping("/history/type/{type}")
	@Operation(summary = "Get history by measurement type")
	public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByType(@PathVariable String type) {

		return ResponseEntity.ok(service.getMeasurementByType(type));
	}

	@GetMapping("/count/{operation}")
	@Operation(summary = "Get operation count")
	public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {

		return ResponseEntity.ok(service.getOperationCount(operation));
	}

	@GetMapping("/history/errored")
	@Operation(summary = "Get errored operations history")
	public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations() {

		return ResponseEntity.ok(service.getErrorHistory());
	}

	// =========================================================
	// ERROR DTO HELPER
	// =========================================================

	private QuantityMeasurementDTO errorDTO(Exception e) {
		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
		dto.setError(true);
		dto.setErrorMessage(e.getMessage());
		return dto;
	}
}