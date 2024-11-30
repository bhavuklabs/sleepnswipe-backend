package io.github.bhavuklabs.sleepnswipebackend.ai.gemini.config;

import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client.GoogleChatClient;
import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client.GoogleChatClientImpl;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.config.GenericAIConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Configuration
public class GoogleGeminiConfiguration implements GenericAIConfiguration<GoogleChatClient> {

    @Value("${google.gemini.api.key}")
    private String geminiApiKey;

    @Value("${google.gemini.api.url}")
    private String geminiApiUrl;

    @Bean
    @Override
    public GoogleChatClient getClient() throws URISyntaxException {
        return new GoogleChatClientImpl(geminiApiUrl, geminiApiKey, new RestTemplate());
    }
}
