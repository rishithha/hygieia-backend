package com.hygeia.backend.controller;

import com.hygeia.backend.model.User;
import com.hygeia.backend.repository.UserRepository;
import com.hygeia.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 1. Check if the user exists in the database
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            // For this MVP, we are keeping it simple and just checking if the email exists.
            // In a production app, you would also verify the password using BCrypt here!
            
            // 2. Generate the JWT token
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            
            // 3. Send the token back to the React frontend
            return ResponseEntity.ok(token);
        }

        // Return a 401 Unauthorized if the user isn't found
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}

// Helper class to catch the incoming JSON from React
class LoginRequest {
    private String email;
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}