package io.github.bhavuklabs.sleepnswipebackend.matching.domain;


import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation.KafkaMatchProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRightSwipeStreamProcessor {

    private final KafkaMatchProcessor kafkaMatchProcessor;

    public UserRightSwipeStreamProcessor(KafkaMatchProcessor kafkaMatchProcessor) {
        this.kafkaMatchProcessor = kafkaMatchProcessor;
    }

    @KafkaListener(topicPattern = "right-swipes-*")
    public void processUserRightSwipes(SwipeRequestDomain swipeRequestDomain) {
        this.kafkaMatchProcessor.processMatchCandidates(swipeRequestDomain.userId());
    }
}
