package com.userservice.controller;

import com.userservice.model.User;
import com.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    // OAuth Save
    @PostMapping("/oauth")
    public User saveOAuth(@RequestParam String email, @RequestParam String name, @RequestHeader(value = "X-Internal-Call", required = false) String internalHeader) {

        if (!"true".equals(internalHeader)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only Used by Google Authentication"
            );
        }

        return userService.saveOAuthUser(email, name);
    }
}
