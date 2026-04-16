//package com.apigateway.controller;
//
//import com.apigateway.security.JwtUtil;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.Map;
//
//@RestController
//public class GoogleOAuthController {
//
//    @Value("${google.client-id}")
//    private String clientId;
//
//    @Value("${google.client-secret}")
//    private String clientSecret;
//
//    private final JwtUtil jwtUtil;
//    private final WebClient webClient;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public GoogleOAuthController(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//        this.webClient = WebClient.builder().build();
//    }
//
//    // Step 1: Redirect to Google
//    @GetMapping("/auth/google")
//    public Mono<Void> googleLogin(ServerWebExchange exchange) {
//
//        String googleAuthUrl =
//                "https://accounts.google.com/o/oauth2/v2/auth"
//                        + "?client_id=" + clientId
//                        + "&redirect_uri=http://localhost:8080/auth/google/callback"
//                        + "&response_type=code"
//                        + "&scope=email%20profile"
//                        + "&access_type=offline"
//                        + "&prompt=select_account";
//
//        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
//        exchange.getResponse().getHeaders().setLocation(URI.create(googleAuthUrl));
//
//        return exchange.getResponse().setComplete();
//    }
//
//    // Step 2: Callback
//    @GetMapping("/auth/google/callback")
//    public Mono<Void> googleCallback(@RequestParam("code") String code,
//                                     ServerWebExchange exchange) {
//
//        String tokenRequestBody =
//                "code=" + code +
//                        "&client_id=" + clientId +
//                        "&client_secret=" + clientSecret +
//                        "&redirect_uri=http://localhost:8080/auth/google/callback" +
//                        "&grant_type=authorization_code";
//
//        return webClient
//                .post()
//                .uri("https://oauth2.googleapis.com/token")
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .bodyValue(tokenRequestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .flatMap(tokenResponse -> {
//
//                    try {
//                        JsonNode json = objectMapper.readTree(tokenResponse);
//                        String accessToken = json.get("access_token").asText();
//
//                        return webClient
//                                .get()
//                                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
//                                .header("Authorization", "Bearer " + accessToken)
//                                .retrieve()
//                                .bodyToMono(String.class);
//
//                    } catch (Exception e) {
//                        return redirectError(exchange, "token_parse_error");
//                    }
//
//                })
//                .flatMap(userInfo -> {
//
//                    try {
//                        JsonNode userJson = objectMapper.readTree(userInfo.toString());
//
//                        String email = userJson.get("email").asText();
//                        String name = userJson.get("name").asText();
//
//                        return webClient
//                                .post()
//                                .uri("http://localhost:8081/users/oauth?email={email}&name={name}", email, name)
//                                .header("X-Internal-Call", "true")
//                                .retrieve()
//                                .bodyToMono(Void.class)
//                                .then(Mono.defer(() -> {
//
//                                    String jwt = jwtUtil.generateToken(email);
//
//                                    String redirectUrl =
//                                            "http://localhost:3000/oauth2/redirect?token=" + jwt +
//                                                    "&email=" + email;
//
//                                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
//                                    exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));
//
//                                    return exchange.getResponse().setComplete();
//                                }));
//
//                    } catch (Exception e) {
//                        return redirectError(exchange, "user_parse_error");
//                    }
//                })
//                .onErrorResume(e -> redirectError(exchange, "oauth_failed"));
//    }
//
//    private Mono<Void> redirectError(ServerWebExchange exchange, String error) {
//
//        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
//        exchange.getResponse().getHeaders().setLocation(
//                URI.create("http://localhost:3000/login?error=" + error)
//        );
//        return exchange.getResponse().setComplete();
//    }
//}

package com.apigateway.controller;

import com.apigateway.security.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class GoogleOAuthController {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    private final JwtUtil jwtUtil;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoogleOAuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.webClient = WebClient.builder().build();
    }

    // Step 1: Redirect to Google
    @GetMapping("/auth/google")
    public Mono<Void> googleLogin(ServerWebExchange exchange) {

        String googleAuthUrl =
                "https://accounts.google.com/o/oauth2/v2/auth"
                        + "?client_id=" + clientId
                        + "&redirect_uri=http://localhost:8080/auth/google/callback"
                        + "&response_type=code"
                        + "&scope=email%20profile"
                        + "&access_type=offline"
                        + "&prompt=select_account";

        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(googleAuthUrl));
        return exchange.getResponse().setComplete();
    }

    // Step 2: Handle Google Callback
    @GetMapping("/auth/google/callback")
    public Mono<Void> googleCallback(@RequestParam("code") String code,
                                     ServerWebExchange exchange) {

        String tokenRequestBody =
                "code=" + code +
                        "&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&redirect_uri=http://localhost:8080/auth/google/callback" +
                        "&grant_type=authorization_code";

        return webClient
                .post()
                .uri("https://oauth2.googleapis.com/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(tokenRequestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(tokenResponse -> {
                    try {
                        JsonNode json = objectMapper.readTree(tokenResponse);
                        String accessToken = json.get("access_token").asText();

                        return webClient
                                .get()
                                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                                .header("Authorization", "Bearer " + accessToken)
                                .retrieve()
                                .bodyToMono(String.class);

                    } catch (Exception e) {
                        System.err.println("❌ Token parse error: " + e.getMessage());
                        return redirectError(exchange, "token_parse_error");
                    }
                })
                .flatMap((userInfo) -> {
                    try {
                        JsonNode userJson = objectMapper.readTree(userInfo.toString());
                        String email = userJson.get("email").asText();
                        String name  = userJson.get("name").asText();

                        System.out.println("✅ Google user fetched - Email: " + email + ", Name: " + name);

                        return webClient
                                .post()
                                .uri("http://localhost:8081/users/oauth?email={email}&name={name}", email, name)
                                .header("X-Internal-Call", "true")
                                .retrieve()
                                .bodyToMono(Void.class)
                                .onErrorResume(err -> {
                                    // User may already exist — not a fatal error, continue
                                    System.err.println("⚠️ user-service /oauth failed (user may exist): " + err.getMessage());
                                    return Mono.empty();
                                })
                                .then(Mono.defer(() -> {
                                    String jwt = jwtUtil.generateToken(email);

                                    String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
                                    String redirectUrl = "http://localhost:3000/oauth2/redirect"
                                            + "?token=" + jwt
                                            + "&email=" + encodedEmail;

                                    System.out.println("✅ Redirecting to frontend: " + redirectUrl);

                                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                    exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));
                                    return exchange.getResponse().setComplete();
                                }));

                    } catch (Exception e) {
                        System.err.println("❌ User parse error: " + e.getMessage());
                        return redirectError(exchange, "user_parse_error");
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("❌ OAuth flow error: " + e.getMessage());
                    return redirectError(exchange, "oauth_failed");
                });
    }

    private Mono<Void> redirectError(ServerWebExchange exchange, String error) {
        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(
                URI.create("http://localhost:3000/login?error=" + error)
        );
        return exchange.getResponse().setComplete();
    }
}