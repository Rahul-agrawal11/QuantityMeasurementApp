package com.quantity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuantityHistoryDTO {
    private String email;
    private String operation;
    private String details;
    private LocalDateTime createdAt;
}