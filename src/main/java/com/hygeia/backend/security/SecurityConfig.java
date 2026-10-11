package com.hygeia.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF because our React frontend will use tokens, not cookies
            .csrf(csrf -> csrf.disable())
            
            // Tell Spring Security not to save sessions on the server (make it stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Define the rules for who can access which URLs
            .authorizeHttpRequests(auth -> auth
                // We will build this auth endpoint next so users can actually log in
                .requestMatchers("/api/auth/**").permitAll() 
                // Every other endpoint (appointments, availability, etc.) is strictly locked
                .anyRequest().authenticated() 
            )
            
            // Place our custom JWT filter right at the front door
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}