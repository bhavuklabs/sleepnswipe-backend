package io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.embedding.Embedding;

import java.util.List;

public class AIRequestDomain<Type> {
    private String prompt;
    private String model;
    private List<Embedding<Type>> embedding;
    private String context;
}
