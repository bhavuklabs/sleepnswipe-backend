package io.github.bhavuklabs.sleepnswipebackend.health.domain.mappers;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentAnalysis;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.utilities.SentimentLoader;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SentimentAnalysisMapper {

    private final UserRepository userRepository;

    public SentimentAnalysisMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public SentimentAnalysis fromDto(SentimentScoreDomain domain) {
//        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
//        var domains = domain.getSentimentsMap();
//        Map<String, Double> map = new HashMap<>();
//        for(String sentiment : SentimentLoader.getInstance().setSentimentLoaderMap().getSentimentLoaderMap().keySet()) {
//            map.put(sentiment, )
//        }
//        sentimentAnalysis.setAnalysisTimestamp(LocalDateTime.now());
//
//        return sentimentAnalysis;
//    }

//    public SentimentScoreDomain toDto(SentimentAnalysis sentimentAnalysis) {
//        SentimentScoreDomain domain = new SentimentScoreDomain(sentimentAnalysis.getScores());
//        return domain;
//    }
}
