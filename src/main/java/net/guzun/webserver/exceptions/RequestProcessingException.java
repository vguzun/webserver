package net.guzun.webserver.exceptions;

/**
 * The Class RequestProcessingException.
 */
public class RequestProcessingException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2272270230625363192L;

    /**
     * Instantiates a new request processing exception.
     *
     * @param message the message
     */
    public RequestProcessingException(String message) {
        super(message);
    }

    /**
     * Instantiates a new request processing exception.
     *
     * @param exception the exception
     */
    public RequestProcessingException(Throwable exception) {
        super(exception);
    }

}
