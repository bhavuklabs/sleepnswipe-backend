package io.github.bhavuklabs.sleepnswipebackend.commons.ai.context;

import java.util.List;

public interface SummarizerContext<Embedding> {
    List<Embedding> generateSummaryContext();
}
