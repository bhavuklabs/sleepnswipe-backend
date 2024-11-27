package io.github.bhavuklabs.sleepnswipebackend.matching.web.controllers;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.MatchResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.MatchService;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.SwipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final SwipeService swipeService;
    private final MatchService matchService;

    public MatchController(SwipeService swipeService, MatchService matchService) {
        this.swipeService = swipeService;
        this.matchService = matchService;
    }

    @PostMapping("/swipe")
    public ResponseEntity<String> processSwipe(
            @Valid @RequestBody SwipeRequestDomain swipeRequestDomain
    ) throws Exception {
        swipeService.processSwipe(swipeRequestDomain);
        return ResponseEntity.ok("Swipe processed successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MatchResponseDomain>> getUserMatches(
            @PathVariable UUID userId
    ) {
        List<MatchResponseDomain> matches = matchService.findMatchCandidates(userId);
        return ResponseEntity.ok(matches);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}