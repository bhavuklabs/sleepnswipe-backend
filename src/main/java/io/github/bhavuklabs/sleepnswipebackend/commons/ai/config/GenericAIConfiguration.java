package io.github.bhavuklabs.sleepnswipebackend.commons.ai.config;

import java.net.URISyntaxException;

public interface GenericAIConfiguration<ChatClient> {
    public ChatClient getClient() throws URISyntaxException;
}
