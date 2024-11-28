package io.github.bhavuklabs.sleepnswipebackend.commons.ai.embedding;

import java.util.Map;
import java.util.UUID;


public class Embedding<Type> {

    private UUID uuid;
    private Map<Integer, Type> embeddings;
}
