package io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.embedding.Embedding;

import java.util.List;

public class EmbeddingRequestDomain<Type> {

    private List<Embedding<Type>> embedding;
    private String model;
}
