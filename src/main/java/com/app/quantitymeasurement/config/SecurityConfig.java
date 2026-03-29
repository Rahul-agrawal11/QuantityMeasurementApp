package com.app.quantitymeasurement.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.*;

import com.app.quantitymeasurement.oauth2.CustomOAuth2UserService;
import com.app.quantitymeasurement.oauth2.OAuth2SuccessHandler;
import com.app.quantitymeasurement.security.JwtAuthenticationEntryPoint;
import com.app.quantitymeasurement.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final JwtAuthenticationEntryPoint entryPoint;
	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2SuccessHandler successHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter, JwtAuthenticationEntryPoint entryPoint,
			CustomOAuth2UserService oAuth2UserService, OAuth2SuccessHandler successHandler) {
		this.jwtFilter = jwtFilter;
		this.entryPoint = entryPoint;
		this.oAuth2UserService = oAuth2UserService;
		this.successHandler = successHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// Stateless (JWT)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Exception Handling (JWT errors)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))

				// Authorization Rules
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**", "/oauth2/**", "/h2-console/**", "/swagger-ui/**",
								"/swagger-ui.html", "/v3/api-docs/**")
						.permitAll()

						// Secure your APIs now
						.requestMatchers("/api/**").authenticated()

						.anyRequest().authenticated())

				// OAuth2 Login
				.oauth2Login(oauth -> oauth.userInfoEndpoint(user -> user.userService(oAuth2UserService))
						.successHandler(successHandler));

		// Add JWT Filter
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		// Allow H2 Console
		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

		return http.build();
	}

	// AuthenticationManager (REQUIRED)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// CORS (keep your existing)
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}