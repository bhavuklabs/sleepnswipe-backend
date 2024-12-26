package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.services.implementation;

import com.google.gson.Gson;
import io.github.venkat1701.javageminiclient.basic.BasicRequestValidator;
import io.github.venkat1701.javageminiclient.commons.exceptions.ValidationException;
import io.github.venkat1701.javageminiclient.commons.prompt.RequestPrompt;
import io.github.venkat1701.javageminiclient.commons.utilities.commons.Content;
import io.github.venkat1701.javageminiclient.commons.utilities.commons.Part;
import io.github.venkat1701.javageminiclient.commons.utilities.request.RequestBody;
import io.github.venkat1701.javageminiclient.models.ChatModel;
import io.github.venkat1701.javageminiclient.request.ChatRequest;
import io.github.venkat1701.javageminiclient.response.ChatResponse;
import io.github.bhavuklabs.sleepnswipebackend.ai.commons.GenerateRequestBody;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.QuestionDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.request.QuestionSentimentDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.ListQuestions;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.SentimentRecord;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.mappers.QuestionMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.repositories.QuestionsRepository;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.services.core.QuestionService;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentAnalysisService;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.utilities.SentimentLoader;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeQuota;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.SwipeQuotaRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class QuestionServiceImplementation extends QuestionService {

    private final QuestionsRepository questionsRepository;
    private final QuestionMapper questionMapper;
    private final UserRepository userRepository;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final SwipeQuotaRepository swipeQuotaRepository;

    public QuestionServiceImplementation(QuestionsRepository repository, QuestionMapper mapper, UserRepository userRepository, SentimentAnalysisService sentimentAnalysisService, SwipeQuotaRepository swipeQuotaRepository) {
        super(repository, mapper);
        this.questionsRepository = repository;
        this.questionMapper = mapper;
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.swipeQuotaRepository = swipeQuotaRepository;
    }


    @Override
    public List<QuestionDomain> createQuestions() {
        var restTemplate = new RestTemplate();
        var chatModel = new ChatModel(restTemplate, new BasicRequestValidator());
        RequestBody requestBody = getRequestBody("""
                        Generate a list 5 multiple-choice Questionnaires, 3 scenario-based and 2 upbringing-based to infer the personality of a person.\s
                        Prompt: Make sure you're returning a list of 5 questions so that they are serializable/deserializable in the given format.
                        {
                            "questions": [
                                {"question": "...", "options": ["option1", "option2", "option3", "option4"]},
                                ...
                            ]
                        }
                """);
        ChatRequest chatRequest = new ChatRequest("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=",
                "AIzaSyCLH6TVW39tElRtfYKzJE8_GLXmFsjU-k4",
                requestBody)
                .withHeader("Content-Type", "application/json");

        try {
            ChatResponse response = chatModel.call(chatRequest);

            System.out.println(response.getBody());
            Gson gson = new Gson();
            var parsedResponse = gson.fromJson(response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText().replace("```json","").replace("```",""), ListQuestions.class);
            System.out.println(parsedResponse);
            return null;
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestBody getRequestBody(String prompt) {
        return GenerateRequestBody.getRequestBody(prompt);
    }

    @Override
    public SentimentRecord generateSentimentScore(QuestionSentimentDomain sentimentDomain) {
        var map = SentimentLoader.getInstance().setSentimentLoaderMap().getSentimentLoaderMap();
        ChatModel chatModel = new ChatModel(new RestTemplate(), new BasicRequestValidator());
        RequestBody body = this.getRequestBody(""" 
                    Following are the questions and answers for the onboarding questions Just return the 81 double values in order with nothing else than that. -1 means negative sentiment and 1 means positive sentiment and 0 means neutralGenerate values in range of -1 to +1 inclusive and make them realistic values. Not just 0.0 or 0.5 but actual values. Make sure that youre evaluating all the questions and their answers with great depth and detail before performing sentiment analysis: Generate double datatype sentiment scores for all the 81 parameters given below:
                    The attributes with their default values is given, all you have to do is return data in this format: 
                    {
                        "sentimentsMap":[
                            {
                                "sentiment": "sentiment_name",
                                "value": double_sentiment_value
                            }
                        ]
                    }
                   Follow the given format to answer
                """+map+" and these are the questions with responses."+sentimentDomain.responses());
        ChatRequest request = new ChatRequest("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=",
                "AIzaSyCLH6TVW39tElRtfYKzJE8_GLXmFsjU-k4",
                body
        ).withHeader("Content-Type", "application/json");

        try {
            ChatResponse response = chatModel.call(request);
            var parsedResponse = response.getBody().getCandidates().getFirst().getContent().getParts().get(0).getText()
                    .replace("```json", "").replace("```", "");
            Gson gson = new Gson();
            SentimentRecord sentimentRecord = gson.fromJson(parsedResponse, SentimentRecord.class);
            var user = userRepository.findByEmail(sentimentDomain.email()).get(0);
            sentimentAnalysisService.saveSentimentAnalysis(user, sentimentRecord);
            var quota = this.swipeQuotaRepository.findByUserId(user.getId()).get();
            quota.setBonusSwipes(quota.getBonusSwipes() + 20);
            this.swipeQuotaRepository.save(quota);
            return sentimentRecord;
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
