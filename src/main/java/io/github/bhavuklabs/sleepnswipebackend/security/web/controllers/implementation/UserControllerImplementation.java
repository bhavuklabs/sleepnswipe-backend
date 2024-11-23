package io.github.bhavuklabs.sleepnswipebackend.security.web.controllers.implementation;

import io.github.bhavuklabs.sleepnswipebackend.commons.mappers.GenericMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.AuthResponseDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.entities.UserDomain;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.mappers.UserMapper;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.models.User;
import io.github.bhavuklabs.sleepnswipebackend.security.domain.services.core.UserService;
import io.github.bhavuklabs.sleepnswipebackend.security.web.controllers.core.UserController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class UserControllerImplementation extends UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserControllerImplementation(UserService service, UserMapper mapper) {
        super(service, mapper);
        this.userService = service;
        this.userMapper = mapper;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDomain> authenticate(@RequestBody AuthRequestDomain request) {
        AuthResponseDomain responseDomain = this.userService.authenticationSignup(request);
        if(responseDomain == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(responseDomain);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDomain> signup(@RequestBody UserDomain user) {
        AuthResponseDomain responseDomain = this.userService.signup(user);
        if(responseDomain == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(responseDomain);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDomain> signin(@RequestBody AuthRequestDomain userDTO) {
        return ResponseEntity.ok(this.userService.login(userDTO));
    }
}
