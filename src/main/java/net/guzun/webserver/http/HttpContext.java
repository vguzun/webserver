package net.guzun.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import net.guzun.webserver.processors.RequestProcessor;

public class HttpContext implements Runnable {

    private SocketChannel clientSocketChannel;
    private RequestProcessor requestProcessor;
	private final String absolutePath;

    public HttpContext(String absolutePath, RequestProcessor requestProcessor, SocketChannel clientSocketChannel) {
        this.absolutePath = absolutePath;
		this.clientSocketChannel = clientSocketChannel;
        this.requestProcessor = requestProcessor;
    }

    public void run() {
    	PrintStream outputStream = null;
		try {
			Socket clientSocket = clientSocketChannel.socket();
			outputStream = new PrintStream(clientSocket.getOutputStream(), true);
			
            InputStream inputStream = clientSocket.getInputStream();
			HttpRequest request = new HttpRequest(absolutePath, inputStream);
            HttpResponse response = new HttpResponse(outputStream);
            requestProcessor.process(request, response);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		finally {
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
