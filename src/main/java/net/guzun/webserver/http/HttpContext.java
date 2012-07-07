package net.guzun.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.processors.RequestProcessor;

import org.apache.log4j.Logger;

public class HttpContext implements Runnable {
	private static Logger logger = Logger.getLogger(HttpContext.class);
	private SocketChannel clientSocketChannel;
	private RequestProcessor requestProcessor;
	private final String absolutePath;

	public HttpContext(String absolutePathParam, RequestProcessor requestProcessorParam, SocketChannel clientSocketChannelParam) {
		this.absolutePath = absolutePathParam;
		this.clientSocketChannel = clientSocketChannelParam;
		this.requestProcessor = requestProcessorParam;
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
		} catch (RequestProcessingException e1) {
			e1.printStackTrace();
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
