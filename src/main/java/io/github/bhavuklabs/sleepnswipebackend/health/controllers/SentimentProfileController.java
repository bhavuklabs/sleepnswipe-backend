package io.github.bhavuklabs.sleepnswipebackend.health.controllers;

import io.github.bhavuklabs.sleepnswipebackend.ai.health.domain.services.core.SentimentProfileService;
import io.github.bhavuklabs.sleepnswipebackend.exquisite.utilities.DashboardDetails;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.entities.SentimentProfileRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.health.domain.services.core.SentimentAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentiment")
public class SentimentProfileController {

    private final SentimentProfileService sentimentProfileService;

    public SentimentProfileController(final SentimentProfileService sentimentProfileService) {
        this.sentimentProfileService = sentimentProfileService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<DashboardDetails> fetchSentiments(@RequestBody SentimentProfileRequestDomain sentimentProfileRequestDomain) {
        return ResponseEntity.ok(this.sentimentProfileService.generateAndReturnSentimentProfile(sentimentProfileRequestDomain));
    }
}
