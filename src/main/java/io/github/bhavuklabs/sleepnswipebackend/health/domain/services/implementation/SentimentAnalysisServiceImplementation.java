package io.github.bhavuklabs.sleepnswipebackend.health.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.SentimentRecord;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentAnalysis;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentAnalysisRepository;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentAnalysisService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisServiceImplementation extends SentimentAnalysisService {
    private final SentimentAnalysisRepository sentimentAnalysisRepository;

    public SentimentAnalysisServiceImplementation(SentimentAnalysisRepository sentimentAnalysisRepository) {
        this.sentimentAnalysisRepository = sentimentAnalysisRepository;
    }

    @Transactional
    public SentimentAnalysis saveSentimentAnalysis(User user, SentimentRecord sentimentRecord) {
        Map<String, Double> sentimentMap = sentimentRecord.sentimentsMap().stream()
                .collect(Collectors.toMap(SentimentRecord.SentimentDomain::getSentiment, SentimentRecord.SentimentDomain::getValue));

        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        sentimentAnalysis.setId(UUID.randomUUID());
        sentimentAnalysis.setUser(user);
        sentimentAnalysis.setScores(sentimentMap);
        sentimentAnalysis.setAnalysisTimestamp(LocalDateTime.now());

        return sentimentAnalysisRepository.save(sentimentAnalysis);
    }
}
