package io.github.bhavuklabs.sleepnswipebackend.matching.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="user_matches")
public class UserMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id_1")
    private User user1;

    @ManyToOne
    @JoinColumn(name="user_id_2")
    private User user2;

    @Column(name="compatibility_score", precision=5)
    private double compatibilityScore;

    @Column(name="sentiment_match_score", precision=5)
    private double sentimentMatchScore;

    @Column(name="health_match_score", precision=5)
    private double healthMatchScore;

    @Column(name="matched_timestamp")
    private LocalDateTime matchedTimestamp;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    public UserMatch() {

    }

    public enum MatchStatus {
        ACTIVE, EXPIRED, BLOCKED
    }

    public UserMatch(UUID id, User user1, User user2, double compatibilityScore, double sentimentMatchScore, double healthMatchScore, LocalDateTime matchedTimestamp, MatchStatus status) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.compatibilityScore = compatibilityScore;
        this.sentimentMatchScore = sentimentMatchScore;
        this.healthMatchScore = healthMatchScore;
        this.matchedTimestamp = matchedTimestamp;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public double getCompatibilityScore() {
        return compatibilityScore;
    }

    public void setCompatibilityScore(double compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
    }

    public double getSentimentMatchScore() {
        return sentimentMatchScore;
    }

    public void setSentimentMatchScore(double sentimentMatchScore) {
        this.sentimentMatchScore = sentimentMatchScore;
    }

    public double getHealthMatchScore() {
        return healthMatchScore;
    }

    public void setHealthMatchScore(double healthMatchScore) {
        this.healthMatchScore = healthMatchScore;
    }

    public LocalDateTime getMatchedTimestamp() {
        return matchedTimestamp;
    }

    public void setMatchedTimestamp(LocalDateTime matchedTimestamp) {
        this.matchedTimestamp = matchedTimestamp;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }
}
