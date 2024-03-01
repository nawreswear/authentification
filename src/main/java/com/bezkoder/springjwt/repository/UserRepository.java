package com.bezkoder.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.springjwt.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByNom(String nom);

	Boolean existsByEmail(String email);

	User findByEmail(String email);
	boolean existsByType(String type);


	default boolean existsAdmin() {
		return existsByType("admin");
	}

}
