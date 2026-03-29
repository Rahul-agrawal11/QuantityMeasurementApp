package com.app.quantitymeasurement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	private String token;

	public AuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}