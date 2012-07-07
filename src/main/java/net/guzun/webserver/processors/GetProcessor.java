package net.guzun.webserver.processors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpConstants;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.io.ContentProvider;
import net.guzun.webserver.io.ResourcesFactory;

/**
 * The Class GetProcessor.
 */
public class GetProcessor extends BaseProcessor {

    private static final int BUFFER_SIZE = 4096;
    private HttpHelper httpHelper;
    private final ResourcesFactory resourcesFactory;

    /**
     * Instantiates a new gets the processor.
     * @param httpHelper HttpHelper
     * @param resourcesFactory resources factory param
     */
    public GetProcessor(HttpHelper httpHelper, ResourcesFactory resourcesFactory) {
        this.httpHelper = httpHelper;
        this.resourcesFactory = resourcesFactory;
    }

    /*
     * (non-Javadoc)
     * @see
     * net.guzun.webserver.processors.BaseProcessor#process(net.guzun.webserver
     * .http.HttpRequest, net.guzun.webserver.http.HttpResponse)
     */
    @Override
    public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
        if (request.getMethod().equals(HttpConstants.METHOD_GET)) {
            writeResponse(request, response);
        } else {
            super.process(request, response);
        }
    }

    /**
     * Write response.
     * @param request  the request
     * @param response the response
     * @throws RequestProcessingException the request processing exception
     */
    private void writeResponse(HttpRequest request, HttpResponse response) throws RequestProcessingException {
        String absolutePath = request.getAbsolutPath();
        OutputStream outputStream = response.getOutputStream();

        ContentProvider contentProvider = resourcesFactory.getContentProvider(absolutePath);
        if (contentProvider.exists()) {
            if (contentProvider.isDirectory()) {
                boolean parentPathEnabled = request.getUriPath().equals("/");
                displayFolderContent(outputStream, contentProvider, parentPathEnabled);
            } else {
                displayFileContent(outputStream, contentProvider);
            }
        } else {
            displayNotFound(outputStream);
        }
    }

    /**
     * Display not found.
     * @param outputStream the output stream
     */
    private void displayNotFound(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream, true);
        httpHelper.createResponseHeader(printStream, HttpConstants.HTTP_NOT_FOUND, "text/html");
        httpHelper.writeHtmHead(printStream);
        printStream.print("<h1> 404 Resource not found</h1>");
        httpHelper.writeHtmTail(printStream);
    }

    /**
     * Display file content.
     * @param outputStream the output stream
     * @param contentProvider the content provider
     * @throws RequestProcessingException the request processing exception
     */
    private void displayFileContent(OutputStream outputStream, ContentProvider contentProvider)
            throws RequestProcessingException {
        PrintStream printStream = new PrintStream(outputStream, true);
        String contentType = contentProvider.getContentType();
        httpHelper.createResponseHeader(printStream, HttpConstants.HTTP_OK, contentType);
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        try {
            InputStream fileInputStream = contentProvider.getContentStream();

            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            fileInputStream.close();
        } catch (IOException e) {
            throw new RequestProcessingException(e);
        }
    }

    /**
     * Display folder content.
     * @param outputStream the output stream
     * @param contentProvider the content provider
     * @param parentPathEnabled the parent path enabled
     */
    private void displayFolderContent(OutputStream outputStream, ContentProvider contentProvider,
            boolean parentPathEnabled) {
        PrintStream printStream = new PrintStream(outputStream, true);

        httpHelper.createResponseHeader(printStream, HttpConstants.HTTP_OK, "text/html");
        StringBuilder html = buildBody(contentProvider, parentPathEnabled);
        httpHelper.writeHtmHead(printStream);
        httpHelper.writeHtmTail(printStream);
        printStream.print(html.toString());
    }

    /**
     * Writes the response body.
     *
     * @param contentProvider the content provider
     * @param parentPathEnabled the parent path enabled
     * @return the string builder
     */
    private StringBuilder buildBody(ContentProvider contentProvider, boolean parentPathEnabled) {
        StringBuilder html = new StringBuilder();
        html.append("<ul id=\"folders\">");

        String[] list = contentProvider.list();
        if (!parentPathEnabled) {
            html.append("<li><a href=\"../\">&lt;..&gt;</a></li>");
        }

        for (String fileItem : list) {
            String pathToResource = contentProvider.getPath() + "/"  + fileItem;
            ContentProvider childContent = resourcesFactory.getContentProvider(pathToResource);
            if (childContent.isDirectory()) {
                html.append(String.format("<li><a href=\"%1$s/\">&lt;%1$s&gt;</a></li>", childContent.getName()));
            } else {
                html.append(String.format("<li><a href=\"%1$s\">%1$s</a></li>", childContent.getName()));
            }
        }

        html.append("</ul>");
        html.append("<form action=\"#\" method=\"post\" enctype=\"multipart/form-data\">");
        html.append("<input type=\"file\" name=\"file\"/><br/>");
        html.append("<input type=\"submit\" name=\"submit\"/>");
        html.append("</form>");
        return html;
    }
}
