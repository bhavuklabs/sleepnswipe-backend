package io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.implementation;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.config.DynamicKafkaTopicConfiguration;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.entities.SwipeRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories.SwipeHistoryRepository;
import io.github.bhavuklabs.sleepnswipebackend.matching.domain.services.core.SwipeService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SwipeServiceImplementation implements SwipeService {

    private final SwipeHistoryRepository swipeHistoryRepository;
    private final UserRepository userRepository;
    private final DynamicKafkaTopicConfiguration dynamicKafkaTopicConfiguration;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public SwipeServiceImplementation(final SwipeHistoryRepository swipeHistoryRepository, final UserRepository userRepository, final DynamicKafkaTopicConfiguration dynamicKafkaTopicConfiguration, final KafkaTemplate<String, Object> kafkaTemplate) {
        this.swipeHistoryRepository = swipeHistoryRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.dynamicKafkaTopicConfiguration = dynamicKafkaTopicConfiguration;
    }

    @Transactional
    @Override
    public void processSwipe(SwipeRequestDomain requestDomain) throws Exception {
        var user = this.userRepository.findById(requestDomain.userId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        var targetProfile = this.userRepository.findById(requestDomain.targetProfileId()).orElseThrow(() -> new UsernameNotFoundException("Target User Not Found"));

        SwipeHistory swipeHistory = new SwipeHistory();
        swipeHistory.setUser(user);
        swipeHistory.setTargetProfile(targetProfile);
        swipeHistory.setSwipeTimestamp(LocalDateTime.now());
        swipeHistory.setSwipeType(requestDomain.swipeType());

        this.swipeHistoryRepository.save(swipeHistory);

        if(requestDomain.swipeType() == SwipeHistory.SwipeType.RIGHT) {
            String userTopic = dynamicKafkaTopicConfiguration.createUserRightSwipesTopic(requestDomain.userId());
            if(userTopic == null) {
                this.kafkaTemplate.send(userTopic, swipeHistory);
            }
        }

    }
}
