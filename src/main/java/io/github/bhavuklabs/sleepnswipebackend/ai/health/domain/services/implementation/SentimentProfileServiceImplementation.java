package io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.services.implementation;

import com.google.gson.Gson;
import io.github.venkat1701.javageminiclient.basic.BasicRequestValidator;
import io.github.venkat1701.javageminiclient.commons.exceptions.ValidationException;
import io.github.venkat1701.javageminiclient.models.ChatModel;
import io.github.venkat1701.javageminiclient.request.ChatRequest;
import io.github.venkat1701.javageminiclient.response.ChatResponse;
import io.github.bhavuklabs.sleepnswipebackend.ai.commons.GenerateRequestBody;
import io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.entities.SentimentProfileDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.services.core.SentimentProfileService;
import io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities.DashboardDetails;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentProfileRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentAnalysis;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentProfile;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentAnalysisRepository;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories.SentimentProfileRepository;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SentimentProfileServiceImplementation implements SentimentProfileService {

    private final UserService userService;
    private final SentimentProfileRepository sentimentProfileRepository;
    private final SentimentAnalysisRepository sentimentAnalysisRepository;

    public SentimentProfileServiceImplementation(UserService userService, SentimentProfileRepository sentimentProfileRepository, SentimentAnalysisRepository sentimentAnalysisRepository) {
        this.userService = userService;
        this.sentimentProfileRepository = sentimentProfileRepository;
        this.sentimentAnalysisRepository = sentimentAnalysisRepository;
    }

    public DashboardDetails generateAndReturnSentimentProfile(SentimentProfileRequestDomain sentimentProfileDomain) {
        User user = this.userService.findUserByEmail(sentimentProfileDomain.email()).stream().findFirst().orElse(null);
        if(user == null) {
            throw new UsernameNotFoundException("User with corresponding mail Not Found");
        }

        var analysis = this.sentimentAnalysisRepository.findByUserId(user.getId()).orElse(null);
        var map = analysis.getScores();
        ChatModel chatModel = new ChatModel(new RestTemplate(), new BasicRequestValidator());
        ChatRequest request = new ChatRequest(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=",
                "AIzaSyCLH6TVW39tElRtfYKzJE8_GLXmFsjU-k4",
                GenerateRequestBody.getRequestBody("""
                        Generate 4 scores namely: overall sentiment score, emotional stability score, personality type as in String and with accordance to Myers Briggs Indicator Type and the Sentiment scores provided and the last parameter being the social interaction score.
                        Prompt: Make sure you're returning the data in the given format
                        {
                            "overallSentimentScore": some_value,
                            "emotionalSentimentScore": some_value,
                            "personalityType": "some_string",
                            "socialInteractionScore": some_value
                        }
                        """+map)
        ).withHeader("Content-Type", "application/json");

        try {
            ChatResponse response = chatModel.call(request);
            Gson gson = new Gson();
            var parsedResponse = gson.fromJson(response.getBody().getCandidates().stream().findFirst().get().getContent().getParts().getFirst().getText().replace("```json","").replace("```",""), io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.entities.SentimentProfileDomain.class);
            SentimentProfile sentimentProfile = new SentimentProfile();
            sentimentProfile.setUser(user);
            sentimentProfile.setOverallSentimentScore(parsedResponse.overallSentimentScore());
            sentimentProfile.setPersonalityType(parsedResponse.personalityType());
            sentimentProfile.setSocialInteractionScore(parsedResponse.socialInteractionScore());
            sentimentProfile.setSentimentAnalysis(analysis);
            sentimentProfileRepository.save(sentimentProfile);
            List<List<Integer>> sleepRecord = new ArrayList<>();
            for(int i=0;i<=5;i++) {
                var list = new ArrayList<Integer>();
                for(int j=0;j<=5;j++) {
                    int val = (int)(Math.random()*(9-1)+1);
                    list.add(val);
                }
                sleepRecord.add(list);
            }
            DashboardDetails details = new DashboardDetails(sentimentProfile.getOverallSentimentScore(),sentimentProfile.getPersonalityType(), sentimentProfile.getEmotionalStabilityScore(), sentimentProfile.getSocialInteractionScore(), 2, sleepRecord);
            return details;
        } catch(ValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
