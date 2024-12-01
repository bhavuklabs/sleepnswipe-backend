package io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.services.GenericAIService;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.QuestionnaireRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public interface SentimentScoreGeneration extends GenericAIService<SentimentScoreDomain> {

    public SentimentScoreDomain generateSentimentScores(QuestionnaireRequestDomain questionnaireResponsesDomain) throws URISyntaxException;

}
