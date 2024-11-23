package io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends GenericMapper<User, UserDomain> {
    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDomain> getDtoClass() {
        return UserDomain.class;
    }
}