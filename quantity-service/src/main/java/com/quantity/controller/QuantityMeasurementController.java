package com.quantity.controller;

import com.quantity.config.JwtUtil;
import com.quantity.dto.QuantityInputDTO;
import com.quantity.dto.QuantityMeasurementDTO;
import com.quantity.service.IQuantityMeasurementService;
import com.quantity.service.QuantityHistoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST APIs for quantity operations")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @Autowired
    private QuantityHistoryService quantityHistoryService;

    @Autowired
    private JwtUtil jwtUtil;

    // =========================================================
    // CORE OPERATIONS
    // =========================================================

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compare(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity to target unit")
    public ResponseEntity<QuantityMeasurementDTO> convert(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> add(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/add-with-target")
    @Operation(summary = "Add with target unit")
    public ResponseEntity<QuantityMeasurementDTO> addWithTarget(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.add(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO(),
                        input.getTargetQuantityDTO()
                )
        );
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtract(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    @PostMapping("/subtract-with-target")
    @Operation(summary = "Subtract with target unit")
    public ResponseEntity<QuantityMeasurementDTO> subtractWithTarget(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.subtract(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO(),
                        input.getTargetQuantityDTO()
                )
        );
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divide(
            @Valid @RequestBody QuantityInputDTO input) {

        return ResponseEntity.ok(
                service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO())
        );
    }

    // =========================================================
    // HELPER METHOD (JWT EMAIL EXTRACTION)
    // =========================================================

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}