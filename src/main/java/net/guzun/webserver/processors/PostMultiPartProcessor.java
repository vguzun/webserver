package net.guzun.webserver.processors;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.http.HttpStreamReader;
import net.guzun.webserver.utils.ContentDisposition;

public class PostMultiPartProcessor extends BaseProcessor {
	
	public PostMultiPartProcessor(RequestProcessor nextProcessor) {
		super(nextProcessor);
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
		if (request.isMultipart()) {
			HttpStreamReader inputStream = request.getInputStream();
			inputStream.upgradeToMultipart(request.getBoundary());

			try {
				do {
					String headersString = inputStream.readHeaders();
					String[] headers = headersString.split("\r\n");
					ByteArrayOutputStream data = new ByteArrayOutputStream();
					inputStream.readBody(data);
					if (headers.length > 1) {
						ContentDisposition contentDisposition = new ContentDisposition(headers[1]);

						String fileName = contentDisposition.getFileName();
						if (fileName != null) {
							FileOutputStream fstream = new FileOutputStream(request.getAbsolutPath() + fileName);
							data.writeTo(fstream);
							fstream.close();
						}
					}
					
				} while (inputStream.readBoundary());
			} catch (IOException e) {
				throw new RequestProcessingException(e);
			}

			PrintStream printStream = new PrintStream(response.getOutputStream(), true);
			printStream.println("HTTP/1.1 302 Moved Temporarily\r\n");
			printStream.println("Location: http://www.yahoo.com/\r\n");
			printStream.println("\r\n\r\n\r\n");
		} else {
			super.process(request, response);
		}

	}
}
