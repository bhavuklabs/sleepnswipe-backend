package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, UUID> {
}
