package com.example.ecommercedemo.Services;

import com.example.ecommercedemo.DTO.UserRegistrationDTO;
import org.springframework.security.core.userdetails.User;

public interface UserService {
    User register(UserRegistrationDTO registrationDTO);
    void verifyAccount(String verificationCode);
    User findByUsername(String username);
    User findByEmail(String email);
    void updateProfile(User user);
    void initiatePasswordReset(String email);
    void completePasswordReset(String token, String newPassword);
}
