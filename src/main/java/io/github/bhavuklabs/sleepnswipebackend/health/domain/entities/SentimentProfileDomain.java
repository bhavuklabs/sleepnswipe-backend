package io.github.bhavuklabs.sleepnswipebackend.health.domain.entities;

public record SentimentProfileDomain(
        String email,
        String overallSentimentScore,
        String personalityType,
        double emotionalStabilityScore,
        double socialInteractionScore
) {
}
