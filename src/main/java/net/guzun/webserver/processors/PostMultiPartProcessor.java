package net.guzun.webserver.processors;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;

public class PostMultiPartProcessor extends BaseProcessor {
	public PostMultiPartProcessor(RequestProcessor nextProcessor) {
		super(nextProcessor);
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {

		if (request.isMultipart()) {
			@SuppressWarnings("deprecation")
			MultipartStream multipartStream = new MultipartStream(request.getInputStream(), request.getBoundary());
			boolean nextPart;
			try {
				nextPart = true;
				while (nextPart) {
					String headersString = multipartStream.readHeaders();
					String[] headers = headersString.split("\r\n");
					if (headers.length > 1) {
						ContentDisposition contentDisposition = new ContentDisposition(headers[1]);

						String fileName = contentDisposition.getFileName();
						if (fileName != null) {
							ByteArrayOutputStream data = new ByteArrayOutputStream();
							multipartStream.readBodyData(data);
							FileOutputStream fstream = new FileOutputStream(request.getAbsolutPath() + fileName);
							data.writeTo(fstream);
							fstream.close();
							nextPart = multipartStream.readBoundary();
							continue;
						}
					}
					if (request.getInputStream().available() > 0) {
						multipartStream.discardBodyData();
						nextPart = multipartStream.readBoundary();
					} else {
						break;
					}
				}
			} catch (MalformedStreamException e) {
				System.err.print("Mailformed stream");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintStream printStream = new PrintStream(response.getOutputStream(), true);
			StringBuilder html = new StringBuilder();
			html.append("HTTP/1.1 302 Found\r\n");
			html.append("Location: " + request.getAbsolutPath());
			printStream.append(html.toString());
			printStream.flush();
			printStream.close();
		}

	}
}
