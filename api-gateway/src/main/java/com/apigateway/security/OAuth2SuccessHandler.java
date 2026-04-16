//package com.apigateway.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.server.WebFilterExchange;
//import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//
//@Component
//public class OAuth2SuccessHandler implements ServerAuthenticationSuccessHandler {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    private final WebClient webClient = WebClient.create("http://localhost:8081");
//
//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
//                                              Authentication authentication) {
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        ServerHttpResponse response = exchange.getResponse();
//
//        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        // Save user to database via user-service
//        return webClient
//                .post()
//                .uri("/users/oauth?email={email}&name={name}", email, name)
//                .header("X-Internal-Call", "true")
//                .retrieve()
//                .bodyToMono(Void.class)
//                .then(Mono.fromRunnable(() -> {
//                    // Generate JWT token
//                    String token = jwtUtil.generateToken(email);
//
//                    // Build redirect URL
//                    String redirectUrl = "http://localhost:3000/oauth2/redirect?token=" + token + "&email=" + email;
//
//                    // Set redirect response
//                    response.setStatusCode(HttpStatus.FOUND);
//                    response.getHeaders().setLocation(URI.create(redirectUrl));
//                }));
//    }
//}

package com.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class OAuth2SuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name  = oauthUser.getAttribute("name");

        return webClient
                .post()
                .uri("/users/oauth?email={email}&name={name}", email, name)
                .header("X-Internal-Call", "true")
                .retrieve()
                // Don't fail the whole flow if user-service returns an error body
                .bodyToMono(Void.class)
                .onErrorResume(err -> {
                    System.err.println("⚠️  user-service /oauth call failed: " + err.getMessage());
                    return Mono.empty();   // continue anyway — user may already exist
                })
                // ↓ Chain INSIDE the reactive pipeline, not inside fromRunnable
                .then(Mono.defer(() -> {
                    String token = jwtUtil.generateToken(email);

                    String redirectUrl = "http://localhost:3000/oauth2/redirect"
                            + "?token=" + token
                            + "&email=" + java.net.URLEncoder.encode(email,
                            java.nio.charset.StandardCharsets.UTF_8);

                    response.setStatusCode(HttpStatus.FOUND);
                    response.getHeaders().setLocation(URI.create(redirectUrl));

                    // Complete the response properly
                    return response.setComplete();
                }));
    }
}