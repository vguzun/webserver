package net.guzun.webserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.guzun.webserver.http.HttpContext;
import net.guzun.webserver.io.ContentProviderFactory;
import net.guzun.webserver.processors.DefaultProcessor;
import net.guzun.webserver.processors.GetProcessor;
import net.guzun.webserver.processors.HeaderProcessor;
import net.guzun.webserver.processors.PostMultiPartProcessor;
import net.guzun.webserver.processors.RequestProcessor;

import org.apache.log4j.Logger;

/**
 * WebServer main thread
 *
 */
public class WebServer implements Runnable {
    private static Logger logger = Logger.getLogger(WebServer.class);
    private ServerSocketChannel serverSocketChannel;
    private int port;
    private String rootFolder;
    private volatile boolean isStarted;
    private RequestProcessor requestProcessor = new HeaderProcessor(new PostMultiPartProcessor((new GetProcessor(
            new DefaultProcessor(null), new ContentProviderFactory()))));
    private ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * initialize web-server
     * @param listenPort pot to listen
     * @param rootFolder folder to browse
     * @throws IOException exception
     */
    public WebServer(String listenPort, String rootFolder) throws IOException {
        try {
            this.port = Integer.parseInt(listenPort);
            this.rootFolder = rootFolder;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
        } catch (IOException e) {
            logger.error("An error occured during server start up", e);
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException closeException) {
                    logger.error("Channel can't be closed", closeException);
                }
                serverSocketChannel = null;
            }
            throw e;
        }
    }

    /**
     * Stop server
     */
    public void stop() {
        isStarted = false;
        executor.shutdown();
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            logger.error("Error on closing server socket", e);
        }
    }

    /**
     * Executing method
     */
    public void run() {
        isStarted = true;
        System.out.println(String.format("Server started on %d port, roodFolder=%s", port, rootFolder));
        while (isStarted) {
            try {
                SocketChannel clientSocket = serverSocketChannel.accept();
                if (clientSocket != null) {
                    System.out.println("connection request received");
                    HttpContext connection = new HttpContext(rootFolder, requestProcessor, clientSocket);
                    executor.execute(connection);
                }
            } catch (AsynchronousCloseException e) {
                logger.info("Asynchronose close");
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
