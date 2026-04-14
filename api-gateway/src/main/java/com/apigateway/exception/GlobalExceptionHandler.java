package com.apigateway.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccess(AccessDeniedException ex) {
        return ResponseEntity.status(403).body("Access Denied!!!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failed!!!");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong Credentials!!!");
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<?> handleOAuth(OAuth2AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login from OAuth Failed!!!");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJWT(ExpiredJwtException ex) {
        return ResponseEntity.status(401).body("Token Expired!!!");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJWT(MalformedJwtException ex) {
        return ResponseEntity.status(401).body("Invalid Token!!!");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignature(SignatureException ex) {
        return ResponseEntity.status(401).body("Invalid Signature!!!");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handelUser(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!!!");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGlobal(RuntimeException ex) {
        return ResponseEntity.status(500).body("Something went wrong: " + ex.getMessage());
    }
}
