package com.hygeia.backend.repository;

import com.hygeia.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Boot automatically gives you save(), findAll(), findById(), etc.
}