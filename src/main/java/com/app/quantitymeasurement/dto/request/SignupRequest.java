package com.app.quantitymeasurement.dto.request;

import jakarta.persistence.Column;

public class SignupRequest {
	@Column(unique = true)
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}