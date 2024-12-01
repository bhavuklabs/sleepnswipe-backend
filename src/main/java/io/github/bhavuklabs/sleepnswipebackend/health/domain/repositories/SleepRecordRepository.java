package io.github.bhavuklabs.sleepnswipebackend.health.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.health.domain.models.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SleepRecordRepository extends JpaRepository<SleepRecord, UUID> {
}
