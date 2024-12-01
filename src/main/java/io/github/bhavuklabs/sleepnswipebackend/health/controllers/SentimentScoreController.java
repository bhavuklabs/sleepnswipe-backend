package io.github.bhavuklabs.sleepnswipebackend.health.controllers;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.QuestionnaireRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentScoreDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentScoreGeneration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/sentiment")
public class SentimentScoreController {

    private final SentimentScoreGeneration sentimentScoreGeneration;

    public SentimentScoreController(final SentimentScoreGeneration sentimentScoreGeneration) {
        this.sentimentScoreGeneration = sentimentScoreGeneration;
    }

    @PostMapping("/send")
    public ResponseEntity<SentimentScoreDomain> sentimentScore() throws URISyntaxException {
        List<String> questions = List.of("You're faced with a complex problem at work with a tight deadline.  How do you approach it?",
                "A friend is venting about a personal problem. How do you respond?",
                "You're planning a weekend getaway.  What kind of trip appeals to you most?",
                "Growing up, were you encouraged to express your feelings openly?",
                "In your childhood, what type of activities did you enjoy most?"
        );

        List<String> answers = List.of(
          "I systematically break it down into smaller, manageable tasks and tackle them one by one.",
          "I listen empathetically, offering support and validation without judgment.",
          "A meticulously planned itinerary with specific activities and sights to see.",
          "Sometimes, it depended on the situation and who I was talking to.",
          "A mix of solitary and group activities, depending on my mood."
        );
        return ResponseEntity.ok(this.sentimentScoreGeneration.generateSentimentScores(new QuestionnaireRequestDomain(questions, answers)));
    }
}
