package io.github.bhavuklabs.sleepnswipebackend.commons.ai.services;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.EmbeddingRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.EmbeddingResponseDomain;

import java.net.URISyntaxException;

public interface GenericAIService<ResponsePojo> {
    ResponsePojo generateResponse(AIRequestDomain aiRequest) throws URISyntaxException;
}
