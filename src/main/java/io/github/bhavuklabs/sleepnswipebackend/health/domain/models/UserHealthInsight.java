package io.github.bhavuklabs.sleepnswipebackend.health.domain.models;


import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="user_health_insight")
public class UserHealthInsight {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="insight_type", length=100)
    private String insightType;

    @Column(name="insight_description")
    private String insightDescription;

    @Column(name="recommendation")
    private String recommendation;

    @Column(name="generated_timestamp")
    private LocalDateTime generatedTimestamp;
}
