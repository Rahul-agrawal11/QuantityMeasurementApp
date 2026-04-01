package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global Exception Handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

	/**
	 * Error Response DTO
	 */
	static class ErrorResponse {
		public LocalDateTime timestamp;
		public int status;
		public String error;
		public String message;
		public String path;
	}

	/**
	 * Handle Validation Errors (@Valid)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {

		logger.warning("Validation Exception: " + ex.getMessage());

		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

		List<String> errMsg = errorList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());

		ErrorResponse error = new ErrorResponse();
		error.timestamp = LocalDateTime.now();
		error.status = HttpStatus.BAD_REQUEST.value();
		error.error = "Validation Error";
		error.message = String.join("; ", errMsg);
		error.path = request.getDescription(false).replace("uri=", "");

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle Custom QuantityMeasurementException
	 */
	@ExceptionHandler(QuantityMeasurementException.class)
	public ResponseEntity<ErrorResponse> handleQuantityException(QuantityMeasurementException ex, WebRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.timestamp = LocalDateTime.now();
		error.status = HttpStatus.BAD_REQUEST.value();
		error.error = "Quantity Measurement Error";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("uri=", "");

		logger.warning("Handling QuantityMeasurementException: " + ex.getMessage() + " for path: " + error.path);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle All Other Exceptions
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.timestamp = LocalDateTime.now();
		error.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		error.error = "Internal Server Error";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("uri=", "");

		logger.severe("Global Exception: " + ex.getMessage() + " for path: " + error.path);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthException(Exception ex, WebRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.timestamp = LocalDateTime.now();
		error.status = HttpStatus.UNAUTHORIZED.value();
		error.error = "Unauthorized";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("uri=", "");

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(Exception ex, WebRequest request) {

		ErrorResponse error = new ErrorResponse();
		error.timestamp = LocalDateTime.now();
		error.status = HttpStatus.FORBIDDEN.value();
		error.error = "Access Denied";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("uri=", "");

		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
}