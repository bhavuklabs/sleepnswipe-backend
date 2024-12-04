package io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentProfile;

import java.util.List;

public record DashboardDetails(
        double overallSentimentScore,
        String personalityType,
        double emotionalStabilityScore,
        double socialInteractionScore,
        int matchNumbers,
        List<List<Integer>> sleepRecord
) {
}
