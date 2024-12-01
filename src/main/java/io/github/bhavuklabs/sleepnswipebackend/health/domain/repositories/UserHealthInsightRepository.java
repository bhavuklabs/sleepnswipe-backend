package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.UserHealthInsight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserHealthInsightRepository extends JpaRepository<UserHealthInsight, UUID> {
}
