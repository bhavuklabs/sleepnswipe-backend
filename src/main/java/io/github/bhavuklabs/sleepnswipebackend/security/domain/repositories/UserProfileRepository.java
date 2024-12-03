package io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
}
