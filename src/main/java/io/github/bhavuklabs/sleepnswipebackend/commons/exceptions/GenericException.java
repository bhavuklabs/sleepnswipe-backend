package io.github.bhavuklabs.sleepnswipebackend.commons.exceptions;

public class GenericException extends Throwable{

    private String message;
    private Throwable cause;

    public GenericException(String message, Throwable cause) {
        super(message);
        this.message = message;
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    public GenericException(String message) {
        super(message);
    }

}
