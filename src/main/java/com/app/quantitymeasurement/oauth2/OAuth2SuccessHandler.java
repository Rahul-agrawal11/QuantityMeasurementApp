package com.app.quantitymeasurement.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.app.quantitymeasurement.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;

	@Value("${frontend.url:http://localhost:3000}")
	private String frontendUrl;

	public OAuth2SuccessHandler(JwtService jwtService) {
		super();
		this.jwtService = jwtService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException {

		OAuth2User user = (OAuth2User) auth.getPrincipal();
		String email = user.getAttribute("email");
		String token = jwtService.generateToken(email);

		String redirectUrl = frontendUrl + "/oauth2/callback?token=" + token + "&email=" + email;
		response.sendRedirect(redirectUrl);
	}
}
