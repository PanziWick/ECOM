package com.example.ecommercedemo.Controller;

import com.example.ecommercedemo.DTO.ApiResponse;
import com.example.ecommercedemo.Models.Product;
import com.example.ecommercedemo.Models.User;
import com.example.ecommercedemo.Repositories.UserRepository;
import com.example.ecommercedemo.Services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userServiceImp.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(200, "User retrieved", users));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the users", new ArrayList<>()));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse<List<User>>> createUser(@RequestBody User user) {
        try {
            return userServiceImp.addNewUser(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the user", new ArrayList<>()));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<List<User>>> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            return userServiceImp.updateUser(user,id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the user", new ArrayList<>()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<List<Void>>> deleteUser(@PathVariable Long id) {
        try {
            return userServiceImp.deleteUser(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the user", new ArrayList<>()));
        }
    }
}

