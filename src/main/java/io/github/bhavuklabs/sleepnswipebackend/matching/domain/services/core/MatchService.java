package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.MatchResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface MatchService {

    List<MatchResponseDomain> findMatchCandidates(UUID userId);
    double calculateCompatibility(UUID userId, UUID candidateId);
    List<MatchResponseDomain> rankMatchCandidates(UUID userId, List<User> candidates);
}
