package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentProfile;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SentimentProfileRepository extends JpaRepository<SentimentProfile, UUID> {

    Optional<SentimentProfile> findSentimentProfileByUser(User user);

}
