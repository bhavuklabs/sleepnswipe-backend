package io.github.bhavuklabs.sleepnswipebackend.security.domain.entities;

public record AuthRequestDomain(
        String email,
        String password
) {
}
