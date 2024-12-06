package io.github.bhavuklabs.sleepnswipebackend.matching.web.controllers;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.MatchResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.KafkaMatchProcessor;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.MatchService;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.SwipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final SwipeService swipeService;
    private final MatchService matchService;
    private final KafkaMatchProcessor kafkaMatchProcessor;

    public MatchController(
            SwipeService swipeService,
            MatchService matchService,
            KafkaMatchProcessor kafkaMatchProcessor
    ) {
        this.swipeService = swipeService;
        this.matchService = matchService;
        this.kafkaMatchProcessor = kafkaMatchProcessor;
    }

    @PostMapping("/swipe")
    public ResponseEntity<String> processSwipe(
            @Valid @RequestBody SwipeRequestDomain swipeRequestDomain
    ) throws Exception {
        swipeService.processSwipe(swipeRequestDomain);
        return ResponseEntity.ok("Swipe processed successfully");
    }

    @GetMapping("/candidates/{userId}")
    public ResponseEntity<List<MatchResponseDomain>> getMatchCandidates(
            @PathVariable UUID userId
    ) {
        // Instead of processing match candidates, find and return potential matches
        List<MatchResponseDomain> matchCandidates = matchService.findMatchCandidates(userId);
        return ResponseEntity.ok(matchCandidates);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MatchResponseDomain>> getUserMatches(
            @PathVariable UUID userId
    ) {
        kafkaMatchProcessor.processMatchCandidates(userId);
        List<MatchResponseDomain> matches = matchService.findMatchCandidates(userId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/compatibility/{userId}/{candidateId}")
    public ResponseEntity<Double> getCompatibilityScore(
            @PathVariable UUID userId,
            @PathVariable UUID candidateId
    ) {
        double compatibilityScore = matchService.calculateCompatibility(userId, candidateId);
        return ResponseEntity.ok(compatibilityScore);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}