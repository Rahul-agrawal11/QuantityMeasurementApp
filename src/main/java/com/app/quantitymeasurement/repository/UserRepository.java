package com.app.quantitymeasurement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.quantitymeasurement.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByEmail(String email);
}
