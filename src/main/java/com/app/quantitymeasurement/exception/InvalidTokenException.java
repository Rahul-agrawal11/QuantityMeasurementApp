package com.app.quantitymeasurement.exception;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException() {
		super("Invalid JWT Token");
	}
}