package com.apigateway.controller;

import com.apigateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    // LOGIN → CALL USER SERVICE → GENERATE TOKEN
    @PostMapping("/login")
    public Mono<String> login(@RequestParam String email,
                              @RequestParam String password) {

        return webClient
                .post()
                .uri("/users/login")
                .bodyValue(Map.of("email", email, "password", password))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> jwtUtil.generateToken(email));
    }
}