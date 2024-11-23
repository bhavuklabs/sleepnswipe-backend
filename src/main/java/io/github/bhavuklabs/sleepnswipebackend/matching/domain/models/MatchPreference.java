package io.github.bhavuklabs.sleepnswipebackend.matching.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.GenderEnum;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="match_preferences")
public class MatchPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="min_age")
    private int minAge;

    @Column(name="max_age")
    private int maxAge;

    @Column(name="preferred_gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum preferredGender;

    @Column(name="max_distance_km")
    private int maxDistanceKm;

    @Column(name="sleep_compatibility_weight", precision=5, scale=2)
    private double sleepCompatibilityWeight;

    @Column(name="sentiment_compatibility_weight", precision=5, scale=2)
    private double sentimentCompatibilityWeight;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
