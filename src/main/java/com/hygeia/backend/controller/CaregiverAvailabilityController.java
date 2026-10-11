package com.hygeia.backend.controller;

import com.hygeia.backend.model.CaregiverAvailability;
import com.hygeia.backend.repository.CaregiverAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caregivers")
@CrossOrigin(origins = "*") // This is crucial: it allows your React app to talk to this API later
public class CaregiverAvailabilityController {

    @Autowired
    private CaregiverAvailabilityRepository availabilityRepository;

    @GetMapping("/{id}/availability")
    public List<CaregiverAvailability> getCaregiverAvailability(@PathVariable Long id) {
        return availabilityRepository.findByCaregiverId(id);
    }
}