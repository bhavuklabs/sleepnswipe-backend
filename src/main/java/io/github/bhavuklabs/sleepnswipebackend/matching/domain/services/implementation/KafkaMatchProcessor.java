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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class KafkaMatchProcessor {

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
    public void processMatchCandidates(UUID uuid) {
        var sourceUser = this.userRepository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SwipeHistory> rightSwipesOnUser = this.swipeHistoryRepository.findByTargetProfileAndSwipeType(sourceUser,SwipeHistory.SwipeType.RIGHT);

        for(SwipeHistory swipeHistory : rightSwipesOnUser) {
            User potentialMatchUser = swipeHistory.getUser();
            boolean mutualSwipe = swipeHistoryRepository.existsByUserAndTargetProfileAndSwipeType(potentialMatchUser, sourceUser, SwipeHistory.SwipeType.RIGHT);

            if(mutualSwipe) {
                createMatch(sourceUser, potentialMatchUser);
            }
        }
    }

    private void createMatch(User sourceUser, User potentialMatchUser) {
        UserMatch match = new UserMatch();
        match.setUser1(sourceUser);
        match.setUser2(potentialMatchUser);

        match.setMatchedTimestamp(LocalDateTime.now());
        match.setStatus(UserMatch.MatchStatus.ACTIVE);

        match.setCompatibilityScore(0);
        match.setSentimentMatchScore(0);
        match.setHealthMatchScore(0);
        this.userMatchRepository.save(match);
    }


}
