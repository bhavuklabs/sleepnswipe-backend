package io.github.bhavuklabs.sleepnswipebackend.security.web.controllers.core;

import io.github.bhavuklabs.sleepnswipebackend.commons.controllers.GenericCrudController;
import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import io.github.bhavuklabs.sleepnswipebackend.commons.persistence.GenericPersistenceService;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers.UserMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

public class UserController extends GenericCrudController<User, UserDomain, UUID> {

    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
