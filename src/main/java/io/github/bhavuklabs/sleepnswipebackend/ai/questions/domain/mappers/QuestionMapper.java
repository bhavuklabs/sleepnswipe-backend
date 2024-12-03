package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.mappers;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.QuestionDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.models.Questions;
import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper extends GenericMapper<Questions, QuestionDomain> {

    public QuestionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Questions> getEntityClass() {
        return Questions.class;
    }

    @Override
    protected Class<QuestionDomain> getDtoClass() {
        return QuestionDomain.class;
    }
}
