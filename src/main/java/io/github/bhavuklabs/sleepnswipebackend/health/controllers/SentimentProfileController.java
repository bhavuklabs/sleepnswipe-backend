package io.github.bhavuklabs.sleepnswipebackend.health.controllers;

import io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.services.core.SentimentProfileService;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentProfileRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentiment")
public class SentimentProfileController {

    private final SentimentProfileService sentimentProfileService;

    public SentimentProfileController(final SentimentProfileService sentimentProfileService) {
        this.sentimentProfileService = sentimentProfileService;
    }

    @GetMapping("/fetch")
    public void fetchSentiments(@RequestBody SentimentProfileRequestDomain sentimentProfileRequestDomain) {
        this.sentimentProfileService.generateAndReturnSentimentProfile(sentimentProfileRequestDomain);
    }
}
