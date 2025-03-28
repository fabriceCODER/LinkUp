package com.fabish.LinkUpAPI.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.fabish.LinkUpAPI.repository")
@EntityScan(basePackages = "com.fabish.LinkUpAPI.entity")
public class JpaConfig {
    // Additional JPA configurations
}