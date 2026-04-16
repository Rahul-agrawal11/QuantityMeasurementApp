package com.userservice.service;

import com.userservice.config.JwtUtil;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(User postUser) {
        User user = new User();
        user.setEmail(postUser.getEmail());
        user.setPassword(passwordEncoder.encode(postUser.getPassword()));
        user.setName(postUser.getName());
        user.setProvider("LOCAL");

        userRepository.save(user);

        return "User Registered Successfully!!!";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token instead of returning plain text
        return jwtUtil.generateToken(email);
    }

    public User saveOAuthUser(String email, String name) {
        return userRepository.findByEmail(email).orElseGet(()->{
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setProvider("GOOGLE");
            return userRepository.save(user);
        });
    }
}
