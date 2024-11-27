package io.github.bhavuklabs.sleepnswipebackend.matching.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.matching.domain.models.MatchPreference;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.GenderEnum;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchPreferenceRepository extends JpaRepository<MatchPreference, UUID> {
    Optional<MatchPreference> findByUser(User user);
    @Query("SELECT mp FROM MatchPreference mp WHERE " +
            "(:age BETWEEN mp.minAge AND mp.maxAge) AND " +
            "mp.preferredGender = :gender")
    List<MatchPreference> findMatchingPreferences(
            @Param("age") int age,
            @Param("gender") GenderEnum gender
    );

    boolean existsByUser(User user);
}