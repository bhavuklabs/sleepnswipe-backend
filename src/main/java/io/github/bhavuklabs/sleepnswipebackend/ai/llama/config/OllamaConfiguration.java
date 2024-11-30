package io.github.bhavuklabs.sleepnswipebackend.ai.llama.config;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.config.GenericAIConfiguration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;

public class OllamaConfiguration implements GenericAIConfiguration<ChatClient> {

    @Value("${spring.ai.ollama.chat.url}")
    private String chatUrl;

    @Override
    public ChatClient getClient() throws URISyntaxException {
        OllamaApi api = new OllamaApi(new URI(chatUrl).toString());
        var options = OllamaOptions.builder()
                .withModel("llama3.1")
                .withKeepAlive("10m")
                .build();

        return ChatClient.builder(
                OllamaChatModel.builder().withOllamaApi(api).withDefaultOptions(options).build()
        ).build();
    }
}
