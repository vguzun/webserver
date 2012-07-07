package net.guzun.webserver.http;

import java.io.OutputStream;

/**
 * Represents HttpResponse.
 */
public class HttpResponse {
    /** The output stream. */
    private OutputStream outputStream;

    /**
     * Instantiates a new http response.
     * @param outputStream   the output stream
     */
    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Gets the output stream.
     * @return the output stream
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
