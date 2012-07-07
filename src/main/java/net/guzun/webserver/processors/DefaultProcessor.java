package net.guzun.webserver.processors;

import java.io.PrintStream;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpConstants;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

/**
 * The Class DefaultProcessor.
 */
public class DefaultProcessor extends BaseProcessor {

    private HttpHelper httpHelper;

    /**
     * Instantiates a new default processor.
     *
     * @param httpHelper the http helper
     */
    public DefaultProcessor(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }
    /*
     * (non-Javadoc)
     * @see
     * net.guzun.webserver.processors.BaseProcessor#process(net.guzun.webserver
     * .http.HttpRequest, net.guzun.webserver.http.HttpResponse)
     */
    @Override
    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
        PrintStream printStream = new PrintStream(response.getOutputStream());
        httpHelper.createResponseHeader(printStream, HttpConstants.HTTP_METHOD_NOT_ALLOWED, "text/html");
        httpHelper.writeHtmHead(printStream);
        printStream.append("<h1>405 Method is not allowed</h1>");
        printStream.append("Method is not implemented yet");
        httpHelper.writeHtmTail(printStream);
    }

}
