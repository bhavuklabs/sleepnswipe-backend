package io.github.bhavuklabs.sleepnswipebackend.matching.domain.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
public class DynamicKafkaTopicConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final ConcurrentHashMap<String, Boolean> createdTopics = new ConcurrentHashMap<>();
    private final AdminClient adminClient;


    // Add a constructor that Spring can use for dependency injection
    public DynamicKafkaTopicConfiguration(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    // Ensure the method is public
    public String createUserRightSwipesTopic(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        String topicName = "right-swipes-" + uuid.toString();

        return createdTopics.computeIfAbsent(topicName, key -> {
            try {
                NewTopic topic = TopicBuilder.name(topicName)
                        .partitions(1)
                        .replicas(1)
                        .build();

                adminClient.createTopics(Collections.singleton(topic))
                        .all()
                        .get(10, TimeUnit.SECONDS);

                return true;
            } catch (TopicExistsException e) {
                return true;
            } catch (Exception e) {
                return false;
            }
        }) ? topicName : null;
    }
}