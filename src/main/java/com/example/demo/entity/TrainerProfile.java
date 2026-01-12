package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "trainer_profiles")
public class TrainerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;   // Yoga, Cardio, Strength
    private int experienceYears;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
