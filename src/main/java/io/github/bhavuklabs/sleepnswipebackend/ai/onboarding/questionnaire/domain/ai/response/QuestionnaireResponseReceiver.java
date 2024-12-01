package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.ai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireResponseReceiver {

    @JsonProperty(namespace = "questions")
    private List<Question> questions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Question {
        @JsonProperty(namespace = "question")
        private String question;

        @JsonProperty(namespace = "options")
        private List<String> options;
    }
}
