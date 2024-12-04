package io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    List<User> findByEmail(String email);

    List<User> findByUsernameAndPassword(String username, String password);

    List<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u WHERE u.id NOT IN " +
            "(SELECT sh.targetProfile.id FROM SwipeHistory sh WHERE sh.user.id = :targetUserId)")
    List<User> findUsersNotSwipedByUser(@Param("targetUserId") UUID targetUserId);

    @Query("SELECT u FROM User u WHERE " +
            "YEAR(CURRENT_DATE) - YEAR(u.dateOfBirth) BETWEEN :minAge AND :maxAge")
    List<User> findUsersWithinAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    @Query("SELECT u FROM User u WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) * " +
            "cos(radians(u.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(u.latitude)))) <= :distance")
    List<User> findUsersWithinLocation(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("distance") double distance);

    @Query("SELECT u FROM User u WHERE u.gender = :preferredGender")
    List<User> findUsersByGenderPreference(@Param("preferredGender") String preferredGender);
}
