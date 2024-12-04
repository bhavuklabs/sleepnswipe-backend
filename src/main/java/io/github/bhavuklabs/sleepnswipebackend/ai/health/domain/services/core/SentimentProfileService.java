package io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentProfileRequestDomain;

public interface SentimentProfileService {

    void generateAndReturnSentimentProfile(SentimentProfileRequestDomain sentimentProfileDomain);
}
