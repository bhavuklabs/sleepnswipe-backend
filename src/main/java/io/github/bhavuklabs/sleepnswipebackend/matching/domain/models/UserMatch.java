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

    @Column(name="compatibility_score", precision=5, scale=2)
    private double compatibilityScore;

    @Column(name="sentiment_match_score", precision=5, scale=2)
    private double sentimentMatchScore;

    @Column(name="health_match_score", precision=5, scale=2)
    private double healthMatchScore;

    @Column(name="matched_timestamp")
    private LocalDateTime matchedTimestamp;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    public enum MatchStatus {
        ACTIVE, EXPIRED, BLOCKED
    }
}
