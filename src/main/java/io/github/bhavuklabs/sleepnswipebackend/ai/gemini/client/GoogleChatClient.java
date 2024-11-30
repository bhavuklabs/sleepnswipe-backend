package io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client;

import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.pojo.GeminiResponsePojo;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIResponseDomain;

import java.net.URISyntaxException;
import java.util.Map;

public interface GoogleChatClient {
    String sendMessage(AIRequestDomain request) throws URISyntaxException;
    Map<String, String> getHistoryContext(String history);
}
