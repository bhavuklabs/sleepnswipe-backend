package io.github.bhavuklabs.sleepnswipebackend.health.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="user_health_profiles")
public class HealthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="height_cm")
    private int heightCm;

    @Column(name="weight_kg")
    private int weightKg;

    @Column(name="blood_type")
    @Enumerated(EnumType.STRING)
    private UserProfile.BloodType bloodType;

    @Column(name="medical_conditions")
    private String medicalConditions;

    @Column(name="bmi", precision = 5)
    private double bmi;

    @Column(name="created_at")
    private LocalDateTime createdAt;

}
