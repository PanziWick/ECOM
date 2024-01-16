package com.example.ecommercedemo.Services;

import com.example.ecommercedemo.DTO.ApiResponse;
import com.example.ecommercedemo.DTO.UserRegistrationDTO;
import com.example.ecommercedemo.Models.User;
import com.example.ecommercedemo.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByID(Long id){
        return userRepository.findById(id);
    }

    public ResponseEntity<ApiResponse<List<User>>> addNewUser (User user){
        try {
            if(isUserDuplicate(user.getUsername())){
                return ResponseEntity.badRequest().body(new ApiResponse<>(400,"User with same name already Exists",new ArrayList<>()));
            }
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>(201,"User Added",new ArrayList<>()));

        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"server error",new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<User>>> updateUser(User updateduser,Long id){
        try{
            Optional<User> existingUserOptional = userRepository.findById(id);
            if(existingUserOptional.isPresent()){
                User existingUser = existingUserOptional.get();

                existingUser.setUsername(updateduser.getUsername());
                existingUser.setEmail(updateduser.getEmail());
                existingUser.setRole(updateduser.getRole()) ;

                userRepository.save(existingUser);
                return ResponseEntity.ok(new ApiResponse<>(200, "User updated successfully", new ArrayList<>()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "User not found", new ArrayList<>()));
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"server error",new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Void>>> deleteUser(Long id){
        try{
            Optional<User> exisitingUserOptional = userRepository.findById(id);
            if(exisitingUserOptional.isPresent()){
                User user = exisitingUserOptional.get();
                userRepository.deleteById(id);
                return ResponseEntity.ok(new ApiResponse<>(200, "User Deleted successfully", new ArrayList<>()));
            }else{
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "User not found", new ArrayList<>()));
            }

        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while deleting the user", new ArrayList<>()));
        }
    }

    private boolean isUserDuplicate(String productName) {
        return userRepository.existsByEmail(productName);
    }

    @Override
    public org.springframework.security.core.userdetails.User register(UserRegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public void verifyAccount(String verificationCode) {

    }

    @Override
    public org.springframework.security.core.userdetails.User findByUsername(String username) {
        return null;
    }

    @Override
    public org.springframework.security.core.userdetails.User findByEmail(String email) {
        return null;
    }

    @Override
    public void updateProfile(org.springframework.security.core.userdetails.User user) {

    }

    @Override
    public void initiatePasswordReset(String email) {

    }

    @Override
    public void completePasswordReset(String token, String newPassword) {

    }
}
