package com.hygeia.backend.repository;

import com.hygeia.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Find all appointments where the user is the patient
    List<Appointment> findByPatientId(Long patientId);
    
    // Find all appointments where the user is the caregiver
    List<Appointment> findByCaregiverId(Long caregiverId);
}