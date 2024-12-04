package io.github.bhavuklabs.sleepnswipebackend.exquisite.controllers;

import io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.entities.SentimentProfileDomain;
import io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities.DashboardDetails;
import io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities.DashboardRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities.MatchDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentProfile;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentAnalysisRepository;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentProfileRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.MatchPreferenceRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.UserMatchRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DashboardController {

    private final UserService userService;
    private final UserMatchRepository userMatchRepository;
    private final SentimentProfileRepository sentimentProfileRepository;
    private final MatchPreferenceRepository matchPreferenceRepository;
    private final SentimentAnalysisRepository sentimentAnalysisRepository;

    public DashboardController(UserService userService, UserMatchRepository userMatchRepository, SentimentProfileRepository sentimentProfileRepository, MatchPreferenceRepository matchPreferenceRepository, SentimentAnalysisRepository sentimentAnalysisRepository) {
        this.userService = userService;
        this.userMatchRepository = userMatchRepository;
        this.sentimentProfileRepository = sentimentProfileRepository;
        this.matchPreferenceRepository = matchPreferenceRepository;
        this.sentimentAnalysisRepository = sentimentAnalysisRepository;
    }

    @GetMapping("/user/data")
    public ResponseEntity<DashboardDetails> dashboardDetails(@RequestBody DashboardRequestDomain email) {
        var user = this.userService.findUserByEmail(email.email()).get();
        System.out.println(user);
        if(user == null) {
            throw new UsernameNotFoundException("Username with Corresponding Email Not Found");
        }

        var sentimentProfile = this.sentimentProfileRepository.findSentimentProfileByUser(user).get();
        List<List<Integer>> sleepRecord = new ArrayList<>();
        for(int i=0;i<=5;i++) {
            var list = new ArrayList<Integer>();
            for(int j=0;j<=5;j++) {
                int val = (int)(Math.random()*(9-1)+1);
                list.add(val);
            }
            sleepRecord.add(list);
        }

        DashboardDetails details = new DashboardDetails(sentimentProfile.getOverallSentimentScore(), sentimentProfile.getPersonalityType(), sentimentProfile.getEmotionalStabilityScore(), sentimentProfile.getSocialInteractionScore(), 2, sleepRecord);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/match/profiles")
    public ResponseEntity<List<MatchDomain>> getMatchProfiles(@RequestBody DashboardRequestDomain email) {
        List<MatchDomain> matchProfiles = new ArrayList<>();
        var user = this.userService.findUserByEmail(email.email()).get();
        List<UserMatch> userMatches = userMatchRepository.findUserMatchByMatchStatusAndUser(UserMatch.MatchStatus.ACTIVE, user);

        for (UserMatch match : userMatches) {
            User user1 = match.getUser1();
            User user2 = match.getUser2();

            SentimentProfile sentimentProfile1 = sentimentProfileRepository.findSentimentProfileByUser(user1)
                    .orElse(null);
            SentimentProfile sentimentProfile2 = sentimentProfileRepository.findSentimentProfileByUser(user2)
                    .orElse(null);

            MatchDomain matchDomain = new MatchDomain(
                    match.getId(),
                    user1.getId(),
                    user2.getId(),
                    match.getCompatibilityScore(),
                    match.getSentimentMatchScore(),
                    match.getHealthMatchScore(),
                    match.getMatchedTimestamp(),
                    match.getStatus(),
                    sentimentProfile1 != null ? sentimentProfile1.getPersonalityType() : null,
                    sentimentProfile2 != null ? sentimentProfile2.getPersonalityType() : null
            );

            matchProfiles.add(matchDomain);
        }

        return ResponseEntity.ok(matchProfiles);
    }


}
