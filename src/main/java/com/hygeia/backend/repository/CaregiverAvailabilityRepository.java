package com.hygeia.backend.repository;

import com.hygeia.backend.model.CaregiverAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CaregiverAvailabilityRepository extends JpaRepository<CaregiverAvailability, Long> {
    
    // Spring Boot magically writes the SQL for this based on the method name!
    List<CaregiverAvailability> findByCaregiverId(Long caregiverId);
}