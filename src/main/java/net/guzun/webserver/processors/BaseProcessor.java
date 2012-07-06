package net.guzun.webserver.processors;

import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public abstract class BaseProcessor implements RequestProcessor{

    protected RequestProcessor nextProcessor;

    public BaseProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public void process(HttpRequest request, HttpResponse response){
    	if (getNextProcessor() != null) {
    		getNextProcessor().process(request, response);
    	}
    }

    public RequestProcessor getNextProcessor() {
        return nextProcessor;
    }

}