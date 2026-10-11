package com.hygeia.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Grab the "Authorization" header from the incoming web request
        String header = request.getHeader("Authorization");

        // 2. Check if the header exists and starts with "Bearer " (standard JWT format)
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Strip off the "Bearer " prefix

            // 3. Check if the token is valid and not expired
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);

                // 4. Create an official Spring Security VIP pass for this request
                UsernamePasswordAuthenticationToken auth = 
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                
                // 5. Hand the VIP pass to Spring Security Context
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 6. Let the request continue to the Controller
        filterChain.doFilter(request, response);
    }
}