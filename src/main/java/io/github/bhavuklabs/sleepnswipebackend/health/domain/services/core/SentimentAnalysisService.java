package io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.SentimentRecord;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentAnalysis;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;

public abstract class SentimentAnalysisService {

    public abstract SentimentAnalysis saveSentimentAnalysis(User user, SentimentRecord sentimentRecord);
}
