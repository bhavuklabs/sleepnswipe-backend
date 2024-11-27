package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.MatchResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.SwipeHistoryRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.MatchService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchServiceImplementation implements MatchService {

    private final UserRepository userRepository;
    private final SwipeHistoryRepository swipeHistoryRepository;

    public MatchServiceImplementation(UserRepository userRepository, SwipeHistoryRepository swipeHistoryRepository) {
        this.userRepository = userRepository;
        this.swipeHistoryRepository = swipeHistoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatchResponseDomain> findMatchCandidates(UUID userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<User> potentialCandidates = userRepository.findUsersNotSwipedByUser(userId);
        List<User> filteredCandidates = filterCandidates(currentUser, potentialCandidates);
        return rankMatchCandidates(userId, filteredCandidates);
    }

    @Override
    public double calculateCompatibility(UUID userId, UUID candidateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new NoSuchElementException("Candidate not found"));

        double baseCompatibility = calculateBaseCompatibility(user, candidate);
        double sentimentScore = calculateSentimentMatchScore(userId, candidateId);
        double healthScore = calculateHealthMatchScore(userId, candidateId);

        return (baseCompatibility * 0.5) + (sentimentScore * 0.3) + (healthScore * 0.2);
    }

    @Override
    public List<MatchResponseDomain> rankMatchCandidates(UUID userId, List<User> candidates) {
        return candidates.stream()
                .map(candidate -> generateMatchResponse(userId, candidate))
                .sorted(Comparator.comparing(MatchResponseDomain::compatibilityScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private MatchResponseDomain generateMatchResponse(UUID userId, User candidate) {
        double compatibilityScore = calculateCompatibility(userId, candidate.getId());

        return new MatchResponseDomain(
                UUID.randomUUID(),
                userId,
                candidate.getId(),
                compatibilityScore,
                calculateSentimentMatchScore(userId, candidate.getId()),
                calculateHealthMatchScore(userId, candidate.getId()),
                LocalDateTime.now(),
                UserMatch.MatchStatus.ACTIVE
        );
    }

    private List<User> filterCandidates(User currentUser, List<User> potentialCandidates) {
        return potentialCandidates.stream()
                .filter(candidate ->
                        isWithinAgeRange(currentUser, candidate) &&
                                isWithinLocationPreference(currentUser, candidate) &&
                                isGenderCompatible(currentUser, candidate)
                )
                .collect(Collectors.toList());
    }

    private boolean isWithinAgeRange(User currentUser, User candidate) {
        int userAge = calculateAge(currentUser.getDateOfBirth().toLocalDate());
        int candidateAge = calculateAge(candidate.getDateOfBirth().toLocalDate());
        return Math.abs(userAge - candidateAge) <= 5;
    }

    private boolean isWithinLocationPreference(User user, User candidate) {
        double distance = calculateDistance(
                user.getLatitude(), user.getLongitude(),
                candidate.getLatitude(), candidate.getLongitude()
        );
        return distance <= 100;
    }

    private boolean isGenderCompatible(User user, User candidate) {
        return user.getGender() == candidate.getGender();
    }

    private double calculateBaseCompatibility(User user, User candidate) {
        double ageCompatibility = calculateAgeProximityScore(user, candidate);
        double genderCompatibility = calculateGenderCompatibility(user, candidate);

        return (ageCompatibility + genderCompatibility) / 2.0;
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private double calculateAgeProximityScore(User user, User candidate) {
        int ageDifference = Math.abs(calculateAge(user.getDateOfBirth().toLocalDate()) -
                calculateAge(candidate.getDateOfBirth().toLocalDate()));
        return Math.max(0, 1.0 - (ageDifference / 10.0));
    }

    private double calculateGenderCompatibility(User user, User candidate) {
        return user.getGender() == candidate.getGender() ? 1.0 : 0.5;
    }

    private double calculateSentimentMatchScore(UUID userId, UUID candidateId) {
        // Placeholder for more complex sentiment matching logic
        return 0.75;
    }

    private double calculateHealthMatchScore(UUID userId, UUID candidateId) {
        // Placeholder for more complex health compatibility logic
        return 0.85;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}