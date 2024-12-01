package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SentimentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, UUID> {
}
