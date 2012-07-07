package net.guzun.webserver.exceptions;

public class BadInputStreamException extends RequestProcessingException {
    private static final long serialVersionUID = 8434598810045327973L;

    public BadInputStreamException(String message) {
        super(message);
    }

    public BadInputStreamException(Throwable exception) {
        super(exception);
    }
}
