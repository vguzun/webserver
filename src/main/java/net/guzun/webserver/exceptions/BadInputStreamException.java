package net.guzun.webserver.exceptions;

/**
 * The Class BadInputStreamException.
 */
public class BadInputStreamException extends RequestProcessingException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8434598810045327973L;

    /**
     * Instantiates a new bad input stream exception.
     * @param message the error message
     */
    public BadInputStreamException(String message) {
        super(message);
    }

    /**
     * Instantiates a new bad input stream exception.
     * @param exception the exception
     */
    public BadInputStreamException(Throwable exception) {
        super(exception);
    }
}
