package com.apigateway.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User user = super.loadUser(request);

        String email = user.getAttribute("email");
        String googleId = user.getAttribute("sub");

        System.out.println("Email: " + email);
        System.out.println("Google Id: " + googleId);

        return user;
    }
}
