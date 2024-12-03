package io.github.bhavuklabs.sleepnswipebackend.ai.questions.web;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.request.QuestionSentimentDomain;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response.SentimentRecord;
import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.services.core.QuestionService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/onboarding")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    public QuestionController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public void sendQuestionRequest() {
        this.questionService.createQuestions();
    }

    @GetMapping("/generatescores")
    public void generateScores(@RequestBody QuestionSentimentDomain questionSentimentDomain) {
        var user = this.userService.findUserByEmail(questionSentimentDomain.email()).get();
        this.questionService.generateSentimentScore(questionSentimentDomain);
    }

    @PostMapping("/generate-and-save-scores")
    public SentimentRecord generateAndSaveScores(@RequestBody QuestionSentimentDomain sentimentDomain) {
        return questionService.generateSentimentScore(sentimentDomain);
    }

}
