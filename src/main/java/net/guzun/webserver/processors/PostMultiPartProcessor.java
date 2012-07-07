package net.guzun.webserver.processors;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.http.HttpStreamReader;

import org.apache.commons.fileupload.MultipartStream.IllegalBoundaryException;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;

public class PostMultiPartProcessor extends BaseProcessor {
	public PostMultiPartProcessor(RequestProcessor nextProcessor) {
		super(nextProcessor);
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		if (request.isMultipart()) {
			HttpStreamReader inputStream = request.getInputStream();
			inputStream.upgradeToMultipart(request.getBoundary());

			boolean nextPart;
			try {
				nextPart = true;
				while (nextPart) {
					// multipartStream.skipPreamble();
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
					nextPart = inputStream.readBoundary();
				}
			} catch (MalformedStreamException e) {
				System.err.print("Mailformed stream");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PrintStream printStream = new PrintStream(response.getOutputStream(), true);
			printStream.println("HTTP/1.1 200 OK");
			printStream.println();
			// printStream.flush();
			// printStream.close();
		} else {
			super.process(request, response);
		}

	}
}
