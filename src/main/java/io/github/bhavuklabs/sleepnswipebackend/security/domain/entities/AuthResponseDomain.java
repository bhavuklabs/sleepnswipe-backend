package io.github.bhavuklabs.sleepnswipebackend.security.domain.entities;

public record AuthResponseDomain(
        String jwt,
        String message
) {

}