package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.ai.response.QuestionnaireResponseReceiver;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.entities.QuestionnaireDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.mappers.QuestionnaireMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.repositories.QuestionnaireRepository;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.services.GenericAIService;

import java.net.URISyntaxException;
import java.util.List;

public abstract class QuestionnaireService implements GenericAIService<QuestionnaireResponseReceiver> {
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionnaireMapper questionnaireMapper;

    public QuestionnaireService(final QuestionnaireRepository questionnaireRepository, final QuestionnaireMapper questionnaireMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionnaireMapper = questionnaireMapper;
    }

    public abstract List<QuestionnaireDomain> createQuestionnaires(AIRequestDomain requestDomain) throws URISyntaxException;

}
