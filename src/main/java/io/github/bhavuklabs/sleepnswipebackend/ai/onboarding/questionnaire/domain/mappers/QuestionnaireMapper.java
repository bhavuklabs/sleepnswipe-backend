package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.mappers;

import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.ai.response.QuestionnaireResponseReceiver;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.entities.QuestionnaireDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireMapper extends GenericMapper<Questionnaire, QuestionnaireDomain> {

    public QuestionnaireMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Questionnaire> getEntityClass() {
        return null;
    }

    @Override
    protected Class<QuestionnaireDomain> getDtoClass() {
        return null;
    }
}
