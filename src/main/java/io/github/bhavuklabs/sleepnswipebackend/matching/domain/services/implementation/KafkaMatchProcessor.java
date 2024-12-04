package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.SwipeHistoryRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.UserMatchRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class KafkaMatchProcessor extends io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.KafkaMatchProcessor {

    private final SwipeHistoryRepository swipeHistoryRepository;
    private final UserMatchRepository userMatchRepository;
    private final UserRepository userRepository;

    @Autowired
    public KafkaMatchProcessor(SwipeHistoryRepository swipeHistoryRepository, UserMatchRepository userMatchRepository, UserRepository userRepository) {
        this.swipeHistoryRepository = swipeHistoryRepository;
        this.userMatchRepository = userMatchRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void processMatchCandidates(UUID uuid) {
        var sourceUser = this.userRepository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SwipeHistory> rightSwipesOnUser = this.swipeHistoryRepository.findByTargetProfileAndSwipeType(sourceUser,SwipeHistory.SwipeType.RIGHT);
        for(SwipeHistory swipeHistory : rightSwipesOnUser) {
            User potentialMatchUser = swipeHistory.getUser();
            boolean mutualSwipe = swipeHistoryRepository.existsByUserAndTargetProfileAndSwipeType(potentialMatchUser, sourceUser, SwipeHistory.SwipeType.RIGHT);

            if(!mutualSwipe) {
                createMatch(sourceUser, potentialMatchUser);
            }
        }
    }

    private void createMatch(User sourceUser, User potentialMatchUser) {
        if (sourceUser.getId().equals(potentialMatchUser.getId())) {
            return; // Or log a warning
        }

        UserMatch match = new UserMatch();

        if (sourceUser.getId().compareTo(potentialMatchUser.getId()) < 0) {
            match.setUser1(sourceUser);
            match.setUser2(potentialMatchUser);
        } else {
            match.setUser1(potentialMatchUser);
            match.setUser2(sourceUser);
        }

        match.setMatchedTimestamp(LocalDateTime.now());
        match.setStatus(UserMatch.MatchStatus.ACTIVE);

        match.setCompatibilityScore(calculateCompatibilityScore(sourceUser, potentialMatchUser));
        match.setSentimentMatchScore(calculateSentimentMatchScore(sourceUser, potentialMatchUser));
        match.setHealthMatchScore(calculateHealthMatchScore(sourceUser, potentialMatchUser));

        this.userMatchRepository.save(match);
    }

    private double calculateCompatibilityScore(User user1, User user2) {
        double baseCompatibility = calculateBaseCompatibility(user1, user2);
        double sentimentScore = calculateSentimentMatchScore(user1, user2);
        double healthScore = calculateHealthMatchScore(user1, user2);

        return (baseCompatibility * 0.5) + (sentimentScore * 0.3) + (healthScore * 0.2);
    }

    private double calculateBaseCompatibility(User user1, User user2) {
        int ageDifference = Math.abs(calculateAge(user1.getDateOfBirth().toLocalDate()) -
                calculateAge(user2.getDateOfBirth().toLocalDate()));
        double ageCompatibility = Math.max(0, 1.0 - (ageDifference / 10.0));

        double genderCompatibility = user1.getGender() == user2.getGender() ? 1.0 : 0.5;

        return (ageCompatibility + genderCompatibility) / 2.0;
    }

    private double calculateSentimentMatchScore(User user1, User user2) {
        return 0.75;
    }

    private double calculateHealthMatchScore(User user1, User user2) {
        return 0.85;
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }


}
