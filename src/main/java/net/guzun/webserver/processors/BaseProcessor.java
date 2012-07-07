package net.guzun.webserver.processors;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public abstract class BaseProcessor implements RequestProcessor {

    private RequestProcessor nextProcessor;
    
    public BaseProcessor() {
    	
    }

    public BaseProcessor(RequestProcessor nextProcessor) {
        this.setNextProcessor(nextProcessor);
    }

    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
    	if (getNextProcessor() != null) {
    		getNextProcessor().process(request, response);
    	}
    }

    public RequestProcessor getNextProcessor() {
        return nextProcessor;
    }

	public void setNextProcessor(RequestProcessor nextProcessor) {
	    this.nextProcessor = nextProcessor;
    }

}