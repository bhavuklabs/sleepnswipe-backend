package io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
    List<User> findByEmail(String email);
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByEmailAndPassword(String email, String password);

}
