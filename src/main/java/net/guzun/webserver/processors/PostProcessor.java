package net.guzun.webserver.processors;

import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public class PostProcessor extends BaseProcessor {

	public PostProcessor(RequestProcessor nextProcessor) {
	    super(nextProcessor);
    }

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		super.process(request, response);

	}
}
