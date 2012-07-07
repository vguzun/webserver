package net.guzun.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import net.guzun.webserver.processors.RequestProcessor;

import org.apache.log4j.Logger;

/**
 * The Class HttpContext.
 */
public class HttpContext implements Runnable {
    private static Logger logger = Logger.getLogger(HttpContext.class);
    private SocketChannel clientSocketChannel;
    private RequestProcessor requestProcessor;
    private HttpHelper httpHelper;
    private final String absolutePath;

    /**
     * Instantiates a new http context.
     *
     * @param absolutePathParam the absolute path param
     * @param requestProcessorParam the request processor param
     * @param clientSocketChannelParam the client socket channel param
     * @param httpHelper http helper utility class
     */
    public HttpContext(String absolutePathParam, RequestProcessor requestProcessorParam,
            SocketChannel clientSocketChannelParam, HttpHelper httpHelper) {
        this.absolutePath = absolutePathParam;
        this.clientSocketChannel = clientSocketChannelParam;
        this.requestProcessor = requestProcessorParam;
        this.httpHelper = httpHelper;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        PrintStream outputStream = null;
        try {
            Socket clientSocket = clientSocketChannel.socket();
            outputStream = new PrintStream(clientSocket.getOutputStream(), true);

            InputStream inputStream = clientSocket.getInputStream();
            HttpRequest request = new HttpRequest(absolutePath, inputStream);
            HttpResponse response = new HttpResponse(outputStream);

            try {
                requestProcessor.process(request, response);
            } catch (Throwable ex) {
                logger.error("Internal server error occured", ex);
                httpHelper.createResponseInternallError(outputStream);

            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.println();
                    outputStream.flush();
                    outputStream.close();
                }
                clientSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
