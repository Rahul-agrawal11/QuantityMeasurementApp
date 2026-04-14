package com.apigateway.config;

import com.apigateway.security.CustomOAuth2UserService;
import com.apigateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        http
                // Disable CSRF (not needed for APIs)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Authorize requests
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/users/register", "/users/login").permitAll()
                        .pathMatchers("/oauth2/**", "/login/**", "/auth/**").permitAll()
                        .anyExchange().authenticated()
                )

                // OAuth2 Login
                .oauth2Login(oauth2 -> oauth2

                        // On successful login → generate JWT
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {

                            OAuth2User user = (OAuth2User) authentication.getPrincipal();
                            String email = user.getAttribute("email");
                            String name = user.getAttribute("name");

                            if (email == null) {
                                throw new RuntimeException("Email not found from OAuth2 provider");
                            }

                            // 👉 CALL USER SERVICE TO SAVE USER
                            WebClient.create("http://localhost:8081")
                                    .post()
                                    .uri(uriBuilder -> uriBuilder
                                            .path("/users/oauth")
                                            .queryParam("email", email)
                                            .queryParam("name", name)
                                            .build())
                                    .header("X-Internal-Call", "true")
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .subscribe();

                            String token = jwtUtil.generateToken(email);

                            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatus.OK);

                            return response.writeWith(
                                    Mono.just(response.bufferFactory().wrap(token.getBytes()))
                            );
                        })
                );

        return http.build();
    }
}