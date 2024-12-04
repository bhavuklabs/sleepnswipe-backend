package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.KafkaMatchProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRightSwipeStreamProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UserRightSwipeStreamProcessor.class);

    private final KafkaMatchProcessor kafkaMatchProcessor;

    public UserRightSwipeStreamProcessor(KafkaMatchProcessor kafkaMatchProcessor) {
        this.kafkaMatchProcessor = kafkaMatchProcessor;
    }

    @KafkaListener(topicPattern = "right-swipes-*")
    public void processUserRightSwipes(SwipeRequestDomain swipeRequestDomain) {
        try {
            if (swipeRequestDomain != null && swipeRequestDomain.userId() != null) {
                this.kafkaMatchProcessor.processMatchCandidates(swipeRequestDomain.userId());
            } else {
                logger.warn("Received invalid swipe request domain");
            }
        } catch (Exception e) {
            logger.error("Error processing user right swipes", e);
        }
    }
}