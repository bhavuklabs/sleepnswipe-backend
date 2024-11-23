package io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core;

import io.github.bhavuklabs.sleepnswipebackend.commons.persistence.GenericPersistenceService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers.UserMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

public abstract class UserService extends GenericPersistenceService<User, UserDomain,UUID> {

    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    public abstract AuthResponseDomain signup(UserDomain userDomain);
    public abstract AuthResponseDomain login(AuthRequestDomain userDomain);
    public abstract AuthResponseDomain authenticationSignup(AuthRequestDomain userDomain);
    public abstract Optional<UserDomain> update(UserDomain userDomain);
}
