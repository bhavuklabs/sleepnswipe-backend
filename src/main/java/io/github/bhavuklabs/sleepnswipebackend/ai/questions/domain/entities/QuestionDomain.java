package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record QuestionDomain(
        @JsonProperty("question") String question,
        List<String> options
) {
}
