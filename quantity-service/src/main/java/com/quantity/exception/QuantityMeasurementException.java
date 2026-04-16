package com.quantity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class QuantityMeasurementException extends RuntimeException {

    private final HttpStatus status;

    public QuantityMeasurementException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}