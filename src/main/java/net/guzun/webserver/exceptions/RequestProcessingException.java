package net.guzun.webserver.exceptions;

public class RequestProcessingException extends Exception {

    private static final long serialVersionUID = -2272270230625363192L;

    public RequestProcessingException(String message) {
        super(message);
    }

    public RequestProcessingException(Throwable exception) {
        super(exception);
    }

}
