package com.hygeia.backend.repository;

import com.hygeia.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Add this new method:
    Optional<User> findByEmail(String email);
}