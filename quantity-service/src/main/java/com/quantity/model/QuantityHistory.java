package com.quantity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_history")
@Data
public class QuantityHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String operation;
    private String details;
    private LocalDateTime createdAt;
}