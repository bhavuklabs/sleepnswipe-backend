package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.QuestionDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.request.QuestionSentimentDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.ListQuestions;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.SentimentRecord;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.mappers.QuestionMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.models.Questions;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.repositories.QuestionsRepository;
import io.github.bhavuklabs.sleepnswipebackend.commons.persistence.GenericPersistenceService;

import java.util.List;
import java.util.UUID;

public abstract class QuestionService extends GenericPersistenceService<Questions, QuestionDomain, UUID> {

    private final QuestionsRepository questionsRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionsRepository repository, QuestionMapper mapper) {
        super(repository, mapper);
        this.questionsRepository = repository;
        this.questionMapper = mapper;
    }


    public abstract ListQuestions createQuestions();

    public abstract SentimentRecord generateSentimentScore(QuestionSentimentDomain questionSentimentDomain);
}
