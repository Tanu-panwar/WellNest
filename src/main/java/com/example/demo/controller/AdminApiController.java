package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.TrainerProfileRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wellnest.com/admin")
public class AdminApiController {


    @GetMapping("/all-data")
    public String viewAllData() {
        return "Admin has access to everything";
    }
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TrainerProfileRepository trainerProfileRepository;
    
 // ✅ View all users
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ View all user profiles
    @GetMapping("/all-user-profiles")
    public ResponseEntity<?> getAllUserProfiles() {
        return ResponseEntity.ok(userProfileRepository.findAll());
    }

    // ✅ View all trainer profiles
    @GetMapping("/all-trainer-profiles")
    public ResponseEntity<?> getAllTrainerProfiles() {
        return ResponseEntity.ok(trainerProfileRepository.findAll());
    }

    // ✅ Delete user (and related profiles)
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userProfileRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(id))
                .forEach(p -> userProfileRepository.delete(p));

        trainerProfileRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(id))
                .forEach(p -> trainerProfileRepository.delete(p));

        userRepository.deleteById(id);

        return ResponseEntity.ok("User deleted successfully");
    }

}
