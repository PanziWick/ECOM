package com.example.ecommercedemo.Services;

;
import com.example.ecommercedemo.Models.AuthenticationRequest;
import com.example.ecommercedemo.Models.AuthenticationResponse;
import com.example.ecommercedemo.Models.RegisterRequest;
import com.example.ecommercedemo.Models.User;
import com.example.ecommercedemo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        try {
            // Check if the email already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DataIntegrityViolationException("Email address already in use. Please use a different email.");
            }

            var user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            // Save the user to the database
            userRepository.save(user);

            // Generate JWT token
            var jwtToken = jwtService.generateToken(user);

            // Return a meaningful message indicating successful user creation
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("User successfully registered.")
                    .build();
        } catch (DataIntegrityViolationException e) {
            // Handle data duplication exception
            return AuthenticationResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if(authentication.isAuthenticated())  {
                // Authentication successful
                var user = userRepository.findByEmail(request.getEmail())
                        .orElseThrow(); // You might want to handle the case where the user is not found
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .message("Authentication successful.")
                        .build();
            }
        } catch (AuthenticationException e) {
            // Authentication failed
            return AuthenticationResponse.builder()
                    .message("Authentication failed. Invalid credentials.")
                    .build();
        }
        return null;
    }
}
