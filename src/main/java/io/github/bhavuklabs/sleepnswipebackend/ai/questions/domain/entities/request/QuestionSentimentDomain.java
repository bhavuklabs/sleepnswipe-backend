package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.request;

import java.util.List;
import java.util.Map;

public record QuestionSentimentDomain(
        String email,
        List<Map<String, String>> responses
) {
}
