package io.github.bhavuklabs.sleepnswipebackend.health.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="sentiment_profiles")
public class SentimentProfile {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="overall_sentiment_score", precision=5, scale=2)
    private double overallSentimentScore;

    @Column(name="personality_type", length=50)
    private String personalityType;

    @Column(name="emotional_stability_score", precision=5, scale=2)
    private double emotionalStabilityScore;


    @Column(name="social_interaction_score", precision=5, scale=2)
    private double socialInteractionScore;

    @Column(name="last_analysis_date")
    private LocalDateTime lastAnalysisDate;

}
