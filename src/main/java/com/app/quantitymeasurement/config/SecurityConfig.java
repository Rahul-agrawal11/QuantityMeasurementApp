package com.app.quantitymeasurement.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Enable CORS
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// Disable CSRF for REST
				.csrf(csrf -> csrf.disable())

				// Stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Allow everything (VERY IMPORTANT for testing)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**", "/swagger-ui/**",
						"/swagger-ui.html", "/v3/api-docs/**", "/api/**").permitAll().anyRequest().permitAll());

		// Allow H2 Console frames
		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();

		// Allow ALL origins (for dev/testing)
		config.setAllowedOriginPatterns(Arrays.asList("*"));

		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		config.setAllowedHeaders(Arrays.asList("*"));

		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", config);

		return source;
	}
}