package net.guzun.webserver.processors;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

/**
 * The Interface RequestProcessor.
 */
public interface RequestProcessor {

    /**
     * Process.
     * @param request the request
     * @param response the response
     * @throws RequestProcessingException
     *             the request processing exception
     */
    void process(HttpRequest request, HttpResponse response) throws RequestProcessingException;

    /**
     * Gets the next processor.
     * @return the next processor
     */
    RequestProcessor getNextProcessor();
}
