package net.guzun.webserver.processors;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.http.HttpStreamReader;

/**
 * The Class HeaderProcessor.
 */
public class HeaderProcessor extends BaseProcessor {

    /**
     * Instantiates a new header processor.
     * @param nextProcessor the next processor
     */
    public HeaderProcessor(RequestProcessor nextProcessor) {
        super(nextProcessor);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.guzun.webserver.processors.BaseProcessor#process(net.guzun.webserver
     * .http.HttpRequest, net.guzun.webserver.http.HttpResponse)
     */
    @Override
    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
        try {
            HttpStreamReader headersReader = request.getInputStream();
            String headersString = headersReader.readHeaders();
            String[] headers = headersString.split("\r\n");
            processHeadTitle(request, headers[0]);

            // Iterating all header lines except the last, as it is an empty
            // line
            for (int i = 1; i < headers.length; i++) {
                String line = headers[i];
                if (!line.isEmpty()) {
                    processHeaderItem(request, line);
                }
            }
        } catch (Exception e) {
            throw new RequestProcessingException(e);
        }

        super.process(request, response);

    }

    /**
     * Process head title.
     * @param request the request
     * @param line header line to process
     */
    private void processHeadTitle(HttpRequest request, String line) {
        String[] headerParams = line.split(" ");
        request.setMethod(headerParams[0]);
        request.setPath(headerParams[1]);
        request.setProtocol(headerParams[2]);
    }

    /**
     * Process header item.
     * @param request the request
     * @param line the line
     */
    private void processHeaderItem(HttpRequest request, String line) {
        int separatorIndex = line.indexOf(":");
        String name = line.substring(0, separatorIndex);
        String value = line.substring(separatorIndex + 2);
        request.addAtribute(name, value);
    }
}
