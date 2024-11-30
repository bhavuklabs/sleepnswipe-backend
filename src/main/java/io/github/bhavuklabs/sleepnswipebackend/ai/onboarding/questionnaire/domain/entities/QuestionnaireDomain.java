package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.entities;

import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class QuestionnaireDomain {
    private String questionName;
    private List<String> options;

    public QuestionnaireDomain(String questionName, List<String> options) {
        this.questionName = questionName;
        this.options = options;
    }

    public QuestionnaireDomain() {

    }

    public Questionnaire fromDto(QuestionnaireDomain domain) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setQuestionName(questionName);
        questionnaire.setOptions(options);
        return questionnaire;
    }
}
