package io.github.bhavuklabs.sleepnswipebackend.health.domain.entities;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile;

public record HealthProfileDomain(
        String email,
        int heightCm,
        int weightKg,
        UserProfile.BloodType bloodType,
        String medicalConditions,
        double bmi
) {
}
