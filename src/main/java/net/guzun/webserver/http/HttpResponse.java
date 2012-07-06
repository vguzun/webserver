package net.guzun.webserver.http;

import java.io.OutputStream;

public class HttpResponse {
    private OutputStream outputStream;
    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public OutputStream getOutputStream() {
        return outputStream;
    }
}
