package com.quantity.controller;

import com.quantity.dto.QuantityHistoryDTO;
import com.quantity.model.QuantityHistory;
import com.quantity.service.QuantityHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/history")
public class QuantityHistoryController {

    @Autowired
    private QuantityHistoryService historyService;

    // =========================================================
    // GET ALL HISTORY FOR USER
    // =========================================================

    @GetMapping
    public ResponseEntity<List<QuantityHistoryDTO>> getAllHistory() {

        String email = getEmail();

        List<QuantityHistoryDTO> result = historyService.getAllHistory(email)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // =========================================================
    // GET HISTORY BY OPERATION
    // =========================================================

    @GetMapping("/operation/{operation}")
    public ResponseEntity<List<QuantityHistoryDTO>> getByOperation(
            @PathVariable String operation) {

        String email = getEmail();

        List<QuantityHistoryDTO> result = historyService
                .getOperationHistory(email, operation)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    private String getEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    private QuantityHistoryDTO mapToDTO(QuantityHistory h) {
        QuantityHistoryDTO dto = new QuantityHistoryDTO();
        dto.setEmail(h.getEmail());
        dto.setOperation(h.getOperation());
        dto.setDetails(h.getDetails());
        dto.setCreatedAt(h.getCreatedAt());
        return dto;
    }
}