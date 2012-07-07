package net.guzun.webserver.processors;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public interface RequestProcessor {
    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException;
    public RequestProcessor getNextProcessor();
}
