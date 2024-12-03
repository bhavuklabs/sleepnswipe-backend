package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name="question_name")
    private String questionName;

    @ElementCollection
    @CollectionTable(name="question_options", joinColumns = @JoinColumn(name="question_id"))
    @Column(name="option_value")
    private List<String> options;

    public Questions() {}

    public Questions(String questionName, List<String> options) {
        this.questionName = questionName;
        this.options = options;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getQuestionName() {
        return questionName;
    }
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
