package io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchDomain {
    private UUID matchId;
    private UUID user1Id;
    private UUID user2Id;
    private double compatibilityScore;
    private double sentimentMatchScore;
    private double healthMatchScore;
    private LocalDateTime matchedTimestamp;
    private UserMatch.MatchStatus status;
    private String user1PersonalityType;
    private String user2PersonalityType;

    // Constructor

    // Getters and setters
}