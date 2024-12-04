package io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.UserMatch;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserMatchRepository extends JpaRepository<UserMatch, UUID> {
    @Query("SELECT um FROM UserMatch um " +
            "WHERE (um.user1 = :user OR um.user2 = :user) " +
            "AND um.status = :status")
    List<UserMatch> findActiveMatchesForUser(
            @Param("user") User user,
            @Param("status") UserMatch.MatchStatus status
    );

    Optional<UserMatch> findByUser2(User user2);

    @Query("SELECT um FROM UserMatch um " +
            "WHERE um.compatibilityScore > :threshold " +
            "ORDER BY um.compatibilityScore DESC")
    List<UserMatch> findHighCompatibilityMatches(
            @Param("threshold") double compatibilityThreshold
    );

    Optional<UserMatch> findUserMatchByUser1(User user1);

    long countByUser1AndStatus(User user, UserMatch.MatchStatus status);
    long countByUser2AndStatus(User user, UserMatch.MatchStatus status);

    @Query("SELECT um FROM UserMatch um WHERE um.status = :matchStatus AND (um.user1 = :user OR um.user2 = :user)")
    List<UserMatch> findUserMatchByMatchStatusAndUser(
            @Param("matchStatus") UserMatch.MatchStatus matchStatus,
            @Param("user") User user
    );
}
