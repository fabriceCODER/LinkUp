package com.fabish.LinkUpAPI.repository;

import com.fabish.LinkUpAPI.entity.Role;
import com.fabish.LinkUpAPI.entity.User; // Adjust package if your User entity is elsewhere
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for authentication)
    Optional<User> findByEmail(String email);

    // Find users by role with pagination (for admin functionality)
    Page<User> findByRole(Role role, Pageable pageable);

    // Check if email already exists (for registration validation)
    boolean existsByEmail(String email);
}