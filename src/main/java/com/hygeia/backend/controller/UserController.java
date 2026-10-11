package com.hygeia.backend.controller;

import com.hygeia.backend.model.Appointment;
import com.hygeia.backend.model.User;
import com.hygeia.backend.repository.AppointmentRepository;
import com.hygeia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable Long id) {
        // First, find the user in the database to check their role
        Optional<User> userOptional = userRepository.findById(id);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return a 404 if user doesn't exist
        }

        User user = userOptional.get();
        List<Appointment> appointments;

        // Fetch the correct list based on the user's role
        if (user.getRole() == User.Role.PATIENT) {
            appointments = appointmentRepository.findByPatientId(id);
        } else {
            appointments = appointmentRepository.findByCaregiverId(id);
        }

        return ResponseEntity.ok(appointments); // Return a 200 OK with the data
    }
}