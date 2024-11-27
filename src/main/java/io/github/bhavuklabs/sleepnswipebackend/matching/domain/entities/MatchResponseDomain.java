package io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;

import java.time.LocalDateTime;
import java.util.UUID;

public record MatchResponseDomain(
        UUID matchId,
        UUID user1Id,
        UUID user2Id,
        double compatibilityScore,
        double sentimentMatchScore,
        double healthMatchScore,
        LocalDateTime matchedTimestamp,
        UserMatch.MatchStatus status
) {
}
