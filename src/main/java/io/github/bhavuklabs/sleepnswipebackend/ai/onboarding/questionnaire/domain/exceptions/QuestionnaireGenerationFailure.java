package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.exceptions;

import io.github.bhavuklabs.sleepnswipebackend.commons.exceptions.GenericException;

public class QuestionnaireGenerationFailure extends GenericException {

    public QuestionnaireGenerationFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionnaireGenerationFailure(String message) {
        super(message);
    }
}
