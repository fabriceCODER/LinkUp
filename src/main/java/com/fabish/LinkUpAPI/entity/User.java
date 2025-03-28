package com.fabish.LinkUpAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password; // Hashed password for non-OAuth users

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String oauth2Provider; // e.g., "google", "linkedin"
    private String oauth2Id;       // OAuth2 user ID

    @Column(columnDefinition = "TEXT")
    private String skills; // For candidates

    private String resumeUrl; // For candidates
}

