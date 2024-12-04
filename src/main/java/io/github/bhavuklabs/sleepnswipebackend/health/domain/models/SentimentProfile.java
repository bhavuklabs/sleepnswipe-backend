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

    public SentimentProfile(UUID id, User user, double overallSentimentScore, String personalityType, double emotionalStabilityScore, double socialInteractionScore, SentimentAnalysis sentimentAnalysis, LocalDateTime lastAnalysisDate) {
        this.id = id;
        this.user = user;
        this.overallSentimentScore = overallSentimentScore;
        this.personalityType = personalityType;
        this.emotionalStabilityScore = emotionalStabilityScore;
        this.socialInteractionScore = socialInteractionScore;
        this.sentimentAnalysis = sentimentAnalysis;
        this.lastAnalysisDate = lastAnalysisDate;
    }

    @Column(name="overall_sentiment_score", precision=5)
    private double overallSentimentScore;

    @Column(name="personality_type", length=50)
    private String personalityType;

    @Column(name="emotional_stability_score", precision=5)
    private double emotionalStabilityScore;


    @Column(name="social_interaction_score", precision=5)
    private double socialInteractionScore;

    @OneToOne
    @JoinColumn(name="sentiment_id")
    private SentimentAnalysis sentimentAnalysis;

    @Column(name="last_analysis_date")
    private LocalDateTime lastAnalysisDate;

    public SentimentProfile() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getOverallSentimentScore() {
        return overallSentimentScore;
    }

    public void setOverallSentimentScore(double overallSentimentScore) {
        this.overallSentimentScore = overallSentimentScore;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public double getEmotionalStabilityScore() {
        return emotionalStabilityScore;
    }

    public void setEmotionalStabilityScore(double emotionalStabilityScore) {
        this.emotionalStabilityScore = emotionalStabilityScore;
    }

    public double getSocialInteractionScore() {
        return socialInteractionScore;
    }

    public void setSocialInteractionScore(double socialInteractionScore) {
        this.socialInteractionScore = socialInteractionScore;
    }

    public SentimentAnalysis getSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public void setSentimentAnalysis(SentimentAnalysis sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public LocalDateTime getLastAnalysisDate() {
        return lastAnalysisDate;
    }

    public void setLastAnalysisDate(LocalDateTime lastAnalysisDate) {
        this.lastAnalysisDate = lastAnalysisDate;
    }
}
