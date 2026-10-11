package com.hygeia.backend.controller;

import com.hygeia.backend.model.Appointment;
import com.hygeia.backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long id, 
            @RequestParam Appointment.AppointmentStatus status) {
        
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        
        if (appointmentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Appointment appointment = appointmentOptional.get();
        appointment.setStatus(status);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        return ResponseEntity.ok(savedAppointment);
    }
}