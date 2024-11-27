package io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.SwipeHistory;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SwipeHistoryRepository extends JpaRepository<SwipeHistory, UUID> {
    long countByUserAndSwipeTimestampBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end
    );

    List<SwipeHistory> findTop100ByUserOrderBySwipeTimestampDesc(User user);
    long countByUserAndSwipeType(User user, SwipeHistory.SwipeType swipeType);
    boolean existsByUserAndTargetProfile(User user, User targetProfile);

    boolean existsByUserAndTargetProfileAndSwipeType(User potentialMatchUser, User sourceUser, SwipeHistory.SwipeType swipeType);

    List<SwipeHistory> findByTargetProfileAndSwipeType(User sourceUser, SwipeHistory.SwipeType swipeType);
}
