package com.userservice.controller;

import com.userservice.model.User;
import com.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        String result = userService.register(user);
        return ResponseEntity.ok(Map.of("message", result));
    }

    // Login - NOW RETURNS JSON WITH TOKEN
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        String token = userService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", user.getEmail()
        ));
    }

    // OAuth Save
    @PostMapping("/oauth")
    public User saveOAuth(@RequestParam String email,
                          @RequestParam String name,
                          @RequestHeader(value = "X-Internal-Call", required = false) String internalHeader) {

        if (!"true".equals(internalHeader)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only Used by Google Authentication"
            );
        }

        return userService.saveOAuthUser(email, name);
    }
}