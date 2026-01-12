package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private BigDecimal weight;

    // example: WEIGHT_LOSS, MUSCLE_GAIN, GENERAL_HEALTH
    private String fitnessGoal;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
