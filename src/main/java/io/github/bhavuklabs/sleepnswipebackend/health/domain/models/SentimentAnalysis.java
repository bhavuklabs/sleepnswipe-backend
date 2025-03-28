package io.github.bhavuklabs.sleepnswipebackend.health.domain.models;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.utilities.SentimentLoader;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sentiment_analysis")
public class SentimentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name="sentiment_scores", joinColumns = @JoinColumn(name="sentiment_id"))
    @MapKeyColumn(name="sentiment_name")
    @Column(name="sentiment_score")
    private Map<String, Double> scores;



    @Column(name="analysis_timestamp")
    private LocalDateTime analysisTimestamp;
}
