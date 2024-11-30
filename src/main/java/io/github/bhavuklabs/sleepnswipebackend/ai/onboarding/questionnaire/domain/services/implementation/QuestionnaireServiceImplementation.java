package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.services.implementation;

import com.google.gson.Gson;
import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client.GoogleChatClient;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.ai.response.QuestionnaireResponseReceiver;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.entities.QuestionnaireDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.mappers.QuestionnaireMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.repositories.QuestionnaireRepository;
import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.services.core.QuestionnaireService;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class QuestionnaireServiceImplementation extends QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionnaireMapper questionnaireMapper;
    private final GoogleChatClient chatClient;

    public QuestionnaireServiceImplementation(QuestionnaireRepository repository, QuestionnaireMapper mapper, GoogleChatClient chatClient) {
        super(repository, mapper);
        this.questionnaireRepository = repository;
        this.questionnaireMapper = mapper;
        this.chatClient = chatClient;
    }

    @Override
    public List<QuestionnaireDomain> createQuestionnaires(AIRequestDomain requestDomain) throws URISyntaxException {
        var questionsList =  this.generateResponse(requestDomain).getQuestions();
        var list = questionsList.stream().map(question -> {
            var questionnaire = new Questionnaire();
            questionnaire.setQuestionName(question.getQuestion());
            questionnaire.setOptions(question.getOptions());
            return questionnaire;
        }).toList();
        this.questionnaireRepository.saveAll(list);

        return list.stream().map(questionnaire -> {
            return new QuestionnaireDomain(questionnaire.getQuestionName(), questionnaire.getOptions());
        }).toList();
    }

    @Override
    public QuestionnaireResponseReceiver generateResponse(AIRequestDomain aiRequest) throws URISyntaxException {
        var pojo = this.chatClient.sendMessage(aiRequest);
        var gson = new Gson();
        QuestionnaireResponseReceiver responseReceiver = gson.fromJson(pojo, QuestionnaireResponseReceiver.class);
        return responseReceiver;
    }
}
