package io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeQuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SwipeQuotaRepository extends JpaRepository<SwipeQuota, UUID> {
    Optional<SwipeQuota> findById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE SwipeQuota sq SET sq.dailySwipesUsed=0, sq.lastResetDate=:currentDate, sq.updatedAt=:currentTimestamp")
    void resetDailySwipes(@Param("currentDate")LocalDate currentDate,
        @Param("currentTimestamp") LocalDateTime currentTimestamp
        );
}
