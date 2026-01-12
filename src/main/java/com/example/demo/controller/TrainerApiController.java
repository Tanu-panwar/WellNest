package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TrainerProfile;
import com.example.demo.entity.User;
import com.example.demo.repository.TrainerProfileRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wellnest.com/trainer")
public class TrainerApiController {

    @GetMapping("/track-workouts")
    public String trackUserWorkouts() {
        return "Trainer can track user workouts";
    }

    @GetMapping("/track-meals")
    public String trackUserMeals() {
        return "Trainer can track user meals";
    }

    @GetMapping("/track-sleep")
    public String trackUserSleep() {
        return "Trainer can track user sleep";
    }

    @GetMapping("/track-water")
    public String trackUserWater() {
        return "Trainer can track user water intake";
    }
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerProfileRepository trainerProfileRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    
 // -------- TRAINER OWN PROFILE --------

    @PostMapping("/profile")
    public ResponseEntity<?> saveTrainerProfile(
            @RequestBody TrainerProfile profile,
            Principal principal) {

        String username = principal.getName();
        User trainer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        TrainerProfile existing = trainerProfileRepository.findByUser(trainer).orElse(null);

        if (existing != null) {
            existing.setSpecialization(profile.getSpecialization());
            existing.setExperienceYears(profile.getExperienceYears());
            trainerProfileRepository.save(existing);
            return ResponseEntity.ok("Trainer profile updated");
        }

        profile.setUser(trainer);
        trainerProfileRepository.save(profile);
        return ResponseEntity.ok("Trainer profile created");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getTrainerProfile(Principal principal) {

        String username = principal.getName();
        User trainer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        TrainerProfile profile = trainerProfileRepository.findByUser(trainer)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return ResponseEntity.ok(profile);
    }

    // -------- TRAINER VIEW ALL USER PROFILES --------

    @GetMapping("/all-user-profiles")
    public ResponseEntity<?> viewAllUserProfiles() {
        return ResponseEntity.ok(userProfileRepository.findAll());
    }

}
