package io.github.bhavuklabs.sleepnswipebackend.commons.exceptions;

/**
 * A generic exception class to be used as a base for custom exceptions.
 * <p>
 * This class extends {@link Throwable}, allowing it to be used for error handling
 * throughout the application. It provides constructors for setting an error message
 * and an optional root cause.
 * </p>
 */
public class GenericException extends Throwable {

    private String message;
    private Throwable cause;

    /**
     * Constructs a new {@code GenericException} with the specified detail message.
     *
     * @param message the detail message describing the exception.
     */
    public GenericException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code GenericException} with the specified detail message
     * and cause.
     *
     * @param message the detail message describing the exception.
     * @param cause   the underlying cause of the exception.
     */
    public GenericException(String message, Throwable cause) {
        super(message);
        this.message = message;
        this.cause = cause;
    }

    /**
     * Returns the cause of this exception or {@code null} if no cause was provided.
     *
     * @return the cause of the exception.
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
