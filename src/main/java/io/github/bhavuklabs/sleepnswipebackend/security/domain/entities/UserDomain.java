package io.github.bhavuklabs.sleepnswipebackend.security.domain.entities;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.GenderEnum;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile.BloodType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDomain(
        UUID id,
        String email,
        String username,
        String firstName,
        String lastName,
        LocalDateTime dateOfBirth,
        GenderEnum gender,
        LocalDateTime createdAt,
        LocalDateTime lastLogin,

        String profileId,
        BigDecimal heightCm,
        BigDecimal weightKg,
        BloodType bloodType,
        String medicalConditions,
        String allergies,
        BigDecimal bmi,
        LocalDateTime profileCreatedAt
) {
}