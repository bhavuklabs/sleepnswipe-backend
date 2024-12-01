package io.github.bhavuklabs.sleepnswipebackend.health.domain.services.implementation;

import com.google.gson.Gson;
import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client.GoogleChatClient;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.QuestionnaireRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.mappers.SentimentAnalysisMapper;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentAnalysisRepository;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentScoreGeneration;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.utilities.SentimentLoader;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class SentimentScoreGenerationImplementation implements SentimentScoreGeneration {

    private final SentimentAnalysisRepository analysisRepository;
    private final SentimentAnalysisMapper sentimentAnalysisMapper;
    private final GoogleChatClient googleChatClient;

    public SentimentScoreGenerationImplementation(SentimentAnalysisRepository analysisRepository, SentimentAnalysisMapper sentimentAnalysisMapper, GoogleChatClient googleChatClient) {
        this.analysisRepository = analysisRepository;
        this.sentimentAnalysisMapper = sentimentAnalysisMapper;
        this.googleChatClient = googleChatClient;
    }

    public SentimentScoreDomain generateSentimentScores(QuestionnaireRequestDomain questionnaireResponsesDomain) throws URISyntaxException {
        String prompt =
                """ 
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
                """+SentimentLoader.getInstance().setSentimentLoaderMap().getSentimentLoaderMap();
        for(int i=0;i<questionnaireResponsesDomain.optionsList().size();i++) {
            prompt+=questionnaireResponsesDomain.questionsList().get(i)+" : "+questionnaireResponsesDomain.optionsList().get(i);
        }
        AIRequestDomain aiRequestDomain = new AIRequestDomain();
        var part = new AIRequestDomain.Part();
        part.setText(prompt);
        var content = new AIRequestDomain.Content();
        content.setParts(List.of(part));
        aiRequestDomain.setContents(List.of(content));
        System.out.println(aiRequestDomain);
        String response = this.googleChatClient.sendMessage(aiRequestDomain);
        System.out.println("Response: "+response);
        var gson = new Gson();
        var domain = gson.fromJson(response, SentimentScoreDomain.class);
        return domain;
    }

    @Override
    public SentimentScoreDomain generateResponse(AIRequestDomain aiRequest) throws URISyntaxException {
        return null;
    }
}
