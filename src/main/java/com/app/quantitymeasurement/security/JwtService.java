package com.app.quantitymeasurement.security;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET = "my-super-secret-key-my-super-secret-key-123456";

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes())).compact();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean isValid(String token, UserDetails user) {
		return extractUsername(token).equals(user.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token).getBody();
	}
}