package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/wellnest.com/user")
public class UserApiController {

    // ---------------- FREE APIs FOR USER ----------------

    @GetMapping("/free-sessions")
    public String viewFreeSessions() {
        return "Free workout sessions visible to USER";
    }

    @GetMapping("/schedule")
    public String viewSchedule() {
        return "Session schedule visible to USER";
    }

    // ---------------- PROFILE APIs ----------------

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    // ✅ CREATE or UPDATE PROFILE
    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(
            @RequestBody UserProfile profile,
            Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile existingProfile = userProfileRepository.findByUser(user).orElse(null);

        if (existingProfile != null) {
            existingProfile.setAge(profile.getAge());
            existingProfile.setWeight(profile.getWeight());
            existingProfile.setFitnessGoal(profile.getFitnessGoal());
            userProfileRepository.save(existingProfile);
            return ResponseEntity.ok("Profile updated successfully");
        }

        profile.setUser(user);
        userProfileRepository.save(profile);
        return ResponseEntity.ok("Profile created successfully");
    }

    //  VIEW OWN PROFILE
    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return ResponseEntity.ok(profile);
    }
}
