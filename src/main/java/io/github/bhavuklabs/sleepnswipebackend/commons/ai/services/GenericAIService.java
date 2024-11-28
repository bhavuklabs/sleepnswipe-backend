package io.github.bhavuklabs.sleepnswipebackend.commons.ai.services;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.EmbeddingRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.EmbeddingResponseDomain;

public interface GenericAIService<Type> {

    AIResponseDomain generateResponse(AIRequestDomain<Type> aiRequest);
    EmbeddingResponseDomain generateEmbeddings(EmbeddingRequestDomain<Type> embeddingRequest);
    String updateContext(String currentContext, String newInput);
    String getCurrentModel();
    void setModel(String modelName);
}
