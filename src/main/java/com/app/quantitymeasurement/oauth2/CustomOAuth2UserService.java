package com.app.quantitymeasurement.oauth2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.model.AppUser;
import com.app.quantitymeasurement.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder encoder;

	public CustomOAuth2UserService(UserRepository userRepo, PasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

		OAuth2User user = super.loadUser(request);

		String email = user.getAttribute("email");

		// Check if user exists
		if (userRepo.findByEmail(email).isEmpty()) {

			AppUser newUser = new AppUser();
			newUser.setEmail(email);

			// OAuth users don't need password
			newUser.setPassword(encoder.encode("oauth2-user"));

			userRepo.save(newUser);
		}

		return user;
	}
}