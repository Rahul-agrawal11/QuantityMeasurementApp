package com.app.quantitymeasurement.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.request.LoginRequest;
import com.app.quantitymeasurement.dto.request.SignupRequest;
import com.app.quantitymeasurement.dto.response.AuthResponse;
import com.app.quantitymeasurement.exception.UserAlreadyExistsException;
import com.app.quantitymeasurement.model.AppUser;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	
	

	public AuthService(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepo,
			PasswordEncoder encoder) {
		super();
		this.authManager = authManager;
		this.jwtService = jwtService;
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	public AuthResponse login(LoginRequest request) {

		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = jwtService.generateToken(request.getEmail());

		return new AuthResponse(token);
	}

	public AuthResponse signup(SignupRequest request) {

		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException();
		}

		AppUser user = new AppUser();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));

		userRepo.save(user);

		String token = jwtService.generateToken(user.getEmail());
		return new AuthResponse(token);
	}
}