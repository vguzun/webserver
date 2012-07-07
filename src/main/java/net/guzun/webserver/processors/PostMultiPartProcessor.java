package net.guzun.webserver.processors;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import net.guzun.webserver.exceptions.BadInputStreamException;
import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.http.HttpStreamReader;
import net.guzun.webserver.utils.ContentDisposition;

/**
 * The Class PostMultiPartProcessor.
 */
public class PostMultiPartProcessor extends BaseProcessor {

    private HttpHelper httpHelper;

    /**
     * Instantiates a new gets the processor.
     * @param httpHelper HttpHelper
     */
    public PostMultiPartProcessor(HttpHelper httpHelper) {
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

        if (request.isMultipart()) {
            processMultipartRequest(request);
            PrintStream printStream = new PrintStream(response.getOutputStream(), true);

            httpHelper.createResponseRedirect(request, printStream);
            httpHelper.writeHtmHead(printStream);
            printStream.println("File uploaded successfully to continue <a href=\".\">click here></a>\r\n");
            httpHelper.writeHtmTail(printStream);

        } else {
            super.process(request, response);
        }

    }

    /**
     * Process multipart request.
     * @param request the request
     * @throws BadInputStreamException the bad input stream exception
     * @throws RequestProcessingException the request processing exception
     */
    private void processMultipartRequest(HttpRequest request) throws BadInputStreamException,
            RequestProcessingException {
        HttpStreamReader inputStream = request.getInputStream();
        inputStream.upgradeToMultipart(request.getBoundary());

        try {

            do {
                processMultipartItem(request, inputStream);
            } while (inputStream.readBoundary());

        } catch (IOException e) {
            throw new RequestProcessingException(e);
        }
    }

    /**
     * Process multipart item.
     * @param request the request
     * @param inputStream the input stream
     * @throws BadInputStreamException the bad input stream exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void processMultipartItem(HttpRequest request, HttpStreamReader inputStream)
            throws BadInputStreamException, IOException {
        String headersString = inputStream.readHeaders();
        String[] headers = headersString.split("\r\n");
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        inputStream.readBody(data);
        // If there are more then 1 headers this means that it is a file content
        // all others are ignored now
        if (headers.length > 1) {
            ContentDisposition contentDisposition = new ContentDisposition(headers[1]);
            String fileName = contentDisposition.getFileName();
            if (fileName != null) {
                FileOutputStream fstream = new FileOutputStream(request.getAbsolutPath() + fileName);
                data.writeTo(fstream);
                fstream.close();
            }
        }
    }
}
