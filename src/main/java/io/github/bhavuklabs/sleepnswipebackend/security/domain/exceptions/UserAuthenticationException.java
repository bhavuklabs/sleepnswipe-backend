package io.github.bhavuklabs.sleepnswipebackend.security.domain.exceptions;

public class UserAuthenticationException extends RuntimeException {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
