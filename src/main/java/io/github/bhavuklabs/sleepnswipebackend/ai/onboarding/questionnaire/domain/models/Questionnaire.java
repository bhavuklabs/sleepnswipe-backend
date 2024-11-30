package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models;


import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="questions")
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "question_name")
    private String questionName;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_value")
    private List<String> options;

    public Questionnaire() {

    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Questionnaire(String questionName, List<String> options) {
        this.questionName = questionName;
        this.options = options;
    }
}
