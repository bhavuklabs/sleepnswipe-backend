package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.web.controllers;

import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.entities.QuestionnaireDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.mappers.QuestionnaireMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.services.core.QuestionnaireService;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIResponseDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequestMapping("/onboarding/questions")
@RestController
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final QuestionnaireMapper mapper;
    public QuestionnaireController(QuestionnaireService service, QuestionnaireMapper mapper) {
        this.questionnaireService = service;
        this.mapper = mapper;
    }


    @PostMapping("/send")
    public ResponseEntity<List<QuestionnaireDomain>> postRequest() throws URISyntaxException {
        AIRequestDomain.Part part = new AIRequestDomain.Part("""
        Generate a list 5 multiple-choice Questionnaires, 3 scenario-based and 2 upbringing-based to infer the personality of a person. 
        Prompt: Make sure you're returning a list of 5 questions so that they are serializable/deserializable in the given format.
        {
            "questions": [
                {"question": "...", "options": ["option1", "option2", "option3", "option4"]},
                ...
            ]
        }
        """);
        AIRequestDomain.Content content = new AIRequestDomain.Content(Collections.singletonList(part));
        AIRequestDomain requestDomain = new AIRequestDomain(Collections.singletonList(content));
        var entity = this.questionnaireService.createQuestionnaires(requestDomain);
        return ResponseEntity.of(Optional.ofNullable(entity));
    }

}
