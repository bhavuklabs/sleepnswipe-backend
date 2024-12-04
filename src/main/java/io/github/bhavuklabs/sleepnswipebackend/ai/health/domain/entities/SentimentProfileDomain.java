package io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.entities;

public record SentimentProfileDomain(
        double overallSentimentScore,
        String personalityType,
        double emotionalStabilityScore,
        double socialInteractionScore
) {
}
