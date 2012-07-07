package net.guzun.webserver.processors;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

/**
 * The BaseProcessor.
 */
public abstract class BaseProcessor implements RequestProcessor {

    /** The next processor. */
    private RequestProcessor nextProcessor;

    /**
     * Instantiates a new base processor.
     */
    public BaseProcessor() {

    }

    /**
     * Invokes next processor if exists
     * @param request the request to process
     * @param response the respone to write
     * @throws RequestProcessingException request processing exception
     */
    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
        if (getNextProcessor() != null) {
            getNextProcessor().process(request, response);
        }
    }

    /*
     * @see net.guzun.webserver.processors.RequestProcessor#getNextProcessor()
     */
    public RequestProcessor getNextProcessor() {
        return nextProcessor;
    }

    /**
     * Sets the next processor.
     * @param nextProcessor the new next processor
     */
    public void setNextProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

}
