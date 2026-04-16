package com.app.quantitymeasurement.security;

import java.util.Collection;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.quantitymeasurement.model.AppUser;

public class CustomUserDetails implements UserDetails {

	private final AppUser user;

	public CustomUserDetails(AppUser user) {
		this.user = user;
	}

	// Return roles/authorities (for now empty)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	// Return password
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	// Return username (IMPORTANT)
	@Override
	public String getUsername() {
		// If using email as username
		return user.getEmail();

		// OR if using username field
		// return user.getUsername();
	}

	// Account status checks (all true for now)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// Optional: expose AppUser if needed
	public AppUser getUser() {
		return user;
	}
}