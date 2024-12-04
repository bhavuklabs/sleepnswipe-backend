package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.config.DynamicKafkaTopicConfiguration;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.SwipeHistoryRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.SwipeService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SwipeServiceImplementation implements SwipeService {

    private final SwipeHistoryRepository swipeHistoryRepository;
    private final UserRepository userRepository;
    private final DynamicKafkaTopicConfiguration dynamicKafkaTopicConfiguration;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SwipeServiceImplementation.class);

    @Autowired
    public SwipeServiceImplementation(
            final SwipeHistoryRepository swipeHistoryRepository,
            final UserRepository userRepository,
            final DynamicKafkaTopicConfiguration dynamicKafkaTopicConfiguration,
            final KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.swipeHistoryRepository = swipeHistoryRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.dynamicKafkaTopicConfiguration = dynamicKafkaTopicConfiguration;
    }

    @Transactional
    @Override
    public void processSwipe(SwipeRequestDomain requestDomain) throws Exception {
        // Validate user and target profile
        var user = this.userRepository.findById(requestDomain.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        var targetProfile = this.userRepository.findById(requestDomain.targetProfileId())
                .orElseThrow(() -> new UsernameNotFoundException("Target User Not Found"));

        // Create and save swipe history
        SwipeHistory swipeHistory = new SwipeHistory();
        swipeHistory.setUser(user);
        swipeHistory.setTargetProfile(targetProfile);
        swipeHistory.setSwipeTimestamp(LocalDateTime.now());
        swipeHistory.setSwipeType(requestDomain.swipeType());

        this.swipeHistoryRepository.save(swipeHistory);

        // Process right swipes with Kafka
        if (requestDomain.swipeType() == SwipeHistory.SwipeType.RIGHT) {
            try {
                String userTopic = dynamicKafkaTopicConfiguration.createUserRightSwipesTopic(requestDomain.userId());

                if (userTopic != null) {
                    // Send the swipe history to the Kafka topic
                    this.kafkaTemplate.send(userTopic, requestDomain.userId().toString(), swipeHistory)
                            .whenComplete((result, ex) -> {
                                if (ex == null) {
                                    logger.info("Swipe message sent successfully to topic: {}", userTopic);
                                } else {
                                    logger.error("Failed to send swipe message to topic: {}", userTopic, ex);
                                }
                            });
                } else {
                    logger.warn("Could not create Kafka topic for user: {}", requestDomain.userId());
                }
            } catch (Exception e) {
                logger.error("Error processing Kafka topic for right swipe", e);
                // Optionally, you might want to throw a custom exception or handle this differently
            }
        }
    }
}