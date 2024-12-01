package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SentimentProfileRepository extends JpaRepository<SentimentProfile, UUID> {
}
